package voogasalad.authoring.controller.gameobjectsbuilder.obstacle;

import voogasalad.authoring.authoringfeature.Obstacle;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;
import voogasalad.authoring.util.elementbuilders.InputTextElement;
import voogasalad.authoring.util.elementbuilders.AuthoringChoice;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 *  Pane manager for obstacles
 */
public class ObstacleAttributePaneManager extends AbstractAttributePaneManager {
    private static final ResourceBundle rb = VisConstant.gameObjectBundle;
    private InputTextElement obstacleNameInput;
    private InputIntegerSliderElement obstacleHealthSlider;
    private InputIntegerSliderElement obstacleDamageSlider;
    private AuthoringChoice obstacleTypeChoice;


    /**
     * default constructor
     *
     * @param gameObjectController game object controller
     */
    public ObstacleAttributePaneManager(AbstractGameObjectController gameObjectController) {
        super(gameObjectController);
    }

    @Override
    public void initPane() {
        obstacleNameInput = new InputTextElement(rb.getString("obstacleNameInput"));
        obstacleNameInput.getTextField().textProperty().addListener((obs, old, newVal) -> changeName(newVal));

        obstacleHealthSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("obstacleHealthSlider").split(",")).iterator());
        obstacleHealthSlider.getMySlider().valueProperty().addListener(e -> changeHealth(obstacleHealthSlider.getMySlider().getValue()));

        obstacleDamageSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("obstacleDamageSlider").split(",")).iterator());
        obstacleDamageSlider.getMySlider().valueProperty().addListener(e -> updateDamage(obstacleDamageSlider.getMySlider().getValue()));

        obstacleTypeChoice = new AuthoringChoice(Arrays.asList(rb.getString("obstacleTypeChoice").split(",")).iterator());
        obstacleTypeChoice.getChoiceBox().valueProperty().addListener(e -> setObstacleType((String) obstacleTypeChoice.getChoiceBox().getValue()));

        getAttributePane().getChildren().addAll(obstacleNameInput.getNode(), obstacleTypeChoice.getHBox());
        updateAttributePane();
    }

    private void setObstacleType(String type) {
        Obstacle obj = (Obstacle) getCurrentObj();
        obj.setObstacleType(type);
        if (type.equals("Damage")) {
            if (getAttributePane().getChildren().contains(obstacleHealthSlider.getNode())){
                getAttributePane().getChildren().remove(obstacleHealthSlider.getNode());
            }
            if (!getAttributePane().getChildren().contains(obstacleDamageSlider.getNode())){
                getAttributePane().getChildren().add(obstacleDamageSlider.getNode());
            }
        }
        if (type.equals("Health")) {
            if (!getAttributePane().getChildren().contains(obstacleHealthSlider.getNode())){
                getAttributePane().getChildren().add(obstacleHealthSlider.getNode());
            }
            if (getAttributePane().getChildren().contains(obstacleDamageSlider.getNode())){
                getAttributePane().getChildren().remove(obstacleDamageSlider.getNode());
            }
        }
    }

    @Override
    public void updateAttributePane() {
        if (obstacleNameInput == null) initPane();
        Obstacle obj = (Obstacle) getCurrentObj();
        obstacleNameInput.getTextField().setText(obj.getName());
        obstacleHealthSlider.getMySlider().setValue(obj.getBasicConfigurations().getHealth());
        obstacleDamageSlider.getMySlider().setValue(obj.getDamage());
        obstacleTypeChoice.getChoiceBox().setValue(obj.getObstacleType());
        setObstacleType(obj.getObstacleType());
    }


    private void changeHealth(double health) {
        Obstacle obstacle = (Obstacle) getCurrentObj();
        obstacle.getBasicConfigurations().setHealth((int) Math.round(health));
    }

    private void updateDamage(Double r) {
        Obstacle obstacle = (Obstacle) getCurrentObj();
        obstacle.setDamage((int) Math.round(r));
    }

}
