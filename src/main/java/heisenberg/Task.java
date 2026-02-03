package heisenberg;

/**
 * Represents a generic task in the Heisenberg chatbot.
 * Serves as the base class for specific task types like ToDo, Deadline, and Event.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new Task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon depending on whether the task is done.
     *
     * @return "X" if done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}