package networking;

/**
 * Used for setting up connections with other routers for online chat and online multiplayer
 */
public interface Router {

    /**
     * Adds and item to the list of networked parts of the game for internal routing table
     *
     * @param item Item in game that is connected to the network
     */
    void addNetworkedItem(NetworkedItem item);

    /**
     * Adds connection to external routing table
     *
     * @param IP String IP address of external connection
     * @param port Integer port number for machine
     * @return Returns -1 if connection failed and number >=0 otherwise
     */
    int addConnection(String IP, int port);

    /**
     * Sends message to all other routers in external routing table
     *
     * @param content Message that will be sent to all the other routers
     */
    void send(Message content);

    /**
     * @return Returns the string IP address of this router
     */
    String getIP();

    /**
     * @return Returns integer value of port being used for this router
     */
    int getPort();
}
