package heisenberg;

import java.util.Scanner;

public class Heisenberg {

    // Layout: Constants first
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = " _   _       _                  _\n"
            + "| | | | ___ (_) ___  ___ _ __  | |__   ___ _ __ __ _\n"
            + "| |_| |/ _ \\| |/ __|/ _ \\ '_ \\ | '_ \\ / _ \\ '__/ _` |\n"
            + "|  _  |  __/| |\\__ \\  __/ | | || |_) |  __/ | | (_| |\n"
            + "|_| |_|\\___||_| ___/\\___|_| |_||_.__/ \\___|_|  \\__, |\n"
            + "                                               |___/\n";

    // Command Constants
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    private static final String DEADLINE_PREFIX = "/by";
    private static final String EVENT_FROM_PREFIX = "/from";
    private static final String EVENT_TO_PREFIX = "/to";

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    /**
     * Main entry point for the Heisenberg chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) {
            HeisenbergMessages.setTestMode(true);
        }

        printWelcome();
        Scanner in = new Scanner(System.in);

        // Main interaction loop
        boolean isRunning = true;
        while (isRunning) {
            String input = in.nextLine();
            if (input.equalsIgnoreCase(COMMAND_BYE)) {
                isRunning = false;
                continue;
            }
            try {
                handleCommand(input);
            } catch (HeisenbergException e) {
                showError(e.getMessage());
            }
        }
        printGoodbye();
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param input The raw input string from the user.
     * @throws HeisenbergException If the command is invalid or arguments are missing.
     */
    private static void handleCommand(String input) throws HeisenbergException {
        // Extract the command word (first word)
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        // Use switch for cleaner command handling
        switch (command) {
        case COMMAND_LIST:
            listTasks();
            break;
        case COMMAND_MARK:
            markTask(input);
            break;
        case COMMAND_UNMARK:
            unmarkTask(input);
            break;
        case COMMAND_TODO:
            addTodo(input);
            break;
        case COMMAND_DEADLINE:
            addDeadline(input);
            break;
        case COMMAND_EVENT:
            addEvent(input);
            break;
        default:
            throw new HeisenbergException("I don't know what that means. Speak up!");
        }
    }

    private static void addTodo(String input) throws HeisenbergException {
        String description = input.replaceFirst(COMMAND_TODO, "").trim();
        if (description.isEmpty()) {
            throw new HeisenbergException("The description of a todo cannot be empty, Jesse!");
        }
        addTask(new ToDo(description));
    }

    private static void addDeadline(String input) throws HeisenbergException {
        // Validation
        if (!input.contains(DEADLINE_PREFIX)) {
            throw new HeisenbergException("Missing '" + DEADLINE_PREFIX + "' for the deadline. Stay focused!");
        }

        // Parsing logic
        String cleanInput = input.replaceFirst(COMMAND_DEADLINE, "");
        String[] parts = cleanInput.split(DEADLINE_PREFIX, 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new HeisenbergException("Deadlines need a description and a time. Apply yourself!");
        }
        addTask(new Deadline(description, by));
    }

    private static void addEvent(String input) throws HeisenbergException {
        // Validation
        if (!input.contains(EVENT_FROM_PREFIX) || !input.contains(EVENT_TO_PREFIX)) {
            throw new HeisenbergException("Events need a '" + EVENT_FROM_PREFIX + "' and '" + EVENT_TO_PREFIX + "'. Apply yourself!");
        }

        // Parsing logic
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
        addTask(new Event(description, from, to));
    }

    private static void listTasks() {
        printLine();
        System.out.println(INDENT + "Jesse here is our to-do list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(INDENT + (i + 1) + "." + tasks[i]);
        }
        printLine();
    }

    private static void markTask(String input) throws HeisenbergException {
        try {
            int index = getIndexFromCommand(input, COMMAND_MARK);
            tasks[index].markAsDone();

            printLine();
            System.out.println(INDENT + HeisenbergMessages.getMarkMessage());
            System.out.println(INDENT + "  " + tasks[index]);
            printLine();
        } catch (NumberFormatException e) {
            throw new HeisenbergException("Speak English! Give me a valid number.");
        }
    }

    private static void unmarkTask(String input) throws HeisenbergException {
        try {
            int index = getIndexFromCommand(input, COMMAND_UNMARK);
            tasks[index].unmarkAsDone();

            printLine();
            System.out.println(INDENT + HeisenbergMessages.getUnmarkMessage());
            System.out.println(INDENT + "  " + tasks[index]);
            printLine();
        } catch (NumberFormatException e) {
            throw new HeisenbergException("Speak English! Give me a valid number.");
        }
    }

    /**
     * Extracts the index from a command string.
     *
     * @param input The full command string (e.g., "mark 1").
     * @param command The command name for error messaging.
     * @return The index of the task.
     * @throws HeisenbergException If the index is invalid or out of bounds.
     */
    private static int getIndexFromCommand(String input, String command) throws HeisenbergException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HeisenbergException("You need to tell me which task to " + command + ".");
        }

        int index = Integer.parseInt(parts[1]) - 1;

        if (index < 0 || index >= taskCount) {
            throw new HeisenbergException("That task doesn't exist. Apply yourself!");
        }
        return index;
    }

    private static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;

        printLine();
        System.out.println(INDENT + HeisenbergMessages.getAddMessage());
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static void printWelcome() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Say My Name\n" + LOGO);
        System.out.println("You're god dam right");
        System.out.println("So... What are you up to?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printGoodbye() {
        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "You don't need a regular chatbot. You need a *criminal* chatbot.");
        System.out.println(INDENT + "S'all good, man! See you in court.");
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    private static void printLine() {
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    private static void showError(String message) {
        printLine();
        System.out.println(INDENT + HeisenbergMessages.getErrorMessage());
        System.out.println(INDENT + "Error: " + message);
        printLine();
    }
}