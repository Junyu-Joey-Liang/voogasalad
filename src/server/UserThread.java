package server;

import networking.router.messages.ClientMessage;
import networking.router.messages.ServerMessage;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Thread which listens for client messages and executes the client messags on the server.
 */
public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private ObjectOutputStream output;
    private int threadID;
    private boolean exit = false;

    /**
     * Constructor for UserThread.
     *
     * @param threadID Integer CPU thread ID server will use to refer to this thread.
     * @param socket Socket over which server and client send data on the network
     * @param server Server interface passed in to client to server command messages.
     */
    public UserThread(int threadID, Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.threadID = threadID;
    }

    /**
     * Since this class extends Thread, it requires a run() method.
     * Listens indefinitely for client messages.
     */
    public void run() {
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            do {
                ServerMessage message = (ServerMessage)input.readObject();
                message.execute(server, threadID);
            } while (!exit);

            socket.close();

        } catch (Exception e) {
            // Note these don't send messages to a front end because the remote server has no front end
            System.out.println("Server could not connect to user...");
        }
    }

    /**
     * Sends message over the output stream from server to client.
     *
     * @param message ClientMessage to be sent over the network.
     */
    public void sendMessage(ClientMessage message) {
        try {
            output.writeObject(message);
        } catch (Exception e) {
            // Note these don't send messages to a front end because the remote server has no front end
            System.out.println("Server could not send message...");
        }
    }

    /**
     * Ends this thread.
     */
    public void endThread() {
        exit = true;
    }
}