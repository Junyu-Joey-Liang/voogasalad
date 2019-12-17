package server;

/**
 * Main class for server. This main class runs on the remote server.
 * The remote server hostname is printstacktrace-server.colab.duke.edu
 */
public class ServerMain {

    /**
     * Main method for running the main on the server.
     *
     * @param args Variable command line arguments.
     */
    public static void main(String args[]) {
        RemoteServer remoteServer = new RemoteServer(ServerConstants.SERVER_PORT);
        remoteServer.start();
    }
}
