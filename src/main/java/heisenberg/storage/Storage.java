package heisenberg.storage;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.tasks.Deadline;
import heisenberg.tasks.Event;
import heisenberg.tasks.Task;
import heisenberg.tasks.TaskList;
import heisenberg.tasks.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void load(TaskList tasks) throws HeisenbergException {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new HeisenbergException("Error loading file: " + e.getMessage());
        }
    }

    public void save(TaskList tasks) throws HeisenbergException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean dirCreated = parentDir.mkdirs();
                if (!dirCreated) {
                    throw new HeisenbergException("Could not create directory: " + parentDir.getAbsolutePath());
                }
            }

            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < tasks.getSize(); i++) {
                writer.write(tasks.getTask(i).toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new HeisenbergException("Error saving file: " + e.getMessage());
        }
    }

    private Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        //Validation
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        switch (type) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            if (parts.length >= 4) {
                task = new Deadline(description, parts[3]);
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(description, parts[3], parts[4]);
            }
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}