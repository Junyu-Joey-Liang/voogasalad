package server;

import networking.router.messages.ClientMessage;
import server.managers.AccountManager;
import server.managers.ChatManager;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Map;
import java.util.HashMap;

/**
 * Class which acts as main action coordinator for server. This handles client threads and managers.
 */
public class RemoteServer extends Thread implements Server{
    private int port;

    private AccountManager accountManager;
    private ChatManager chatManager;
    private Map<Integer, UserThread> userThreads;

    /**
     * Constructor for RemoteServer.
     *
     * @param port Integer port number on which the server will be listening for client connection requests.
     */
    public RemoteServer(int port) {
        this.port = port;

        accountManager = new AccountManager();
        chatManager = new ChatManager(this);

        userThreads = new HashMap<>();

        printHostnameAndIP();
        printPortNumber();
    }

    /**
     * Since extends thread then it requires a run() method.
     * Listens indefinitely for client connection requests then creates user threads to handling the client.
     */
    public void run() {
        int threadID = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                UserThread newUser = new UserThread(threadID, socket, this);
                userThreads.put(threadID, newUser);
                newUser.start();
                threadID++;
            }
        } catch (Exception e) {
            // Note these don't send messages to a front end because the remote server has no front end
            System.out.println("Server could not connect to socket. Please restart server");
        }
    }

    /**
     * Allows for retrieval of account manager.
     *
     * @return AccountManager for managing accounts on the server
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * Allows for retrieval of chat manager
     *
     * @return ChatManager for managing chats on the server
     */
    public ChatManager getChatManager() {
        return chatManager;
    }

    /**
     * Sends a message from the server to the cleint.
     *
     * @param message ClientMessage to be sent to client
     * @param threadNum Integer CPU thread ID on which the server communicates with the client.
     */
    public void sendMessage(ClientMessage message, int threadNum) {
        userThreads.get(threadNum).sendMessage(message);
    }

    /**
     * Sends the given message to all CPU threads connected to the server.
     *
     * @param message ClientMessage to be sent from server to client.
     */
    public void sendToAllThreads(ClientMessage message) {
        for (int threadID : userThreads.keySet()) {
            userThreads.get(threadID).sendMessage(message);
        }
    }

    /**
     * Disconnects the given thread from the server to no longer listen for messages.
     *
     * @param threadID Integer CPU thread ID.
     */
    public void signOutUser(int threadID) {
        userThreads.get(threadID).endThread();
        userThreads.remove(threadID);
    }

    private void printHostnameAndIP() {
        try {
            System.out.println(InetAddress.getLocalHost().toString());
        } catch (Exception e) {
            // Note these don't send messages to a front end because the remote server has no front end
            System.out.println("Error getting hostname and IP!");
        }
    }

    private void printPortNumber() {
        System.out.println(port);
    }
}

