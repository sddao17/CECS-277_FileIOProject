
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles and validates all user input using exception handling.
 */
public class CheckInput {

    /**
     * Takes an option as a string and throws an exception if the input is invalid.
     *
     * @return the valid string
     */
    public static String checkAddRetrieveQuit() throws InputMismatchException {
        Scanner in = new Scanner(System.in);
        String input;
        input = in.nextLine();

        if (!(input.equalsIgnoreCase("a") | input.equalsIgnoreCase("r") |
                input.equalsIgnoreCase("q"))) {
            throw new InputMismatchException(input);
        }
        return input;
    }

    /**
     * Checks if the input is a nonempty string.
     *
     * @return a valid string
     */
    public static String checkString() throws InputMismatchException {
        Scanner in = new Scanner(System.in);
        String input;
        String nonEmptyStringRegex = ".*\\w.*";
        boolean valid = false;

        do {
            input = in.nextLine();

            // ensure that the user input is not empty / only whitespace
            if (!input.matches(nonEmptyStringRegex)) {
                PrintMessage.emptyInputError();
            } else {
                valid = true;
            }
        } while (!valid);

        return input;
    }

    /**
     * Checks if the input is a valid service of type String.
     *
     * @return the valid string
     */
    public static String checkService() throws InputMismatchException, UnknownTransactionException {
        Scanner in = new Scanner(System.in);
        String input;
        boolean valid = false;

        do {
            input = in.nextLine();

            if (input.equalsIgnoreCase("-li")) {
                PrintMessage.displayHotelServices();
                PrintMessage.enterServiceSold();
            } else {
                String[] services = MainTest.getHotelServices();

                // try to match the input to one of the services in the list
                for (String service : services) {
                    if (input.equalsIgnoreCase(service)) {
                        valid = true;
                        break;
                    }
                }

                // the input did not match one of the services; throw an exception
                if (!valid) {
                    throw new UnknownTransactionException("\nThe input \"" + input + "\" is not a valid service; "
                                                        + "please try again.");
                }
            }
        } while (!valid);

        return input;
    }

    /**
     * Checks if the input is a double.
     * Modified from CheckInput.java.
     *
     * @return a valid double.
     */
    public static String checkDouble() throws InputMismatchException {
        Scanner in = new Scanner( System.in );
        double input = 0;
        boolean valid = false;

        do {
            if (in.hasNextDouble()) {
                input = in.nextDouble();
                valid = true;
            } else {
                in.next(); // clear invalid string
                PrintMessage.invalidNumberError();
            }
        } while (!valid);

        // format to two decimal places
        DecimalFormat df = new DecimalFormat("#0.00");

        return df.format(input);
    }

    /**
     * Checks if the input is a valid date and throws an exception otherwise.
     *
     * @return a valid date as a string.
     */
    public static String checkDate() throws InputMismatchException, ParseException {
        Scanner in = new Scanner( System.in );
        String input;

        input = in.nextLine();

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter.setLenient(false);
        Date date = formatter.parse(input);

        return date.toString();
    }
}
