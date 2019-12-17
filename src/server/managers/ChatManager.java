package server.managers;

import networking.router.messages.ClientMessage;
import networking.router.messages.clientmessages.AddThreadMessage;
import networking.router.messages.clientmessages.ChatReturnMessage;
import server.Server;
import server.ServerConstants;
import server.errors.ChatError;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class that manages the chats the are running on the current server.
 */
public class ChatManager extends Manager {
    private static final String NEW_USER_JOINED_MESSAGE_BODY = " joined thread ";
    private static final String USER_LEFT_MESSAGE_BODY = " left thread ";

    private Map<String, Set<String>> chatToUsers;
    private Map<String, Integer> userToThread;
    private Map<String, List<String>> chatToHistory;
    private Server server;

    /**
     * Constructor for ChatManager.
     *
     * @param server Server interface on which commands are executed.
     */
    public ChatManager(Server server) {
        this.server = server;
        chatToUsers = new HashMap<>();
        userToThread = new HashMap<>();
        chatToHistory = new HashMap<>();
    }

    /**
     * Says that a user is joining a certain chat.
     *
     * @param username String value of username
     * @param chatID String value of chat that the username is joining
     * @param threadID Integer CPU thread ID with which server communicates with client.
     * @return List of String messages in chat history
     */
    public List<String> joinChat(String username, String chatID, int threadID) {
        addUserToChat(chatID, username);

        if (!chatToUsers.get(chatID).contains(username)) {
            String userJoinedMessage = username + NEW_USER_JOINED_MESSAGE_BODY + chatID;
            chatToHistory.get(chatID).add(userJoinedMessage);
        }
        userToThread.put(username, threadID);

        return chatToHistory.get(chatID);
    }

    /**
     * Says that the given user is leaving the given chat.
     *
     * @param username String value of username
     * @param chatID String value of chat that the user is leaving.
     */
    public void leaveChat(String username, String chatID) {
        String userLeftMessage = username + USER_LEFT_MESSAGE_BODY + chatID;
        chatToHistory.get(chatID).add(userLeftMessage);
        chatToUsers.get(chatID).remove(username);
        userToThread.remove(username);
    }

    /**
     * Sends a message to all the users in a given chat.
     *
     * @param username String value of username of the user sending the message.
     * @param message String value of message content.
     * @param chatName String value of chat on which the message is being sent.
     */
    public void sendMessage(String username, String message, String chatName) {
        try {
            String chatMessage = username + ServerConstants.USERNAME_DELIMITER + message;
            chatToHistory.get(chatName).add(chatMessage);
            for (String user : chatToUsers.get(chatName)) {
                ClientMessage returnMessage = new ChatReturnMessage(chatMessage, chatName);
                server.sendMessage(returnMessage, userToThread.get(user));
            }
        } catch (Exception e) {
            throw new ChatError(e.getMessage());
        }

    }

    /**
     * Allows for retrieval of all the chats this server is running.
     *
     * @return List of String chat names on this server.
     */
    public List<String> getThreads() {
        return new ArrayList<>(chatToUsers.keySet());
    }

    private boolean chatExists(String chatID) {
        return chatToUsers.containsKey(chatID);
    }

    private void addUserToChat(String chatID, String username) {
        if (!chatExists(chatID)) {
            Set<String> users = new HashSet<>();
            users.add(username);
            chatToUsers.put(chatID, users);
            chatToHistory.put(chatID, new ArrayList<>());

            ClientMessage message = new AddThreadMessage(chatID);
            server.sendToAllThreads(message);
        } else {
            chatToUsers.get(chatID).add(username);
        }
    }

}
