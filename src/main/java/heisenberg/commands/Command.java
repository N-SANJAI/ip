package heisenberg.commands;

import heisenberg.exceptions.HeisenbergException;
import heisenberg.storage.Storage;
import heisenberg.tasks.TaskList;
import heisenberg.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws HeisenbergException;

    public boolean isExit() {
        return false;
    }
}