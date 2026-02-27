package heisenberg.commands;

import heisenberg.storage.Storage;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}