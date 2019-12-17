package chat;

import networking.Message;
import networking.NetworkedItem;
import networking.Router;

public class NetworkRouter implements Router {

    public NetworkRouter() {

    }

    public void addNetworkedItem(NetworkedItem item) {}

    public int addConnection(String IP, int port) {
        return 0;
    }

    public void send(Message content) {
        // code to send message content over a port
    }

    public String getIP() {
        return "";
    }


    public int getPort() {
        return 0;
    }

    private void listenForMessage() {
        // listens for messages and upates the appropriate class
    }
}
