package heisenberg.ui;

import java.util.Random;

/**
 * Manages the personality-based responses for the Heisenberg chatbot.
 * Provides random quotes from "Breaking Bad" for various interactions.
 */
public class HeisenbergMessages {
    private static final Random random = new Random();
    private static boolean isTestMode = false;

    // CATEGORY 1: When adding a task
    private static final String[] ADD_PHRASES = {
            "This is 99.1% pure productivity. I've added:",
            "We have work to do. Added to the batch:",
            "No half measures. I've listed this:",
            "You're building an empire, Jesse. Added:"
    };

    // CATEGORY 2: When marking a task
    private static final String[] MARK_PHRASES = {
            "Tight, Tight, Tight! Task completed:",
            "Respect the chemistry. The product is done:",
            "You're the best, man. You're the one! Completed:",
            "Stay out of my territory... because this task is done:"
    };

    // CATEGORY 3: When unmarking a task
    private static final String[] UNMARK_PHRASES = {
            "You're slipping, Jesse. I've marked this task as not done yet:",
            "We are back to square one. Unmarked:",
            "Did you learn nothing from my class? Back to the list:"
    };

    // CATEGORY 4: Errors
    private static final String[] ERROR_PHRASES = {
            "I am not in danger Skyler, I AM THE DANGER",
            "What is wrong with you? Why are you blue?",
            "Do not cross me. Check your command."
    };

    /**
     * Sets the test mode flag.
     *
     * @param isTest If true, the chatbot returns deterministic messages for testing.
     */
    public static void setTestMode(boolean isTest) {
        isTestMode = isTest;
    }

    /**
     * Returns a random message from the given array, or the first one if in test mode.
     */
    private static String getRandom(String[] phrases) {
        if (isTestMode) {
            return phrases[0];
        }
        return phrases[random.nextInt(phrases.length)];
    }

    public static String getAddMessage() {
        return getRandom(ADD_PHRASES);
    }

    public static String getMarkMessage() {
        return getRandom(MARK_PHRASES);
    }

    public static String getUnmarkMessage() {
        return getRandom(UNMARK_PHRASES);
    }

    public static String getErrorMessage() {
        return getRandom(ERROR_PHRASES);
    }
}