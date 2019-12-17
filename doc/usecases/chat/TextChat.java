package chat;

import javafx.scene.Node;
import networking.Chat;
import networking.Message;
import networking.Router;

public class TextChat implements Chat {
    Router router = new NetworkRouter();

    public void send(Object content) {
        Message message = new TextMessage();
        message.addContent(content);
        message.addSender(this.getClass());
        router.send(message);
    }

    public void addMessageToHistory(String username, String message) {

    }

    public Node getNode() {
        return null;
    }
}
