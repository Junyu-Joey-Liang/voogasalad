package voogasalad.authoring.controller.levelbuilder;

public class LevelConfigurationException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public LevelConfigurationException(Throwable cause, String message, Object... values) {
        super(String.format(message, values), cause);
    }

}
