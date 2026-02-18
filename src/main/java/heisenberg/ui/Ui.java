package heisenberg.ui;

import heisenberg.tasks.Task;
import heisenberg.tasks.TaskList;

import java.util.Scanner;

/**
 * Handles user interaction (input and output) for the Heisenberg chatbot.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = " _   _       _                  _\n"
            + "| | | | ___ (_) ___  ___ _ __  | |__   ___ _ __ __ _\n"
            + "| |_| |/ _ \\| |/ __|/ _ \\ '_ \\ | '_ \\ / _ \\ '__/ _` |\n"
            + "|  _  |  __/| |\\__ \\  __/ | | || |_) |  __/ | | (_| |\n"
            + "|_| |_|\\___||_| ___/\\___|_| |_||_.__/ \\___|_|  \\__, |\n"
            + "                                               |___/\n";

    private final Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void showWelcome() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Say My Name\n" + LOGO);
        System.out.println("You're god dam right");
        System.out.println("So... What are you up to?");
        System.out.println(HORIZONTAL_LINE);
    }

    public void showGoodbye() {
        printLine();
        System.out.println(INDENT + "You don't need a regular chatbot. You need a *criminal* chatbot.");
        System.out.println(INDENT + "S'all good, man! See you in court.");
        printLine();
    }

    public void printLine() {
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    public void showError(String message) {
        printLine();
        System.out.println(INDENT + HeisenbergMessages.getErrorMessage());
        System.out.println(INDENT + "Error: " + message);
        printLine();
    }

    public void showAdded(Task task, int count) {
        printLine();
        System.out.println(INDENT + HeisenbergMessages.getAddMessage());
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + count + " tasks in the list.");
        printLine();
    }

    public void showMarked(Task task) {
        printLine();
        System.out.println(INDENT + HeisenbergMessages.getMarkMessage());
        System.out.println(INDENT + "  " + task);
        printLine();
    }

    public void showUnmarked(Task task) {
        printLine();
        System.out.println(INDENT + HeisenbergMessages.getUnmarkMessage());
        System.out.println(INDENT + "  " + task);
        printLine();
    }

    public void showList(TaskList taskList) {
        printLine();
        System.out.println(INDENT + "Jesse here is our to-do list:");
        for (int i = 0; i < taskList.getSize(); i++) {
            System.out.println(INDENT + (i + 1) + "." + taskList.getTask(i));
        }
        printLine();
    }
}