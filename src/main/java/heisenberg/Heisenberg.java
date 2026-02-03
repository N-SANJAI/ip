package heisenberg;

import java.util.Scanner;

public class Heisenberg {

    // Constants and Variables
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = " _   _       _                  _                     \n"
            + "| | | | ___ (_) ___  ___ _ __  | |__   ___ _ __ __ _      \n"
            + "| |_| |/ _ \\| |/ __|/ _ \\ '_ \\ | '_ \\ / _ \\ '__/ _` |\n"
            + "|  _  |  __/| |\\__ \\  __/ | | || |_) |  __/ | | (_| |   \n"
            + "|_| |_|\\___||_| ___/\\___|_| |_||_.__/ \\___|_|  \\__, | \n"
            + "                                               |___/      \n";

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    //Main Heisenberg chatbot function
    public static void main(String[] args) {
        printWelcome();
        Scanner in = new Scanner(System.in);

        // Main interaction loop
        while (true) {
            String input = in.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            try {
                handleCommand(input);
            } catch (HeisenbergException e) {
                printLine();
                System.out.println(INDENT + HeisenbergMessages.getErrorMessage());
                System.out.println(INDENT + "Error: " + e.getMessage());
                printLine();
            }
        }
        printGoodbye();
    }

    //Helper function to handle input commands
    private static void handleCommand(String input) throws HeisenbergException {
        if (input.equalsIgnoreCase("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            markTask(input);
        } else if (input.startsWith("unmark")) {
            unmarkTask(input);
        } else if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            throw new HeisenbergException("I don't know what that means. Speak up!");
        }
    }

    //Helper function to add a task as a Todo
    private static void addTodo(String input) throws HeisenbergException {
        String description = input.replaceFirst("todo", "").trim();
        if (description.isEmpty()) {
            throw new HeisenbergException("The description of a todo cannot be empty, Jesse!");
        }
        addTask(new ToDo(description));
    }

    //Helper function to add task as a Deadline
    private static void addDeadline(String input) throws HeisenbergException {
        // Expected format: deadline return book /by Sunday
        if (!input.contains("/by")) {
            throw new HeisenbergException("Missing '/by' for the deadline. Stay focused!");
        }
        String[] parts = input.replaceFirst("deadline", "").split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new HeisenbergException("Deadlines need a description and a time. Apply yourself!");
        }
        addTask(new Deadline(description, by));
    }

    //Helper function to add task as an Event
    private static void addEvent(String input) throws HeisenbergException {
        // Expected format: event meeting /from Mon 2pm /to 4pm
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new HeisenbergException("Events need a '/from' and '/to'. Apply yourself!");
        }

        String[] parts = input.replaceFirst("event", "").split("/from", 2);
        String description = parts[0].trim();

        String[] timeParts = parts[1].split("/to", 2);
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new HeisenbergException("Events need a description, from, and to. Don't be sloppy.");
        }
        addTask(new Event(description, from, to));
    }

    //Helper function to list tasks stored in tasks[]
    private static void listTasks() {
        printLine();
        System.out.println(INDENT + "Jesse here is our to-do list: ");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(INDENT + (i + 1) + "." + tasks[i]);
        }
        printLine();
    }

    //Helper function to label the task as marked
    private static void markTask(String input) throws HeisenbergException {
        try {
            int index = getIndexFromCommand(input, "mark");
            tasks[index].markAsDone();

            printLine();
            System.out.println(INDENT + HeisenbergMessages.getMarkMessage());
            System.out.println(INDENT + "  " + tasks[index]);
            printLine();

        } catch (NumberFormatException e) {
            throw new HeisenbergException("Speak English! Give me a valid number.");
        }
    }
    //Helper function to label the task as unmarked
    private static void unmarkTask(String input) throws HeisenbergException {
        try {
            int index = getIndexFromCommand(input, "unmark");
            tasks[index].unmarkAsDone();

            printLine();
            System.out.println(INDENT + HeisenbergMessages.getUnmarkMessage());
            System.out.println(INDENT + "  " + tasks[index]);
            printLine();

        } catch (NumberFormatException e) {
            throw new HeisenbergException("Speak English! Give me a valid number.");
        }
    }

    //helper function to get the index from mark and unmark commands
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

    //Helper function to add new task
    private static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;

        printLine();
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    //Helper function to print welcome statement
    private static void printWelcome() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Say My Name \n" + LOGO);
        System.out.println("You're god dam right");
        System.out.println("So... What are you up to?");
        System.out.println(HORIZONTAL_LINE);
    }

    //Helper function to print goodbye statement
    private static void printGoodbye() {
        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "You don't need a regular chatbot. You need a *criminal* chatbot.");
        System.out.println(INDENT + "S'all good, man! See you in court.");
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    //Helper function to print a horizontal like with indentation
    private static void printLine() {
        System.out.println(INDENT + HORIZONTAL_LINE);
    }
}