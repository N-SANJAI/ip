package heisenberg.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final String rawBy;
    private final LocalDate byDate;

    /**
     * Creates a Deadline task.
     * If {@code by} is in yyyy-MM-dd format, it will be parsed and displayed in a friendlier format.
     * Otherwise, it is stored and displayed as-is.
     *
     * @param description The task description.
     * @param by The deadline date string (e.g., "2026-03-06" or "Sunday").
     */
    public Deadline(String description, String by) {
        super(description);
        this.rawBy = by;
        LocalDate parsed;
        try {
            parsed = LocalDate.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            parsed = null;
        }
        this.byDate = parsed;
    }

    @Override
    public String toFileFormat() {
        String savedBy = (byDate != null) ? byDate.toString() : rawBy;
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | " + savedBy;
    }

    @Override
    public String toString() {
        String formattedBy = (byDate != null) ? byDate.format(OUTPUT_FORMAT) : rawBy;
        return "[D]" + super.toString() + " (by: " + formattedBy + ")";
    }
}