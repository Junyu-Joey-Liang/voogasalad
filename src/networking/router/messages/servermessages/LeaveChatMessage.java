package networking.router.messages.servermessages;

import networking.router.messages.ServerMessage;
import server.Server;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server that a given username is leaving a given chat.
 */
public class LeaveChatMessage implements ServerMessage, Serializable {
    private String chatName;
    private String user;

    /**
     * Constructor for LeaveChatMessage
     *
     * @param chat String name of chat the user wishes to leave
     * @param username String username of user leaving
     */
    public LeaveChatMessage(String chat, String username) {
        chatName = chat;
        user = username;
    }

    /**
     * Execute method for command object. Tells the server to remove the given user from the given chat thread.
     *
     * @param server Server interface on which command will be executed
     * @param threadID Integer thread id (CPU thread not chat thread) on which client and server are communicating
     */
    @Override
    public void execute(Server server, int threadID) {
        server.getChatManager().leaveChat(user, chatName);
    }
}
