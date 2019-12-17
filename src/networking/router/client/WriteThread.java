package networking.router.client;

import javafx.scene.control.Alert;
import networking.router.messages.ServerMessage;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class responsible for sending messages over the network. This class is primarily used to send Server Message Commands to the server to be executed.
 * This class runs on its own thread.
 */
public class WriteThread extends Thread {
    private static final String ERROR_OUTPUT_STREAM = "Cannot send data to the network at this time.";

    private ObjectOutputStream output;
    private Socket socket;

    /**
     * Constructor for WriteThread class.
     *
     * @param socket Socket object on which outgoing messages will be sent to the network.
     */
    public WriteThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Since this class extends the Thread Abstract Class then it must implement a run() method. This is used to start up the connection to the server
     * allowing the client application to send messages. Unlike most networking run() threads, this does not contain an infinite loop as the sending
     * to the server is done asynchronously.
     */
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,ERROR_OUTPUT_STREAM).showAndWait();
        }
    }

    /**
     * Tells the write thread to send messages to the server. This is an asynchronous function.
     *
     * @param message ServerMessage command to be sent to and executed on the server.
     */
    public void sendMessage(ServerMessage message) {
        try {
            output.writeObject(message);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,ERROR_OUTPUT_STREAM).showAndWait();
        }

    }
}