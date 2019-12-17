package networking.router.client;

import javafx.scene.control.Alert;
import networking.router.messages.ClientMessage;
import networking.socialcenter.ClientInterface;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * This class is responsible for reading messages from over the network and executing those messages on the client side application running.
 * Data is taken in as an ObjectInputStream so that more than just strings can be send over the network.
 */
public class ReadThread extends Thread {
    private static final String ERROR_SETTING_UP_INPUT_STREAM = "Cannot read data from the network at this time.";
    private static final String ERROR_EXECUTING_MESSAGE_FROM_SERVER = "Received message from network but message was incorrectly formatted";

    private ObjectInputStream input;
    private Socket socket;
    private ClientInterface clientInterface;

    /**
     * Constructor for ReadThread class.
     *
     * @param socket Socket object from the input stream will be read
     * @param clientInterface Client application on which the client side command messages will be executed
     */
    public ReadThread(Socket socket, ClientInterface clientInterface) {
        this.socket = socket;
        this.clientInterface = clientInterface;
    }

    /**
     * This class runs on its own thread, which means it requires a run() method. Call this method when ready to start listening for
     * messages over the network.
     */
    public void run() {
        try {
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,ERROR_SETTING_UP_INPUT_STREAM).showAndWait();
        }
        while (true) {
            try {
                ClientMessage message = (ClientMessage)input.readObject();
                message.execute(clientInterface);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,ERROR_EXECUTING_MESSAGE_FROM_SERVER).showAndWait();
                break;
            }
        }
    }
}
