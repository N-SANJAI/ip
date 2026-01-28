import java.util.Scanner;

public class Heisenberg {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String indent = "   ";
        String logo = " _   _       _                  _                          \n"
                + "| | | | ___ (_) ___  ___ _ __  | |__   ___ _ __ __ _ \n"
                + "| |_| |/ _ \\| |/ __|/ _ \\ '_ \\ | '_ \\ / _ \\ '__/ _` |\n"
                + "|  _  |  __/| |\\__ \\  __/ | | || |_) |  __/ | | (_| |\n"
                + "|_| |_|\\___||_| ___/\\___|_| |_||_.__/ \\___|_|  \\__, |\n"
                + "                                               |___/ \n";

        //Greeting (level 0)
        System.out.println(line);
        System.out.println("Say My Name \n" + logo);
        System.out.println("You're god dam right");
        System.out.println("So? What are you up to?");
        System.out.println(line);

        //Echo (level 1)
        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(indent+line);
            System.out.println(indent+input);
            System.out.println(indent+line);
        }

        //Exit (level 0)
        System.out.println(indent + line);
        System.out.println(indent + "You don't need a regular chatbot. You need a *criminal* chatbot.");
        System.out.println(indent + "S'all good, man! See you in court.");
        System.out.println(indent + line);
    }
}