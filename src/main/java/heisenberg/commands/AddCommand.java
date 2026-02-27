package heisenberg.commands;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.storage.Storage;
import heisenberg.tasks.Task;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeisenbergException {
        tasks.addTask(task);
        ui.showAdded(task, tasks.getSize());
        storage.save(tasks);
    }
}