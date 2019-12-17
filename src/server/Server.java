package server;

import networking.router.messages.ClientMessage;
import server.managers.AccountManager;
import server.managers.ChatManager;

/**
 * Interface responsible for handling how the server can be interacted with by other classes.
 */
// printstacktrace-server.colab.duke.edu
public interface Server {

    /**
     * Allows for retrieval of account manager.
     *
     * @return AccountManager for managing accounts on the server
     */
    AccountManager getAccountManager();

    /**
     * Allows for retrieval of chat manager
     *
     * @return ChatManager for managing chats on the server
     */
    ChatManager getChatManager();

    /**
     * Sends a message from the server to the cleint.
     *
     * @param message ClientMessage to be sent to client
     * @param threadNum Integer CPU thread ID on which the server communicates with the client.
     */
    void sendMessage(ClientMessage message, int threadNum);

    /**
     * Sends the given message to all CPU threads connected to the server.
     *
     * @param message ClientMessage to be sent from server to client.
     */
    void sendToAllThreads(ClientMessage message);

    /**
     * Disconnects the given thread from the server to no longer listen for messages.
     *
     * @param thread Integer CPU thread ID.
     */
    void signOutUser(int thread);
}
