package networking.router.messages.clientmessages;

import networking.router.messages.ClientMessage;
import networking.socialcenter.ClientInterface;

import java.io.Serializable;

/**
 * Command object sent from server to client to indicate that there some was error that occurred in executing the client's most recent command.
 * Often used to tell client that username/password combination was incorrect, username already exists, etc.
 */
public class ClientErrorMessage implements ClientMessage, Serializable {
    private String errorMessage;

    /**
     * Constructor for ClientErrorMessage
     *
     * @param message String with message about the error that occurred
     */
    public ClientErrorMessage(String message) {
        errorMessage = message;
    }

    /**
     * Execute command for command object. Tells the client to display an error message with given message.
     *
     * @param clientInterface Client application interface on which command is executed.
     */
    @Override
    public void execute(ClientInterface clientInterface) {
        clientInterface.displayErrorMessage(errorMessage);
    }
}