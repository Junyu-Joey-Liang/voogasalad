package networking.socialcenter;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import networking.router.Account;
import networking.router.Router;
import networking.router.messages.ServerMessage;
import networking.router.messages.servermessages.UpdateHighScoreMessage;
import networking.socialcenter.accountdisplay.AccountDisplayPage;
import networking.socialcenter.signin.SignInPage;

import java.util.List;

/**
 * Class responsible for handling the social center. This class holds the display pieces
 * along with the networked pieces.
 */
public class SocialCenter implements ClientInterface {
    private AccountDisplayPage accountDisplayPage;
    private SignInPage signInPage;
    private Router router;

    private HBox rootNode;

    /**
     * Constructor for SocialCenter.
     *
     * This takes in and returns no values.
     */
    public SocialCenter() {
        rootNode = new HBox();
        router = new Router(this);
        accountDisplayPage = new AccountDisplayPage(this);
        signInPage = new SignInPage(this);

        rootNode.getChildren().clear();
        rootNode.getChildren().add(signInPage.getNode());
    }

    /**
     * Tells the display pieces that the account credentials have been accepted and that the sign in page should
     * be replaced with the given account page.
     *
     * @param account Account to be set as currently logged in account.
     * @param threads List of String representing currently available threads.
     */
    public void loginToAccount(Account account, List<String> threads) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootNode.getChildren().clear();
                rootNode.getChildren().add(accountDisplayPage.getNode());
                accountDisplayPage.setAccount(account);
                threads.forEach(thread -> accountDisplayPage.addThread(thread));
            }
        });

    }

    /**
     * Allows for retrieval of account display page.
     *
     * @return AccountDisplayPage to have visual objects changed or username retrieved.
     */
    public AccountDisplayPage getAccountDisplayPage() {
        return accountDisplayPage;
    }

    /**
     * Allows for retrieval of sign in page.
     *
     * @return SignInPage to have visual objects change or username and password read.
     */
    public SignInPage getSignInPage() {
        return signInPage;
    }

    /**
     * Tells the router to send a message over the network.
     *
     * @param message ServerMessage message to be sent from client to server.
     */
    public void sendMessage(ServerMessage message) {
        router.sendMessage(message);
    }

    /**
     * Tells the social center that the current account is being signed out. This method does not take in or return
     * any values.
     */
    public void signOut() {
        accountDisplayPage = new AccountDisplayPage(this);
        rootNode.getChildren().clear();
        rootNode.getChildren().add(signInPage.getNode());
    }

    /**
     * If there is currently a user signed in, then this can be called to send a message to the server to update the user's
     * high score for a given game.
     *
     * @param game String name of game to have score udpated.
     * @param score Integer value of score to which the game should be updated.
     */
    public void updateHighScore(String game, int score) {
        if (!accountDisplayPage.getUsername().equals("")) {
            ServerMessage message = new UpdateHighScoreMessage(accountDisplayPage.getUsername(), game, score);
            sendMessage(message);
        }
    }

    /**
     * Displays message to user with the given error message.
     *
     * @param message String message to be displayed to user.
     */
    public void displayErrorMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new Alert(Alert.AlertType.ERROR, message).showAndWait();
            }
        });
    }

    /**
     * Allows for retrieval of root node to be added to scene's display tree.
     *
     * @return Root node of social center.
     */
    public Node getNode() {
        return rootNode;
    }
}
