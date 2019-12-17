package networking.router.messages.servermessages;

import networking.router.messages.ServerMessage;
import server.Server;
import server.managers.ChatManager;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server that the client is sending a message on a given chat.
 */
public class ChatMessage implements ServerMessage, Serializable {
    private String message;
    private String chat;
    private String username;

    /**
     * Constructor for ChatMessage
     *
     * @param username String username of account sending message on client side
     * @param message String message being sent by said user
     * @param chat String name of chat on which the message is being sent
     */
    public ChatMessage(String username, String message, String chat) {
        this.username = username;
        this.message = message;
        this.chat = chat;
    }


    /**
     * Execute method for command object. Tells the server to add the given message to the given chat's history. Then to send the
     * message to everyone on the chat.
     *
     * @param server Server interface on which command will be executed.
     * @param threadID ID of thread on which the current client is communicated. This is a CPU thread ID, NOT a chat thread ID
     */
    @Override
    public void execute(Server server, int threadID) {
        ChatManager chatManager = server.getChatManager();
        chatManager.sendMessage(username, message, chat);
    }
}
