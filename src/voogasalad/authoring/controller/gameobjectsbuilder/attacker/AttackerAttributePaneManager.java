package voogasalad.authoring.controller.gameobjectsbuilder.attacker;

import voogasalad.authoring.authoringfeature.Attacker;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;
import voogasalad.authoring.util.elementbuilders.InputTextElement;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Attribute Pane Manager for attackers
 */
public class AttackerAttributePaneManager extends AbstractAttributePaneManager {
    private static final ResourceBundle rb = VisConstant.gameObjectBundle;
    private InputTextElement attackerNameInput;
    private InputIntegerSliderElement attackerSpeedSlider;
    private InputIntegerSliderElement attackerHealthSlider;
    private InputIntegerSliderElement attackerDamageSlider;

    /**
     * Constructor for attacker
     * @param gameObjectController game object controller
     *
     */
    public AttackerAttributePaneManager(AbstractGameObjectController gameObjectController) {
        super(gameObjectController);
    }

    @Override
    public void initPane() {
        attackerNameInput = new InputTextElement(rb.getString("attackerNameInput"));
        attackerNameInput.getTextField().textProperty().addListener((obs, old, newVal) -> changeName(newVal));

        attackerHealthSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("attackerHealthSlider").split(",")).iterator());
        attackerHealthSlider.getMySlider().valueProperty().addListener(e -> changeHealth(attackerHealthSlider.getMySlider().getValue()));

        attackerSpeedSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("attackerSpeedSlider").split(",")).iterator());
        attackerSpeedSlider.getMySlider().valueProperty().addListener(e -> changeSpeed(attackerSpeedSlider.getMySlider().getValue()));

        attackerDamageSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("attackerDamageSlider").split(",")).iterator());
        attackerDamageSlider.getMySlider().valueProperty().addListener(e -> updateDamage(attackerDamageSlider.getMySlider().getValue()));

        getAttributePane().getChildren().addAll(attackerNameInput.getNode(), attackerSpeedSlider.getNode(), attackerHealthSlider.getNode(),
                attackerDamageSlider.getNode());
        updateAttributePane();
    }

    @Override
    public void updateAttributePane() {
        Attacker obj = (Attacker) getCurrentObj();
        if (attackerNameInput == null) {
            initPane();
        }
        attackerNameInput.getTextField().setText(obj.getName());
        attackerSpeedSlider.getMySlider().setValue(obj.getSpeed());
        attackerHealthSlider.getMySlider().setValue(obj.getBasicConfigurations().getHealth());
        attackerDamageSlider.getMySlider().setValue(obj.getDamage());
    }

    private void changeHealth(double health) {
        Attacker attacker = (Attacker) getCurrentObj();
        attacker.getBasicConfigurations().setHealth((int) Math.round(health));
    }

    private void updateDamage(Double r) {
        Attacker attacker = (Attacker) getCurrentObj();
        attacker.setDamage((int) Math.round(r));
    }

    private void changeSpeed(double s) {
        Attacker attacker = (Attacker) getCurrentObj();
        attacker.setSpeed((int) Math.round(s));
    }

}
