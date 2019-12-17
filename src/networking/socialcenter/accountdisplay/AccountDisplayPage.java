package networking.socialcenter.accountdisplay;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import networking.router.Account;
import networking.router.messages.ServerMessage;
import networking.router.messages.servermessages.ChatMessage;
import networking.router.messages.servermessages.JoinChatMessage;
import networking.router.messages.servermessages.LeaveChatMessage;
import networking.socialcenter.ClientInterface;
import networking.socialcenter.accountdisplay.displayitems.DisplayAvailableThreads;
import networking.socialcenter.accountdisplay.displayitems.DisplayScores;
import networking.socialcenter.accountdisplay.displayitems.DisplayThread;
import networking.socialcenter.accountdisplay.displayitems.DisplayUsername;

import java.util.List;

/**
 * This class is responsible for displaying the social center information to the user along with handling all user interactions.
 * This class creates different account display pieces and then sends messages to the server when the user performs network related
 * actions such as sending a message or creating a thread.
 */
public class AccountDisplayPage implements AccountDisplay {
    public static final String RESOURCES_PATH = "/networking/socialcenter/accountdisplay/resources/";
    public static final String ACCOUNT_DISPLAY_CSS_FILE = "accountdisplay.css";

    private static final String SIGN_OUT_TEXT = "Sign Out";

    private DisplayUsername displayUsername;
    private DisplayScores displayScores;
    private DisplayAvailableThreads displayAvailableThreads;
    private DisplayThread currentThread;

    private ClientInterface clientInterface;
    private BorderPane display;
    private String username;

    /**
     * Constructor for AccountDisplayPage
     *
     * @param clientInterface client application interface on which actions will be performed. Responsible for connecting display to network classes.
     */
    public AccountDisplayPage(ClientInterface clientInterface) {
        username = "";
        this.clientInterface = clientInterface;
        setupVisualComponents();
    }

    /**
     * Sets the current account being displayed to the user.
     *
     * @param account Account object to be set as current account being displayed to user.
     */
    public void setAccount(Account account) {
        username = account.getUsername();
        displayUsername.setUsername(username);
        account.getScores().forEach((game, score) -> addScore(game, score));
    }

    /**
     * Adds a score to the displayed scores seen by the user. Usually called by an update score command of a message sent from
     * server to client.
     *
     * @param game String name of game to be updated.
     * @param score Integer score value to update the game to.
     */
    public void addScore(String game, int score) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayScores.addScores(game, score);
            }
        });
    }

    /**
     * Adds a thread to the list of available threads the user can join.
     *
     * @param threadTitle String name of thread to be added to the list.
     */
    @Deprecated
    public void addChat(String threadTitle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayAvailableThreads.addThread(threadTitle);
            }
        });
    }

    /**
     * Tells the account display page which thread should be displayed as the current thread and to population that thread's history.
     *
     * @param threadTitle String name of thread to be set as current thread.
     * @param history List of String messages containing history of thread.
     */
    public void setCurrentChat(String threadTitle, List<String> history) {
        AccountDisplay disp = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentThread = new DisplayThread(disp, threadTitle, history);
                displayAvailableThreads.selectThread(threadTitle);
                display.setRight(currentThread.getNode());
            }
        });

    }

    /**
     * Creates a message with the given content and sends it via the client application.
     *
     * @param message String message content.
     * @param chat String name of chat on which user sent message
     */
    public void sendMessage(String message, String chat) {
        ServerMessage message1 = new ChatMessage(username, message, chat);
        clientInterface.sendMessage(message1);
    }

    /**
     * Tells the account display to add a message to the history of the current thread being shown.
     *
     * @param message String message content
     * @param chat String name of chat to which thread should be added.
     */
    public void addMessage(String message, String chat) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentThread.addMessage(message, chat);
            }
        });

    }

    /**
     * Allows for retrieval of the usrename of the currently logged in user.
     *
     * @return String value of username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds a thread to the list of available threads the user can join.
     *
     * @param thread String name of thread to be added to the list.
     */
    public void addThread(String thread) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayAvailableThreads.addThread(thread);
            }
        });

    }

    /**
     * Sends a message to the client application interface that the user is trying to join a given thread.
     *
     * @param threadName String name of thread the user wishes to join.
     */
    public void joinThread(String threadName) {
        ServerMessage message = new JoinChatMessage(threadName, username);
        clientInterface.sendMessage(message);
    }

    /**
     * Allows for retrieval of root node to be added to parent tree for displaying.
     *
     * @return Root node of account display page to be added to parent tree.
     */
    public Parent getNode() {
        return display;
    }

    private void signOut() {
        for (String chat : displayAvailableThreads.getThreads()) {
            ServerMessage message = new LeaveChatMessage(chat, username);
            clientInterface.sendMessage(message);
        }
        clientInterface.signOut();
    }

    private void setupVisualComponents() {
        display = new BorderPane();
        display.getStylesheets().add(getClass().getResource(RESOURCES_PATH + ACCOUNT_DISPLAY_CSS_FILE).toExternalForm());
        display.getStyleClass().add("account-information-grid");

        GridPane gridPane = new GridPane();

        displayUsername = new DisplayUsername();
        gridPane.add(displayUsername.getNode(), 0, 0);
        displayScores = new DisplayScores();
        gridPane.add(displayScores.getNode(), 0, gridPane.getRowCount());
        displayAvailableThreads = new DisplayAvailableThreads(this);
        gridPane.add(displayAvailableThreads.getNode(), 0, gridPane.getRowCount());

        Button signOut = new Button(SIGN_OUT_TEXT);
        signOut.setOnAction(event -> signOut());
        gridPane.add(signOut, 0, gridPane.getRowCount());

        display.setLeft(gridPane);
    }
}
