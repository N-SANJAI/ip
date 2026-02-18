package heisenberg.parser;

import heisenberg.exceptions.HeisenbergException;

/**
 * Parses user input and validates command formats.
 */
public class Parser {
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_LIST = "list";

    private static final String DEADLINE_PREFIX = "/by";
    private static final String EVENT_FROM_PREFIX = "/from";
    private static final String EVENT_TO_PREFIX = "/to";

    public static String parseCommand(String input) {
        return input.split(" ", 2)[0].toLowerCase();
    }

    public static String parseTodo(String input) throws HeisenbergException {
        String description = input.replaceFirst(COMMAND_TODO, "").trim();
        if (description.isEmpty()) {
            throw new HeisenbergException("The description of a todo cannot be empty, Jesse!");
        }
        return description;
    }

    public static String[] parseDeadline(String input) throws HeisenbergException {
        if (!input.contains(DEADLINE_PREFIX)) {
            throw new HeisenbergException("Missing '" + DEADLINE_PREFIX + "' for the deadline. Stay focused!");
        }

        String cleanInput = input.replaceFirst(COMMAND_DEADLINE, "");
        String[] parts = cleanInput.split(DEADLINE_PREFIX, 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new HeisenbergException("Deadlines need a description and a time. Apply yourself!");
        }
        return new String[]{description, by};
    }

    public static String[] parseEvent(String input) throws HeisenbergException {
        if (!input.contains(EVENT_FROM_PREFIX) || !input.contains(EVENT_TO_PREFIX)) {
            throw new HeisenbergException("Events need a '" + EVENT_FROM_PREFIX + "' and '" + EVENT_TO_PREFIX + "'. Apply yourself!");
        }

        String cleanInput = input.replaceFirst(COMMAND_EVENT, "");
        String[] parts = cleanInput.split(EVENT_FROM_PREFIX, 2);
        String description = parts[0].trim();

        String[] timeParts = parts[1].split(EVENT_TO_PREFIX, 2);
        if (timeParts.length < 2) {
            throw new HeisenbergException("Events need a start and end time. Don't be sloppy.");
        }
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new HeisenbergException("Events need a description, from, and to. Don't be sloppy.");
        }

        return new String[]{description, from, to};
    }

    public static int parseIndex(String input, String command, int taskCount) throws HeisenbergException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HeisenbergException("You need to tell me which task to " + command + ".");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new HeisenbergException("That task doesn't exist. Apply yourself!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new HeisenbergException("Speak English! Give me a valid number.");
        }
    }
}