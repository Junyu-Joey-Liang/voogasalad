package networking.router.client;

import javafx.scene.control.Alert;
import networking.router.messages.ServerMessage;
import networking.socialcenter.ClientInterface;

import java.net.Socket;

/**
 * This class acts as a client connecting to a server. It runs both a read thread and a write thread that take in and output data, respectively.
 * This class is not the actual application, but just the role of client connection in the server/client interaction pattern. The ClientInterface
 * taken in as a parameter is the actual client application program holding the client connection.
 */
public class Client {
    private static final String ERROR_CONNECTING_TO_SERVER_MESSAGE = "Error connecting to server...Please restart the application.";

    private String hostname;
    private int port;
    private WriteThread writeThread;
    private ClientInterface clientInterface;

    /**
     * Constructor for client class
     *
     * @param hostname String name of server hostname
     * @param port Integer port number on which the client will connect to the server
     * @param clientInterface Client application that desires network connection with the server
     */
    public Client(String hostname, int port, ClientInterface clientInterface) {
        this.hostname = hostname;
        this.port = port;
        this.clientInterface = clientInterface;
    }

    /**
     * This method is called to initiate connection to the server. It does not take in any parameters because everything needed was given in the
     * constructor. Since this is part of the client frontend, then in the event the server cannot be connected to, a JavaFX alert will be displayed.
     */
    public void execute() {
        ReadThread readThread;
        try {
            Socket socket = new Socket(hostname, port);
            readThread = new ReadThread(socket, clientInterface);
            writeThread = new WriteThread(socket);
            readThread.start();
            writeThread.start();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,ERROR_CONNECTING_TO_SERVER_MESSAGE).showAndWait();
        }
    }

    /**
     * Tells the client to send a ServerMessage to the server
     *
     * @param message ServerMessage containing whatever command the client wishes for the server to execute.
     */
    public void sendMessage(ServerMessage message) {
        writeThread.sendMessage(message);
    }
}
