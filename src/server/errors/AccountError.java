package server.errors;

/**
 * Error class thrown in the event that something goes wrong with handling an account.
 */
public class AccountError extends RuntimeException {

    /**
     * Constructor for AccountError
     *
     * @param message String message for error
     * @param values Variable number of arguments related to error
     */
    public AccountError (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Constructor for AccountError
     *
     * @param cause Throwable cause of error
     * @param message String error message
     * @param values Variable number of objects related to error
     */
    public AccountError (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Constructor for AccountError
     *
     * @param cause Throwable cause of error.
     */
    public AccountError (Throwable cause) {
        super(cause);
    }

}
