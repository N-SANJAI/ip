package heisenberg;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.parser.Parser;
import heisenberg.storage.Storage;
import heisenberg.tasks.Deadline;
import heisenberg.tasks.Event;
import heisenberg.tasks.Task;
import heisenberg.tasks.TaskList;
import heisenberg.tasks.ToDo;
import heisenberg.ui.HeisenbergMessages;
import heisenberg.ui.Ui;

public class Heisenberg {

    private final Ui ui;
    private final TaskList tasks;
    private final Storage storage;

    public Heisenberg() {
        ui = new Ui();
        tasks = new TaskList();
        storage = new Storage("data/heisenberg.txt");
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
        new Heisenberg().run();
    }

    public void run() {
        ui.showWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String command = Parser.parseCommand(input);

            if (command.equals(Parser.COMMAND_BYE)) {
                isRunning = false;
                continue;
            }

            try {
                handleCommand(command, input);
            } catch (HeisenbergException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showGoodbye();
    }

    private void handleCommand(String command, String input) throws HeisenbergException {
        switch (command) {
        case Parser.COMMAND_LIST:
            ui.showList(tasks);
            break;

        case Parser.COMMAND_MARK:
            int markIndex = Parser.parseIndex(input, Parser.COMMAND_MARK, tasks.getSize());
            tasks.markTask(markIndex);
            ui.showMarked(tasks.getTask(markIndex));
            storage.save(tasks);
            break;

        case Parser.COMMAND_UNMARK:
            int unmarkIndex = Parser.parseIndex(input, Parser.COMMAND_UNMARK, tasks.getSize());
            tasks.unmarkTask(unmarkIndex);
            ui.showUnmarked(tasks.getTask(unmarkIndex));
            storage.save(tasks);
            break;

        case Parser.COMMAND_DELETE:
            int deleteIndex = Parser.parseIndex(input, Parser.COMMAND_DELETE, tasks.getSize());
            Task deleted = tasks.deleteTask(deleteIndex);
            ui.showDeleted(deleted, tasks.getSize());
            break;

        case Parser.COMMAND_TODO:
            String todoDesc = Parser.parseTodo(input);
            Task todo = new ToDo(todoDesc);
            addTask(todo);
            storage.save(tasks);
            break;

        case Parser.COMMAND_DEADLINE:
            String[] deadlineParts = Parser.parseDeadline(input);
            Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            addTask(deadline);
            storage.save(tasks);
            break;

        case Parser.COMMAND_EVENT:
            String[] eventParts = Parser.parseEvent(input);
            Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
            addTask(event);
            storage.save(tasks);
            break;

        default:
            throw new HeisenbergException("I don't know what that means. Speak up!");
        }
    }

    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showAdded(task, tasks.getSize());
    }
}