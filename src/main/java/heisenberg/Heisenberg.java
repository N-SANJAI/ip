package heisenberg;

import heisenberg.commands.Command;
import heisenberg.exceptions.HeisenbergException;
import heisenberg.parser.Parser;
import heisenberg.storage.Storage;
import heisenberg.tasks.TaskList;
import heisenberg.ui.HeisenbergMessages;
import heisenberg.ui.Ui;

public class Heisenberg {

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    public Heisenberg(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        try {
            storage.load(tasks);
        } catch (HeisenbergException e) {
            ui.showError("Could not load save file.");
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) {
            HeisenbergMessages.setTestMode(true);
        }
        new Heisenberg("data/heisenberg.txt").run();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (HeisenbergException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}