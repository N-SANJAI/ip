package heisenberg.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs during a specific time range.
 */
public class Event extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final String rawFrom;
    private final String rawTo;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    /**
     * Creates an Event task.
     * If {@code from} or {@code to} are in yyyy-MM-dd format, they will be parsed and displayed
     * in a friendlier format. Otherwise, they are stored and displayed as-is.
     *
     * @param description The event description.
     * @param from The start date string (e.g., "2026-03-06" or "Mon 2pm").
     * @param to The end date string (e.g., "22026-03-07" or "4pm").
     */
    public Event(String description, String from, String to) {
        super(description);
        this.rawFrom = from;
        this.rawTo = to;
        this.fromDate = tryParse(from);
        this.toDate = tryParse(to);
    }

    private static LocalDate tryParse(String dateStr) {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toFileFormat() {
        String savedFrom = (fromDate != null) ? fromDate.toString() : rawFrom;
        String savedTo = (toDate != null) ? toDate.toString() : rawTo;
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + savedFrom + " | " + savedTo;
    }

    @Override
    public String toString() {
        String formattedFrom = (fromDate != null) ? fromDate.format(OUTPUT_FORMAT) : rawFrom;
        String formattedTo = (toDate != null) ? toDate.format(OUTPUT_FORMAT) : rawTo;
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }
}