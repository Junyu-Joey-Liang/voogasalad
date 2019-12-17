package chat;

import networking.Chat;
import networking.Router;

public class ChatExample {

    public ChatExample() {
        Player1 p1 = new Player1();
        Player2 p2 = new Player2();

        p1.sendMessage("Hello world");
        p2.printMessage();
    }

    class Player1 {
        Chat chat = new TextChat();

        void sendMessage(String message) {
            chat.send(message);
        }
    }

    class Player2 {
        Chat chat = new TextChat();

        void printMessage() {
            chat.getNode();
        }
    }
}
