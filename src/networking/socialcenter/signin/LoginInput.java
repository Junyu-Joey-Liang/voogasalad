package networking.socialcenter.signin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import networking.router.messages.ServerMessage;
import networking.router.messages.servermessages.GetAccountMessage;
import networking.socialcenter.ClientInterface;


/**
 * This class is responsible for allowing the user to login and displays the ability to login.
 */
public class LoginInput {
    private static final String LOGIN_TITLE = "Login";

    private AccountInput accountInput;
    private BorderPane borderPane;
    private ClientInterface clientInterface;

    /**
     * Constructor for LoginInput.
     *
     * @param clientInterface Client application interface which will be used to send the login information to the server.
     */
    public LoginInput(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;

        accountInput = new AccountInput(LOGIN_TITLE);
        borderPane = new BorderPane();
        borderPane.setTop(accountInput.getNode());

        Button signInButton = new Button(LOGIN_TITLE);
        borderPane.getStylesheets().add(getClass().getResource(SignInPage.RESOURCES_PATH + SignInPage.SIGN_IN_CSS_FILE).toExternalForm());
        signInButton.getStyleClass().add("login-button");
        signInButton.setOnAction(event -> signIn());
        borderPane.setBottom(signInButton);
    }

    /**
     * Allows for retrieval of root node for logging in.
     *
     * @return Root node of login page.
     */
    public Node getNode() {
        return borderPane;
    }

    private void signIn() {
        ServerMessage message = new GetAccountMessage(accountInput.getUsername(), accountInput.getPassword());
        clientInterface.sendMessage(message);

        accountInput.clear();
    }

}
