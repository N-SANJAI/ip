package heisenberg.tasks;

import heisenberg.exceptions.HeisenbergException;

/**
 * Manages the list of tasks.
 */
public class TaskList {
    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    public int getSize() {
        return taskCount;
    }

    public void markTask(int index) {
        tasks[index].markAsDone();
    }

    public void unmarkTask(int index) {
        tasks[index].unmarkAsDone();
    }

    public Task deleteTask(int index) {
        Task deletedTask = tasks[index];
        for (int i = index; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        taskCount--;
        return deletedTask;
    }
}