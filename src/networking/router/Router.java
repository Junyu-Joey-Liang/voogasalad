package networking.router;

import networking.router.client.Client;
import networking.router.messages.ServerMessage;
import networking.socialcenter.ClientInterface;
import server.ServerConstants;

/**
 * Router class through which all messages are sent. This handles the network client object.
 */
public class Router {
    /**
     * Name of server running on a Duke virtual machine
     */
    private static final String SERVER_HOSTNAME = "printstacktrace-server.colab.duke.edu";

    private Client client;

    /**
     * Constructor for Router
     *
     * @param clientInterface client application interface which will be passed to network client read and write threads.
     */
    public Router(ClientInterface clientInterface) {
        client = new Client(SERVER_HOSTNAME, ServerConstants.SERVER_PORT, clientInterface);
        client.execute();
    }

    /**
     * Used by the client application to send messages over the network.
     *
     * @param message ServerMessage to be send to server over the network.
     */
    public void sendMessage(ServerMessage message) {
        client.sendMessage(message);
    }
}
