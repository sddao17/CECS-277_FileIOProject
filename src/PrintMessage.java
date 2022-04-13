
/**
 * Handles all instances of using PrintStream to print messages.
 */
public class PrintMessage {

    /**
     * Shows a message indicating the program has started.
     */
    protected static void initiatingIO() {
        System.out.println("-------------------------------\n" +
                            "    Initiating File I/O ...\n" +
                            "-------------------------------\n");
    }

    /**
     * Displays the options for adding data, retrieve data, or quitting the program.
     */
    public static void showAddRetrieveQuit() {
        System.out.println("Please choose an option:\n" +
                            "\tA)dd data\n" +
                            "\tR)etrieve data\n" +
                            "\tQ)uit");
    }

    /**
     * Prompts the user to enter the name of the client.
     */
    public static void enterClientName() {
        System.out.print("Enter the name of the client: ");
    }

    /**
     * Prompts the user to enter the name of the service sold.
     */
    public static void enterServiceSold() {
        System.out.print("Enter the name of the service sold (`-li` to show list): ");
    }

    /**
     * Prompts the user to enter the amount of the sale.
     */
    public static void enterAmountOfSale() {
        System.out.print("Enter the amount of the sale: ");
    }

    /**
     * Prompts the user to enter the date of the event.
     */
    public static void enterDateOfEvent() {
        System.out.print("Enter the date of the event: ");
    }

    /**
     * Displays the services available from the hotel.
     */
    public static void displayHotelServices() throws ArrayIndexOutOfBoundsException {
        System.out.print("~~~~~~~~~~~~~~~~ List of Hotel Services ~~~~~~~~~~~~~~~~\n\t");
        String[] services = MainTest.getHotelServices();
        int lastIndex = services.length - 1;

        for (int i = 0; i < services.length; ++i) {
            System.out.print(services[i]);
            // add a comma after every element except the last
            if (i < lastIndex) {
                System.out.print(", ");
            }

            // separate services into groups of 3 for easier user readability
            if (i > 0 && ((i + 1) % 3) == 0 && i < lastIndex) {
                System.out.print("\n\t");
            }
        }
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Displays the retrieved data.
     *
     * @param data the data to display
     */
    public static void displayRetrievedData(String data) {
        System.out.println("Data retrieved from the files:\n\n" + data);
    }

    /**
     * Displays an error message indicating the input is invalid.
     *
     * @param invalidInput the invalid input
     */
    public static void invalidInputError(String invalidInput) {
        System.out.println("The input \"" + invalidInput + "\" is invalid; please try again.");
    }

    /**
     * Displays an error message indicating that the input is empty.
     */
    public static void emptyInputError() {
        System.out.print("The input is empty; please try again: ");
    }

    /**
     * Displays an error message indicating the input is not a valid number.
     */
    public static void invalidNumberError() {
        System.out.print("The input is not a valid number; please try again: ");
    }

    /**
     * Displays an error message indicating that the date is invalid.
     */
    public static void invalidDateError() {
        System.out.println("The input is not a valid date; please try again in MM/DD/YYYY format.");
    }

    /**
     * Displays an error message indicating the file could not be found.
     *
     * @param fileName the file name that could not be found
     */
    public static void fileNotFoundError(String fileName) {
        System.out.println("The file \"" + fileName + "\" could not be found. One has been created for you.");
    }

    /**
     * Displays an error message indicating that there are no files of the given type in the directory.
     *
     * @param extensionType the file type that does not exist
     */
    public static void noFileTypeExistsError(String extensionType) {
        System.out.println("There are no files of type \"" + extensionType + "\"; please add data or create the file.");
    }

    /**
     * Displays an error message indicating that an unknown transaction has occurred.
     *
     * @param errorMessage the error message to display
     */
    public static void unknownTransactionError(String errorMessage) {
        System.out.println("An unknown transaction has occurred; " + errorMessage);
    }

    /**
     * Displays an error message indicating the file count not be read.
     *
     * @param errorMessage the error message to display
     */
    public static void fileNotReadableError(String errorMessage) {
        System.out.println("The file could not be read; " + errorMessage);
    }

    /**
     * Shows a message indicating the program has ended.
     */
    protected static void completedIO() {
        System.out.println("\n-------------------------------\n" +
                            "      File I/O completed.\n" +
                            "-------------------------------");
    }
}
