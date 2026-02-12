package heisenberg.tasks;

/**
 * Represents a task that occurs during a specific time range.
 */
public class Event extends Task {

    private final String from;
    private final String to;

    /**
     * Creates an Event task.
     *
     * @param description The event description.
     * @param from The start time.
     * @param to The end time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}