import java.util.Scanner;

/**
 * The Console class provides utility methods for interacting with the console, including reading user input
 * and printing instructions for the program.
 */
public class Console {

    // Scanner instance for reading user input.
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Reads a string input from the user with the provided prompt.
     *
     * @param prompt The prompt message to be displayed to the user.
     * @return A String representing the user's input.
     */
    public static String readNumber(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Reads an integer input from the user within a specified range.
     *
     * @param prompt The prompt message to be displayed to the user.
     * @param min    The minimum allowed value for the input.
     * @param max    The maximum allowed value for the input.
     * @return An integer representing the user's input within the specified range.
     */
    public static int readNumber(String prompt, double min, double max) {
        int value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextInt();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a number between " + min + " and " + max);
        }
        return value;
    }

    /**
     * Prints instructions for the program to the console.
     */
    public static void printInstructions() {
        System.out.println("This program is designed to transcribe an MP4 file into its original language "
                + "and subsequently translate it into either English or Spanish.\n");
        System.out.println("Please enter the number corresponding to the song you want.\n");
        System.out.println("1. Despierto - Andrea Bejar\n");
        System.out.println("2. Tummy hurts - Renee Rapp\n");
    }
}
