package voogasalad.authoring.util.elementbuilders;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import voogasalad.authoring.util.VisualizationUtil;

import static voogasalad.authoring.util.VisConstant.SPACING;


public class AuthoringVerticalScroll {
    private ScrollPane scrollPane;
    private VBox col;

    public AuthoringVerticalScroll() {
        scrollPane = new ScrollPane();
        col = new VBox(SPACING);
        VisualizationUtil.setDefaultPaddingAndSpacing(col, Color.TRANSPARENT);
        scrollPane.setContent(col);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public VBox getCol() {
        return col;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
