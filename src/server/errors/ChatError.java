package server.errors;

/**
 * Error class thrown in the event that something goes wrong with handling a chat.
 */
public class ChatError extends RuntimeException {

    /**
     * Constructor for ChatError
     *
     * @param message String message for error
     * @param values Variable number of arguments related to error
     */
    public ChatError (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Constructor for ChatError
     *
     * @param cause Throwable cause of error
     * @param message String error message
     * @param values Variable number of objects related to error
     */
    public ChatError (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Constructor for ChatError
     *
     * @param cause Throwable cause of error.
     */
    public ChatError (Throwable cause) {
        super(cause);
    }
}
