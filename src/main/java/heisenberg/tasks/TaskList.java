package heisenberg.tasks;

import heisenberg.exceptions.HeisenbergException;
import java.util.ArrayList;

/**
 * Manages the list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        tasks.get(index).unmarkAsDone();
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }
}