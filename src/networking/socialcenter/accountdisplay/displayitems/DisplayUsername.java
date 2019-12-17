package networking.socialcenter.accountdisplay.displayitems;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import networking.socialcenter.accountdisplay.AccountDisplayPiece;

/**
 * Class responsible for displaying the account username.
 */
public class DisplayUsername implements AccountDisplayPiece {
    private static final String USERNAME_DESCRIPTION = "Username: ";
    private GridPane gridPane;
    private Text value;

    /**
     * Default constructor for DisplayUsername.
     *
     * Takes in and returns no values.
     */
    public DisplayUsername() {
        setUpDisplay("");
    }

    /**
     * Overloaded constructor for display username. Takes in username value.
     *
     * @param username String username.
     */
    public DisplayUsername(String username) {
        setUpDisplay(username);
    }

    /**
     * Allows whoever is displaying the username to update/change the displayed username.
     *
     * @param newUsername String username value
     */
    public void setUsername(String newUsername) {
        value.setText(newUsername);
    }

    /**
     * Allows for obtaining of node used to add to a scene.
     *
     * @return Root node for displaying the username of the account.
     */
    public Node getNode() {
        return gridPane;
    }

    private void setUpDisplay(String username) {
        Text description = new Text(USERNAME_DESCRIPTION);
        description.getStyleClass().add("header");
        value = new Text(username);
        value.getStyleClass().add("header");
        gridPane = new GridPane();
        gridPane.add(description, 0, 0);
        gridPane.add(value, 1, 0);
    }
}
