package networking.socialcenter.accountdisplay.displayitems;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import networking.socialcenter.accountdisplay.AccountDisplayPiece;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for displaying all the games along with their respective scores.
 */
public class DisplayScores implements AccountDisplayPiece {
    private static final String DESCRIPTION = "High Scores";
    private static final int GAME_NAME_COLUMN = 0;
    private static final int GAME_SCORE_COLUMN = 1;

    private BorderPane borderPane;
    private GridPane gridPane;
    private Map<String, Integer> games;

    /**
     * Constructor for DisplayScores class.
     *
     * Does not take in any values.
     */
    public DisplayScores() {
        borderPane = new BorderPane();
        Text description = new Text(DESCRIPTION);
        description.getStyleClass().add("header");
        borderPane.setTop(description);
        gridPane = new GridPane();
        gridPane.getStyleClass().add("list-body");
        borderPane.setCenter(new ScrollPane(gridPane));

        games = new HashMap<>();
    }

    /**
     * Tells the display scores class to add/update a given game/score pair to the displayed games and scores.
     *
     * @param game String name of game whose score is being updated.
     * @param score Integer value to be associated with the given game.
     */
    public void addScores(String game, int score) {
        if (games.containsKey(game)) {
            clearGridElem(GAME_SCORE_COLUMN, games.get(game));
            gridPane.add(new Text(game), GAME_NAME_COLUMN, games.get(game));
            gridPane.add(new Text("" + score), GAME_SCORE_COLUMN, games.get(game));
        } else {
            int row = gridPane.getRowCount();
            games.put(game, row);
            gridPane.add(new Text(game), GAME_NAME_COLUMN, row);
            gridPane.add(new Text("" + score), GAME_SCORE_COLUMN, row);
        }

    }

    /**
     * @return Parent node is returned so that the display scores display can be shown in the account display page.
     */
    public Node getNode() {
        return borderPane;
    }

    private void clearGridElem(int row, int col) {
        Text blankText = new Text("");
        StackPane blankBox = new StackPane();
        blankBox.getChildren().addAll(blankText);
        blankBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.add(blankBox, row, col);
    }
}
