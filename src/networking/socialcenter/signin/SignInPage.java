package networking.socialcenter.signin;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import networking.socialcenter.ClientInterface;

/**
 * Class responsible for displaying ability to create an account or login into an existing account.
 */
public class SignInPage {
    public static final String RESOURCES_PATH = "/networking/socialcenter/signin/resources/";
    public static final String SIGN_IN_CSS_FILE = "loginpagestyles.css";

    private BorderPane loginPageNode;

    /**
     * Constructor for SignInPage.
     *
     * @param clientInterface Client application interface. This is passed into the create account and login input pages so that the
     *                        account information can be sent to the server.
     */
    public SignInPage(ClientInterface clientInterface) {
        loginPageNode = new BorderPane();
        loginPageNode.getStylesheets().add(getClass().getResource(RESOURCES_PATH + SIGN_IN_CSS_FILE).toExternalForm());
        loginPageNode.getStyleClass().add("grid");
        LoginInput loginInput = new LoginInput(clientInterface);
        CreateAccountInput createAccountInput = new CreateAccountInput(clientInterface);
        loginPageNode.setLeft(loginInput.getNode());
        loginPageNode.setRight(createAccountInput.getNode());
    }


    /**
     * Allows for retrieval of root node to be added to scene tree.
     *
     * @return Root node of sign in/up page.
     */
    public Parent getNode() {
        return loginPageNode;
    }
}
