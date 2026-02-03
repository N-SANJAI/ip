package heisenberg;

/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {

    private final String by;

    /**
     * Creates a Deadline task.
     *
     * @param description The task description.
     * @param by The deadline time (e.g., "Sunday").
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}