package voogasalad.data.exception;

/**
 * This class is used as a custom exception thrown by the Data API in case their is a problem during
 * the conversion, saving, and/or loading processes.
 *
 * @author Justin Havas
 */
public class UnknownDataException extends RuntimeException {

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public UnknownDataException (Throwable cause) {
        super(cause);
    }
}
