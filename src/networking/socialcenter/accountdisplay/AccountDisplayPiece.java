package networking.socialcenter.accountdisplay;

import javafx.scene.Node;

/**
 * Interface for each display piece displayed on the account display page.
 * Display pieces are responsible for organizing their display objects but must return
 * their root nodes to be displayed.
 */
public interface AccountDisplayPiece {

    /**
     *
     * @return Root node of display piece to add to node tree of account display piece.
     */
    Node getNode();
}
