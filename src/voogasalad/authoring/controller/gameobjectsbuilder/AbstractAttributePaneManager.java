package voogasalad.authoring.controller.gameobjectsbuilder;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;
import voogasalad.authoring.util.elementbuilders.AuthoringIcon;
import voogasalad.authoring.util.elementbuilders.AuthoringScrollWithLabel;

import java.util.Iterator;

import static voogasalad.authoring.util.VisConstant.SMALL_ICON;
import static voogasalad.authoring.util.VisConstant.SPACING;


/**
 * abstract class for attribute pane manager for obstacle, projectile, attacker, and defender
 */
public abstract class AbstractAttributePaneManager implements AttributePaneManager {
    private VBox attributePane;
    private AbstractGameObjectController gameObjectController;


    /**
     * Default constructor, set default spacing
     *
     * @param gameObjectController
     */
    public AbstractAttributePaneManager(AbstractGameObjectController gameObjectController) {
        this.gameObjectController = gameObjectController;
        attributePane = new VBox(SPACING);
        VisualizationUtil.setDefaultPaddingAndSpacing(attributePane, Color.valueOf(VisConstant.gameObjectBundle.getString(gameObjectController.getType() + "CentralPaneColor")));
    }

    /**
     * initialize the attribute pane
     */
    abstract public void initPane();

    /**
     * update the attribute pane
     */
    abstract public void updateAttributePane();

    protected AbstractFeature getCurrentObj() {
        return this.gameObjectController.getCurrentObj();
    }

    protected VBox getAttributePane() {
        return attributePane;
    }

    protected void changeName(String name) {
        this.gameObjectController.changeName(name);
    }

    protected AuthoringScrollWithLabel createObjectScroll(Iterator<String> input) {
        AuthoringScrollWithLabel authoringScrollWithLabel = new AuthoringScrollWithLabel(input.next());
        updateObjectScroll(authoringScrollWithLabel.getScroll().getRow(), input.next());
        return authoringScrollWithLabel;
    }

    protected void updateObjectScroll(HBox row, String type) {
        row.getChildren().clear();
        if (gameObjectController.getAuthoringFeatures(type) != null) {
            for (AuthoringFeature obj : gameObjectController.getAuthoringFeatures(type)) {
                AbstractFeature o = (AbstractFeature) obj;
                AuthoringIcon icon = VisualizationUtil.createIcon(o.getID(), o.getImagePath(), o.getName(), SMALL_ICON, SMALL_ICON);
                row.getChildren().add(icon);
            }
        }
    }

    protected AuthoringFeature getAuthoringFeature(int ID) {
        return gameObjectController.getAuthoringFeature(ID);
    }


}
