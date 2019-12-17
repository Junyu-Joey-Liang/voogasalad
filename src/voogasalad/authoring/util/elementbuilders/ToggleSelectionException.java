package voogasalad.authoring.util.elementbuilders;

public class ToggleSelectionException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on an issue in our code.
     */
    public ToggleSelectionException(String message, Object... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ToggleSelectionException(Throwable cause, String message, Object... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ToggleSelectionException(Throwable cause) {
        super(cause);
    }

    public ToggleSelectionException() {
        super();
    }
}
