
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * @author Alexander Loo, Phoenix Ngan, Steven Dao
 * @version 1.0
 * Due Date: March 17, 2021, 2:00pm
 *
 * Purpose: To write a program that handles exceptions and uses File I/O.
 *
 *        * A hotel salesperson enters sales in a text file. Each line contains the following, separated by semicolons:
 *              - The name of the client,
 *              - the service sold (such as Dinner, Conference, Lodging, and so on),
 *              - the amount of the sale,
 *              - and the date of that event.
 *        * Prompt the user for data to write the file.
 *        * Write a program that reads the text file as described above, and that writes a separate file for each
 *          service category, containing the entries for that category. Name the output files Dinner.txt,
 *          Conference.txt, and so on.
 *        * Enter the name of the output file from Part One as a command line argument.
 *        * Display an error if the sales file does not exist or the format is incorrect.
 *        * Also, create your own exception to handle these "unknown transaction" exceptions.
 *        * For all programs, catch and handle the Exceptions appropriately and validate the input data where needed.
 *        * Do not throw any exceptions back to the operating system. The user should see messages stating the nature
 *          of the problem and provide the "offending" data if possible.
 *
 * Target Output: Demonstrate the concepts above by being able to read / write files and validating user input
 *                  by handling various exceptions.
 */
public class MainTest {

    /**
     * Tests all other classes.
     *
     * @param args command-line arguments for the application of type String array
     */
    public static void main(String[] args) {

        PrintMessage.initiatingIO();


        // variables used for file i/o
        File file;
        File fileForSales;
        File fileForService;
        String fileNameForSales;
        String fileNameForService;
        String retrievedData;
        boolean alreadyWrittenToFile = false;

        // variables to hold input
        String option = "";
        String clientName = "";
        String serviceSold = "";
        String amountOfSale = "";
        String dateOfEvent = "";

        // flags to determine where the program should resume after dealing with an exception
        boolean clientNameIsValid = false;
        boolean serviceSoldIsValid = false;
        boolean amountOfSaleIsValid = false;
        boolean dateOfEventIsValid = false;
        boolean shownCommandLineArgumentWarning = false;

        do {

            try {
                // throw and then catch any exceptions

                // warn the user once that there must be a command-line argument for the input file in order to add data
                if (args.length == 0 && !shownCommandLineArgumentWarning) {
                    shownCommandLineArgumentWarning = true;

                    throw new UnknownTransactionException("\nWarning: there are no command-line arguments for the sales."
                                                        + "\nData cannot be added without one.\n");
                }

                // start checking the flags for program continuation after an exception is triggered
                if (!clientNameIsValid) {
                    PrintMessage.showAddRetrieveQuit();
                    option = CheckInput.checkAddRetrieveQuit();
                }

                if (option.equalsIgnoreCase("a")) {
                    // throw and then catch any exceptions; if no exception is triggered, update the flags

                    // use the file name within the command-line argument
                    if (args.length > 0) {
                        fileNameForSales = args[0];
                        fileForSales = new File(args[0]);
                    } else {
                        // if there is no command-line argument, send an error message stating no data can be written
                        throw new UnknownTransactionException("\nNo command-line argument was found." +
                                                            "\nNo data can be written.");
                    }

                    if (!clientNameIsValid) {
                        PrintMessage.enterClientName();
                        clientName = CheckInput.checkString();
                        clientNameIsValid = true;
                    }

                    if (!serviceSoldIsValid) {
                        PrintMessage.enterServiceSold();
                        serviceSold = CheckInput.checkService();
                        serviceSoldIsValid = true;
                    }

                    if (!amountOfSaleIsValid) {
                        PrintMessage.enterAmountOfSale();
                        amountOfSale = CheckInput.checkDouble();
                        amountOfSaleIsValid = true;
                    }

                    if (!dateOfEventIsValid) {
                        PrintMessage.enterDateOfEvent();
                        dateOfEvent = CheckInput.checkDate();
                        dateOfEventIsValid = true;
                    }

                    // begin file i/o

                    // now creating file name for the service, if it doesn't exist
                    fileNameForService = serviceSold + ".txt";
                    fileForService = new File(fileNameForService);

                    try {
                        // check if the input file actually exists; if not, warn the user and create one for them
                        if (!fileForSales.isFile()) {
                            throw new FileNotFoundException(fileNameForSales);
                        }

                        // the input file exists; now write the data into it
                        // only write into this file once if the service did not exist before
                        if (!alreadyWrittenToFile) {
                            writeToFile(fileForSales, clientName, serviceSold, amountOfSale, dateOfEvent);

                            alreadyWrittenToFile = true;
                        }

                        // check if the service file actually exists; if not, warn the user and create one for them
                        if (!fileForService.isFile()) {
                            throw new FileNotFoundException(fileNameForService);
                        }

                        // the service file exists; now read the data from the input file and write to the service file
                        readAndWriteToServiceFile(fileForSales, fileForService);

                        // if we've made it this far, everything was successful; reset the flags
                        clientNameIsValid = false;
                        serviceSoldIsValid = false;
                        amountOfSaleIsValid = false;
                        dateOfEventIsValid = false;
                        alreadyWrittenToFile = false;

                    } catch (FileNotFoundException fileReadError) {
                        PrintMessage.fileNotFoundError(fileReadError.getMessage());

                        // create the file for the user
                        file = new File(fileReadError.getMessage());
                        file.createNewFile();
                    }
                } else if (option.equalsIgnoreCase("r")) {

                    // check if there are `.txt` files to begin with
                    ArrayList<File> arrayListOfTxtFiles = getTxtFilesInDirectory();

                    if (arrayListOfTxtFiles.size() == 0) {
                        throw new FileNotFoundException(".txt");
                    }

                    // begin attempting to retrieve data from the files
                    retrievedData = readFromFiles(arrayListOfTxtFiles);

                    // if we've made it this far, data was successfully retrieved; print it out
                    PrintMessage.displayRetrievedData(retrievedData);
                }

            } catch (FileNotFoundException error) {
                PrintMessage.noFileTypeExistsError(error.getMessage());
            } catch (IOException error) {
                PrintMessage.fileNotReadableError(error.getMessage());
            } catch (InputMismatchException error) {
                PrintMessage.invalidInputError(error.getMessage());
            } catch (ParseException error) {
                PrintMessage.invalidDateError();
            } catch (UnknownTransactionException error) {
                PrintMessage.unknownTransactionError(error.getMessage());

                // allow the user to escape the loop if needed; reset the flags
                if (!error.getMessage().contains("not a valid service")) {
                    clientNameIsValid = false;
                    serviceSoldIsValid = false;
                    amountOfSaleIsValid = false;
                    dateOfEventIsValid = false;
                    alreadyWrittenToFile = false;
                }
            }

        } while (!option.equalsIgnoreCase("q"));


        // testing is done
        PrintMessage.completedIO();
    }

