package networking.socialcenter.signin;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * General class for taking in account input. This is responsible for displaying fields for and taking in the username and password.
 */
public class AccountInput {
    private GridPane pane;
    private TextField usernameInput;
    private PasswordField passwordInput;

    /**
     * Constructor for AccountInput.
     *
     * @param title String title to be associated with taking in account input (e.g., "Create Account" or "Login")
     */
    public AccountInput(String title) {

        pane = new GridPane();
        pane.getStylesheets().add(getClass().getResource(SignInPage.RESOURCES_PATH + SignInPage.SIGN_IN_CSS_FILE).toExternalForm());
        pane.getStyleClass().add("input-box");

        Text login = new Text(title);
        login.getStyleClass().add("header-text");
        pane.add(login, 0, 0);

        BorderPane borderPane = new BorderPane();
        Text username = new Text("Username");
        username.getStyleClass().add("description-text");
        borderPane.setLeft(username);
        usernameInput = new TextField();
        borderPane.setRight(usernameInput);
        pane.add(borderPane, 0, pane.getRowCount());

        BorderPane borderPane1 = new BorderPane();
        borderPane1.getStyleClass().add("input-box");
        Text password = new Text("Password");
        password.getStyleClass().add("description-text");
        borderPane1.setLeft(password);
        passwordInput = new PasswordField();
        borderPane1.setRight(passwordInput);
        pane.add(borderPane1, 0, pane.getRowCount());
    }

    /**
     * Allows for retrieval of root node to add to node tree.
     *
     * @return Root node of account input
     */
    public Node getNode() {
        return pane;
    }

    /**
     * Allows for retrieval of username given by user.
     *
     * @return String username value.
     */
    public String getUsername() {
        return usernameInput.getText();
    }

    /**
     * Allows for retrieval of password given by user.
     *
     * @return String password value.
     */
    public String getPassword() {
        return passwordInput.getText();
    }

    /**
     * Clears the current inputs for username and password.
     */
    public void clear() {
        usernameInput.clear();
        passwordInput.clear();
    }
}
