package heisenberg.commands;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.storage.Storage;
import heisenberg.tasks.Task;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeisenbergException {
        if (index < 0 || index >= tasks.getSize()) {
            throw new HeisenbergException("That task doesn't exist. Apply yourself!");
        }
        Task deleted = tasks.deleteTask(index);
        ui.showDeleted(deleted, tasks.getSize());
        storage.save(tasks);
    }
}