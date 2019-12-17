package voogasalad.authoring.util.elementbuilders;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import voogasalad.authoring.util.VisualizationUtil;

import static voogasalad.authoring.util.VisConstant.SPACING;


public class AuthoringHorizontalScroll {
    private ScrollPane scrollPane;
    private HBox row;

    public AuthoringHorizontalScroll() {
        scrollPane = new javafx.scene.control.ScrollPane();
        row = new HBox(SPACING);
        VisualizationUtil.setDefaultPaddingAndSpacing(row, Color.TRANSPARENT);
        scrollPane.setContent(row);
        scrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
    }

    public HBox getRow() {
        return row;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
