package networking.socialcenter.accountdisplay.displayitems;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import networking.socialcenter.accountdisplay.AccountDisplay;
import networking.socialcenter.accountdisplay.AccountDisplayPiece;

import java.util.List;

/**
 * This is a display class. It is responsible for displaying the available chat threads on which the user can send messages.
 * This class is also responsible for allowing the user to add threads and join threads.
 */
public class DisplayAvailableThreads implements AccountDisplayPiece {
    private static final String AVAILABLE_THREADS_DESCRIPTION = "Available Threads";
    private static final String MAKE_THREAD_DESCRIPTION = "Thread Title";
    private static final String BUTTON_TEXT = "Create";

    private BorderPane borderPane;
    private ListView<String> threads;
    private TextField threadField;
    private Button threadButton;
    private AccountDisplay accountDisplay;

    /**
     * Constructor for DisplayAvailableThreads.
     *
     * @param accountDisplay Account display object holding all the display pieces for the account
     */
    public DisplayAvailableThreads(AccountDisplay accountDisplay) {
        this.accountDisplay = accountDisplay;

        borderPane = new BorderPane();
        Text description = new Text(AVAILABLE_THREADS_DESCRIPTION);
        description.getStyleClass().add("header");
        borderPane.setTop(description);
        threads = new ListView<>();
        threads.getStyleClass().add("list-body");
        threads.setOnMouseClicked(thread -> joinThread());
        borderPane.setCenter(threads);

        GridPane makeThreadBox = new GridPane();
        threadField = new TextField();
        threadField.setPromptText(MAKE_THREAD_DESCRIPTION);
        makeThreadBox.add(threadField, 0, 0);
        threadButton = new Button(BUTTON_TEXT);
        threadButton.getStyleClass().add("button");
        threadButton.setOnAction(event -> createThread());
        makeThreadBox.add(threadButton, 1, 0);
        borderPane.setBottom(makeThreadBox);
    }

    /**
     * Tells the class to display another available thread to the user. This thread might have been created by the user or it might
     * have been created by a different user on the network and the server is telling this user to display it as an available option.
     *
     * @param threadName String name of new thread.
     */
    public void addThread(String threadName) {
        if (!threads.getItems().contains(threadName)) {
            threads.getItems().add(threadName);
        }
    }

    /**
     * Allows for retrieval of the list of available threads.
     *
     * @return List of String values representing available thread names.
     */
    public List<String> getThreads() {
        return threads.getItems();
    }

    /**
     * @return Root node of this display piece so that it can be displayed in the account display page.
     */
    public Node getNode() {
        return borderPane;
    }

    /**
     * Selects the given thread as the current thread on which the user is messaging.
     *
     * @param thread String name of thread to be set as current thread.
     */
    public void selectThread(String thread) {
        threads.getSelectionModel().select(thread);
    }

    private void createThread() {
        accountDisplay.joinThread(threadField.getText());
        threadField.clear();
    }

    private void joinThread() {
        if (threads.getSelectionModel().getSelectedItem() != null) {
            accountDisplay.joinThread(threads.getSelectionModel().getSelectedItem());
        }

    }
}
