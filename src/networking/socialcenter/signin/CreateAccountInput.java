package networking.socialcenter.signin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import networking.router.messages.ServerMessage;
import networking.router.messages.servermessages.CreateAccountMessage;
import networking.socialcenter.ClientInterface;

/**
 * This is responsible for displaying the ability to and then allowing the user to create and account.
 */
public class CreateAccountInput {
    private static final String CREATE_ACCOUNT_TITLE = "Create Account";

    private AccountInput accountInput;
    private BorderPane borderPane;
    private ClientInterface clientInterface;

    /**
     * Constructor for CreateAccountInput
     *
     * @param clientInterface Client application interface which will be told to send to create account information to the server.
     */
    public CreateAccountInput(ClientInterface clientInterface) {
        accountInput = new AccountInput(CREATE_ACCOUNT_TITLE);
        borderPane = new BorderPane();
        borderPane.setTop(accountInput.getNode());

        Button signInButton = new Button(CREATE_ACCOUNT_TITLE);
        borderPane.getStylesheets().add(getClass().getResource(SignInPage.RESOURCES_PATH + SignInPage.SIGN_IN_CSS_FILE).toExternalForm());
        signInButton.getStyleClass().add("login-button");
        signInButton.setOnAction(event -> createAccount());
        borderPane.setBottom(signInButton);

        this.clientInterface = clientInterface;
    }

    /**
     * Allows for retrieval of root node to be added to display tree.
     *
     * @return Root node for display the ability to create an account.
     */
    public Node getNode() {
        return borderPane;
    }

    private void createAccount() {
        ServerMessage serverMessage = new CreateAccountMessage(accountInput.getUsername(), accountInput.getPassword());
        clientInterface.sendMessage(serverMessage);

        accountInput.clear();
    }

}
