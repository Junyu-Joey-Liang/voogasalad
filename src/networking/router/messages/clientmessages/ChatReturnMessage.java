package networking.router.messages.clientmessages;

import networking.router.messages.ClientMessage;
import networking.socialcenter.accountdisplay.AccountDisplayPage;
import networking.socialcenter.ClientInterface;

import java.io.Serializable;

/**
 * Once a user has sent a message on a thread, this class is sent back to all clients on that thread to tell it that a message is being sent from the
 * server, and that the clients should update their views with the newest message.
 */
public class ChatReturnMessage implements ClientMessage, Serializable {
    private String message;
    private String chatID;

    /**
     * Constructor for ChatReturn Message
     *
     * @param message String message being sent from server (either originally from another client or from the server itself)
     * @param chat String representing chat name to which the message should be added
     */
    public ChatReturnMessage(String message, String chat) {
        this.message = message;
        chatID = chat;
    }

    /**
     * Execute method for command objects. Tells the client to add the given message ot the given chat's history.
     *
     * @param clientInterface Client application interface on which command is executed.
     */
    @Override
    public void execute(ClientInterface clientInterface) {
        AccountDisplayPage accountDisplayPage = clientInterface.getAccountDisplayPage();
        accountDisplayPage.addMessage(message, chatID);
    }
}
