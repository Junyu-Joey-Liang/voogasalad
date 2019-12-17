package networking;

import javafx.scene.Node;

import java.io.File;

/**
 * Used for creating and interacting with chat room. The authoring environment will communicate with this external API of the networking module to send custom messages and
 * receive errors.
 */
public interface Chat extends NetworkedItem {

    /**
     * Used to send a custom object through the chat room to the other person. The internal API will have a way of sending messages but this will be a scalable way to send other
     * things from the voogasalad.player environment.
     *
     * @param content Object to be sent to other person
     */
    void send(Object content);

    /**
     * Adds a message to the history of messages in the chat room
     *
     * @param username String username of user who sent the message
     * @param message String message content
     */
    void addMessageToHistory(String username, String message);

    /**
     *
     * @return Root node object for displaying the chat room
     */
    Node getNode();
}
