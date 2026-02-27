package heisenberg.commands;

import heisenberg.storage.Storage;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}