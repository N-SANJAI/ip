import java.util.Scanner;

public class Heisenberg {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String indent = "   ";
        String logo = " _   _       _                  _                     \n"
                + "| | | | ___ (_) ___  ___ _ __  | |__   ___ _ __ __ _      \n"
                + "| |_| |/ _ \\| |/ __|/ _ \\ '_ \\ | '_ \\ / _ \\ '__/ _` |\n"
                + "|  _  |  __/| |\\__ \\  __/ | | || |_) |  __/ | | (_| |   \n"
                + "|_| |_|\\___||_| ___/\\___|_| |_||_.__/ \\___|_|  \\__, | \n"
                + "                                               |___/      \n";

        //Greeting (level 0)
        System.out.println(line);
        System.out.println("Say My Name \n" + logo);
        System.out.println("You're god dam right");
        System.out.println("So... What are you up to?");
        System.out.println(line);

        Task[] tasks = new Task[100];
        int taskCount = 0;
        Scanner in = new Scanner(System.in);

        while (true) {
            String input = in.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(indent + line);
                System.out.println(indent + "Jesse here is our to-do list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + "." + tasks[i]);
                }
                System.out.println(indent + line);
            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[index].markAsDone();
                System.out.println(indent + line);
                System.out.println(indent + "Tight, Tight, Tight, Task completed: ");
                System.out.println(indent + "  " + tasks[index]);
                System.out.println(indent + line);
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[index].unmarkAsDone();
                System.out.println(indent + line);
                System.out.println(indent + "You're slipping, Jesse. I’ve marked this task as not done yet: ");
                System.out.println(indent + "  " + tasks[index]);
                System.out.println(indent + line);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(indent + line);
                System.out.println(indent + "This is 99.1% pure productivity. I've added: " + input);
                System.out.println(indent + line);
            }
        }

        //Exit (level 0)
        System.out.println(indent + line);
        System.out.println(indent + "You don't need a regular chatbot. You need a *criminal* chatbot.");
        System.out.println(indent + "S'all good, man! See you in court.");
        System.out.println(indent + line);
    }
}