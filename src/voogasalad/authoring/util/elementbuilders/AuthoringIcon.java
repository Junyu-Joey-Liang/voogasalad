package voogasalad.authoring.util.elementbuilders;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import voogasalad.authoring.util.VisualizationUtil;

import static voogasalad.authoring.util.VisConstant.SPACING;

public class AuthoringIcon extends VBox {
    private ImageView iconView;
    private Text nameText;
    private int ID;

    public AuthoringIcon(int ID, ImageView iconView, String name, EventHandler<MouseEvent> e) {
        this(ID, iconView, name);
        this.setOnMouseClicked(e);
    }

    public AuthoringIcon(int ID, ImageView iconView, String name) {
        this.ID = ID;
        this.iconView = iconView;
        getChildren().add(iconView);
        nameText = new Text(name);
        getChildren().add(nameText);
        VisualizationUtil.setDefaultPaddingAndSpacing(this, Color.TRANSPARENT);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(SPACING);
        iconView.setFitWidth(iconView.getBoundsInParent().getWidth());
    }

    public ImageView getIconView() {
        return iconView;
    }

    public void setIconView(ImageView iconView) {
        this.iconView = iconView;
    }

    public Text getNameText() {
        return nameText;
    }

    public void setNameText(Text nameText) {
        this.nameText = nameText;
    }

    public int getID() {
        return ID;
    }
}
