package networking.router.messages.servermessages;

import networking.router.messages.ClientMessage;
import networking.router.messages.ServerMessage;
import networking.router.messages.clientmessages.ChatConfirmationMessage;
import server.Server;
import server.managers.ChatManager;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server that a user is joining a given chat.
 */
public class JoinChatMessage implements ServerMessage, Serializable {
    private String chatName;
    private String user;

    /**
     * Constructor for JoinChatMessage
     *
     * @param chatName String name of chat the user wishes to joing
     * @param username String username of user joining the chat
     */
    public JoinChatMessage(String chatName, String username) {
        this.chatName = chatName;
        this.user = username;
    }

    /**
     * Execute method for command object. Tells the server that a given username is joining a given chat thread.
     *
     * @param server Server interface on which the command will be executed.
     * @param threadID Integer thread ID (CPU thread not chat thread) on which client and server are communicating
     */
    @Override
    public void execute(Server server, int threadID) {
        ChatManager chatManager = server.getChatManager();

        ClientMessage returnMessage = new ChatConfirmationMessage(chatName, chatManager.joinChat(user, chatName, threadID));
        server.sendMessage(returnMessage, threadID);
    }
}
