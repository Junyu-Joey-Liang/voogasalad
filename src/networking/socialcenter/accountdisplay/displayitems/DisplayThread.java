package networking.socialcenter.accountdisplay.displayitems;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import networking.socialcenter.accountdisplay.AccountDisplay;
import networking.socialcenter.accountdisplay.AccountDisplayPiece;
import server.ServerConstants;

import java.util.List;

/**
 * Class responsible for displaying the currently selected thread. This class shows messages from other users and current users.
 * It also allows for the user to send messages to other users.
 */
public class DisplayThread implements AccountDisplayPiece {
    private static final String SEND_BUTTON_TEXT = "Send";
    private GridPane history;
    private BorderPane thread;
    private TextArea messageArea;
    private AccountDisplay accountDisplay;
    private String threadName;

    /**
     * Constructor for DisplayThread class.
     *
     * @param accountDisplay Account display which handles all the display pieces.
     * @param name String name of current thread.
     * @param pastMessages List of string messages contained in this thread's history.
     */
    public DisplayThread(AccountDisplay accountDisplay, String name, List<String> pastMessages) {
        this.accountDisplay = accountDisplay;
        threadName = name;

        history = new GridPane();
        history.getStyleClass().add("message-thread");
        populateHistory(pastMessages);
        thread = new BorderPane();
        thread.setTop(new Text(name));
        thread.getStyleClass().add("thread-header");
        //https://stackoverflow.com/questions/13156896/javafx-auto-scroll-down-scrollpane
        ScrollPane scrollPane = new ScrollPane(history);
        scrollPane.vvalueProperty().bind(history.heightProperty());
        thread.setCenter(scrollPane);
        setUpOutbox();
    }

    /**
     * Tells the current thread display to add a message to the thread's history. This can be used to add a messages sent by
     * the current user or to add a message sent by another user locally or over a network.
     *
     * @param message String name of message.
     * @param chatID String name of chat on which the message should be added. If the given thread is not the name of this thread
     *               then message is not added to the thread history.
     */
    public void addMessage(String message, String chatID) {
        if (threadName.equals(chatID)) {
            Label text = new Label(message);
            text.setWrapText(true);
            StackPane messageBox = new StackPane();
            messageBox.getChildren().addAll(new Rectangle(), text);
            if (message.split(ServerConstants.USERNAME_DELIMITER)[0].equals(accountDisplay.getUsername())) {
                messageBox.getStyleClass().add("current-user-message");
            } else {
                messageBox.getStyleClass().add("remote-message");
            }
            history.add(messageBox, 0, history.getRowCount());
        }
    }

    /**
     * Allows account display to get the node of the current display thread to be displayed in a scene.
     *
     * @return root of the thread display
     */
    public Node getNode() {
        return thread;
    }

    private void populateHistory(List<String> pastMessages) {

        pastMessages.forEach(message ->  {
            addMessage(message, threadName);
        });
    }

    private void setUpOutbox() {
        GridPane outbox = new GridPane();
        messageArea = new TextArea();
        messageArea.getStyleClass().add("message-area");
        outbox.add(messageArea, 0, 0);
        Button sendButton = new Button(SEND_BUTTON_TEXT);
        sendButton.setOnAction(event -> sendMessage());
        outbox.add(sendButton, 1, 0 );

        thread.setBottom(outbox);
    }

    private void sendMessage() {
        accountDisplay.sendMessage(messageArea.getText(), threadName);
        messageArea.clear();
    }
}
