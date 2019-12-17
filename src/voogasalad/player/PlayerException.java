package voogasalad.player;

public class PlayerException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1234L;

    /**
     * Create an exception based on an issue in our code.
     */
    public PlayerException (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public PlayerException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public PlayerException (Throwable exception) {
        super(exception);
    }
}
