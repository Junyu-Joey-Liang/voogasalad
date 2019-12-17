package voogasalad.authoring.util.elementbuilders;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.util.VisualizationUtil;

import static voogasalad.authoring.util.VisConstant.SPACING;


public class AuthoringScrollWithLabel {
    private static final Label currentLabel = new Label("CurrentChoice");
    private static final Label noChosenLabel = new Label("Not Chosen!");
    private AuthoringHorizontalScroll scroll;
    private VBox currentChoice;
    private HBox hBox;
    private Label label;

    public AuthoringScrollWithLabel(String labelText) {
        this.scroll = new AuthoringHorizontalScroll();
        this.hBox = new HBox(SPACING);
        this.label = new Label(labelText);
        this.currentChoice = new VBox(SPACING);
        hBox.getChildren().addAll(label, scroll.getScrollPane(), currentChoice);
    }

    public AuthoringHorizontalScroll getScroll() {
        return scroll;
    }

    public HBox gethBox() {
        return hBox;
    }

    public void updateCurrentChoice(AbstractFeature obj) {
        this.currentChoice.getChildren().clear();
        if (obj == null) {
            this.currentChoice.getChildren().add(noChosenLabel);
        } else {
            this.currentChoice.getChildren().addAll(currentLabel, VisualizationUtil.createIcon(obj.getID(),
                    new ImageView(obj.getView().getImage()), obj.getName(), 50, 50));
        }
    }
}