    /**
     * Maintains and returns the list of services offered by the hotel.
     *
     * @return the array of services of type String[]
     */
    public static String[] getHotelServices() {

        return new String[]{"Bar", "Breakfast", "Conference",
                            "Dinner", "Gym", "Lodging",
                            "Lunch", "Spa", "Special Event"};
    }

    /**
     * Returns the ArrayList of `.txt` files in the directory.
     *
     * @return the ArrayList of `.txt` files in the directory
     */
    public static ArrayList<File> getTxtFilesInDirectory() {
        File files = new File(System.getProperty("user.dir"));
        File[] listOfFiles = files.listFiles();
        assert listOfFiles != null;
        ArrayList<File> arrayListOfTxtFiles = new ArrayList<File>();

        for (File file : listOfFiles) {
            if (file.getName().contains(".txt")) {
                arrayListOfTxtFiles.add(file);
            }
        }
        return arrayListOfTxtFiles;
    }

    /**
     * Writes to a file using the given parameters.
     *
     * @param fileToWrite the file to write to
     * @param clientName the name of the client
     * @param serviceSold the service sold
     * @param amountOfSale the amount of the sale
     * @param dateOfEvent the date of the event
     */
    public static void writeToFile(File fileToWrite, String clientName, String serviceSold, String amountOfSale,
                                   String dateOfEvent) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            fileWriter.write(clientName + ";" +
                            serviceSold + ";" +
                            amountOfSale + ";" +
                            dateOfEvent + "\n");
        }
    }

    /**
     * Parses the last line of the given file and writes it to the service file.
     *
     * @param fileToRead the file to read from
     * @param fileToWrite the file to write to
     */
    public static void readAndWriteToServiceFile(File fileToRead, File fileToWrite) throws IOException {
        String line = null;
        String nextLine;

        try (BufferedReader bReader = new BufferedReader(new FileReader(fileToRead))) {

            // proceed until the reader reaches the last line
            while ((nextLine = bReader.readLine()) != null) {
                line = nextLine;
            }
        }

        // parse the line into its appropriate fields
        assert line != null;
        String[] parsedLine = line.split(";");

        // write into the service file using the appropriate fields
        writeToFile(fileToWrite, parsedLine[0], parsedLine[1], parsedLine[2], parsedLine[3]);
    }

    /**
     * Reads from files using the given ArrayList of files, parses the data, and then returns the parsed data.
     *
     * @param arrayListOfFiles the ArrayList of files to read from
     * @return the parsed data as a string
     */
    public static String readFromFiles(ArrayList<File> arrayListOfFiles) throws IOException {
        StringBuilder retrievedData = new StringBuilder();
        String line;

        for (File fileToRead : arrayListOfFiles) {
            try (BufferedReader bReader = new BufferedReader(new FileReader(fileToRead))) {

                retrievedData.append("- ").append(fileToRead.getName()).append("\n");

                while ((line = bReader.readLine()) != null) {
                    retrievedData.append("\t").append(line.replace(";", " - ")).append("\n");
                }
            }
        }
        return retrievedData.toString();
    }
}
