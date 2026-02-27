package heisenberg.commands;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.storage.Storage;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeisenbergException {
        if (index < 0 || index >= tasks.getSize()) {
            throw new HeisenbergException("That task doesn't exist. Apply yourself!");
        }
        tasks.markTask(index);
        ui.showMarked(tasks.getTask(index));
        storage.save(tasks);
    }
}