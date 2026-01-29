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
            handleCommand(input);
        }
        printGoodbye();
    }

    //Helper function to handle input commands
    private static void handleCommand(String input) {
        if (input.equalsIgnoreCase("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            markTask(input);
        } else if (input.startsWith("unmark")) {
            unmarkTask(input);
        } else {
            addTask(input);
        }
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
    private static void markTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks[index].markAsDone();

        printLine();
        System.out.println(INDENT + "Tight, Tight, Tight, Task completed: ");
        System.out.println(INDENT + "  " + tasks[index]);
        printLine();
    }

    //Helper function to label the task as unmarked
    private static void unmarkTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks[index].unmarkAsDone();

        printLine();
        System.out.println(INDENT + "You're slipping, Jesse. I’ve marked this task as not done yet: ");
        System.out.println(INDENT + "  " + tasks[index]);
        printLine();
    }

    //Helper function to add new task
    private static void addTask(String description) {
        tasks[taskCount] = new Task(description);
        taskCount++;

        printLine();
        System.out.println(INDENT + "This is 99.1% pure productivity. I've added: " + description);
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