package networking.socialcenter;

import javafx.scene.Node;

/**
 * Interface describing methods for the display portion of what the user is allowed to see in the social center.
 */
@Deprecated
public interface ClientDisplay {

    /**
     * Can be called to display the social center in a separate window of its own.
     */
    void displayInSeparateWindow();

    /**
     * Allows for retrievel of root node to be added to scene tree.
     *
     * @return Root node of client display.
     */
    Node getNode();
}
