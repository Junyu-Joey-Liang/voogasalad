package server.errors;

/**
 * Error class thrown in the event that something goes wrong with handling an online game.
 */
public class GameError extends RuntimeException {

    /**
     * Constructor for GameError
     *
     * @param message String message for error
     * @param values Variable number of arguments related to error
     */
    public GameError (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Constructor for GameError
     *
     * @param cause Throwable cause of error
     * @param message String error message
     * @param values Variable number of objects related to error
     */
    public GameError (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Constructor for GameError
     *
     * @param cause Throwable cause of error.
     */
    public GameError (Throwable cause) {
        super(cause);
    }
}
