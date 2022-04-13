
/**
 * Required custom exception for handling unknown transactions.
 */
public class UnknownTransactionException extends Exception {

    /**
     * Default constructor for the exception.
     */
    public UnknownTransactionException() {
        super();
    }

    /**
     * Overloaded constructor for the exception.
     *
     * @param errorMessage the error message to display
     */
    public UnknownTransactionException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Displays the contents of the exception's error message.
     */
    public String toString() {
        return this.getMessage();
    }
}
