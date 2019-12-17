package voogasalad.authoring.controller.gameobjectsbuilder.defender;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.Defender;
import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.elementbuilders.*;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Pane manager for defernders
 */
public class DefenderAttributePaneManager extends AbstractAttributePaneManager {
    private static final ResourceBundle rb = VisConstant.gameObjectBundle;
    private InputTextElement defenderNameInput;
    private InputIntegerSliderElement defenderHealthSlider;
    private InputIntegerSliderElement defenderRadiusSlider;
    private InputIntegerSliderElement defenderFreqAttackSlider;
    private AuthoringChoice defenderTargetMechanismChoice;
    private AuthoringScrollWithLabel defenderProjectileChoice;
    private InputIntegerSliderElement defenderNumProjSlider;


    public DefenderAttributePaneManager(AbstractGameObjectController gameObjectController) {
        super(gameObjectController);
    }

    @Override
    public void initPane() {
        defenderNameInput = new InputTextElement(rb.getString("defenderNameInput"));
        defenderNameInput.getTextField().textProperty().addListener((obs, old, newVal) -> changeName(newVal));

        defenderHealthSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("defenderHealthSlider").split(",")).iterator());
        defenderHealthSlider.getMySlider().valueProperty().addListener(e -> changeHealth(defenderHealthSlider.getMySlider().getValue()));

        defenderRadiusSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("defenderRadiusSlider").split(",")).iterator());
        defenderRadiusSlider.getMySlider().valueProperty().addListener(e -> updateRadiusOfVision(defenderRadiusSlider.getMySlider().getValue()));

        defenderFreqAttackSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("defenderCoolDownSlider").split(",")).iterator());
        defenderFreqAttackSlider.getMySlider().valueProperty().addListener(e -> updateFreqOfAttack(defenderFreqAttackSlider.getMySlider().getValue()));

        defenderTargetMechanismChoice = new AuthoringChoice(Arrays.asList(rb.getString("defenderTargetMechanismChoice").split(",")).iterator());
        defenderTargetMechanismChoice.getChoiceBox().valueProperty().addListener(e -> setTargetMechanismType((String) defenderTargetMechanismChoice.getChoiceBox().getValue()));

        defenderNumProjSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("defenderNumProjSlider").split(",")).iterator());
        defenderNumProjSlider.getMySlider().valueProperty().addListener(e -> updateNumProjectile(defenderNumProjSlider.getMySlider().getValue()));

        defenderProjectileChoice = createObjectScroll(Arrays.asList(rb.getString("defenderProjectileChoice").split(",")).iterator());

        getAttributePane().getChildren().addAll(defenderNameInput.getNode(), defenderHealthSlider.getNode(),
                defenderRadiusSlider.getNode(), defenderFreqAttackSlider.getNode(), defenderTargetMechanismChoice.getHBox(), defenderProjectileChoice.gethBox());
        updateAttributePane();
        addActionToProjectileRow();
    }

    @Override
    public void updateAttributePane() {
        Defender obj = (Defender) getCurrentObj();
        if (defenderNameInput == null) {
            initPane();
        }
        defenderNameInput.getTextField().setText(obj.getName());
        defenderHealthSlider.getMySlider().setValue(obj.getBasicConfigurations().getHealth());
        defenderRadiusSlider.getMySlider().setValue(obj.getRadiusOfVision());
        defenderFreqAttackSlider.getMySlider().setValue(obj.getCoolDown());
        defenderTargetMechanismChoice.getChoiceBox().setValue(obj.getTargetingMechanism());
        updateObjectScroll(defenderProjectileChoice.getScroll().getRow(), rb.getString("defenderProjectileChoice").split(",")[1]);
        addActionToProjectileRow();
        defenderNumProjSlider.getMySlider().setValue(obj.getNumProjectile());
        setTargetMechanismType(obj.getTargetingMechanism());
        defenderProjectileChoice.updateCurrentChoice((Projectile) getAuthoringFeature(obj.getProjectile()));
    }

    private void updateNumProjectile(double input) {
        ((Defender) getCurrentObj()).setNumProjectile((int) input);
    }

    private void addActionToProjectileRow() {
        for (Node n : defenderProjectileChoice.getScroll().getRow().getChildren()) {
            AuthoringIcon icon = (AuthoringIcon) n;
            icon.getIconView().setOnMouseClicked(event -> changeProjectile(icon));
        }
    }

    private void changeProjectile(AuthoringIcon icon) {
        Projectile pro = (Projectile) getAuthoringFeature(icon.getID());
        ((Defender) getCurrentObj()).setProjectile(pro.getID());
        defenderProjectileChoice.updateCurrentChoice(pro);
    }

    private void changeHealth(double health) {
        Defender def = (Defender) getCurrentObj();
        def.getBasicConfigurations().setHealth((int) Math.round(health));
    }

    private void updateRadiusOfVision(Double r) {
        Defender def = (Defender) getCurrentObj();
        def.setRadiusOfVision((int) Math.round(r));
    }

    private void updateFreqOfAttack(Double f) {
        Defender def = (Defender) getCurrentObj();
        def.setCoolDown((int) Math.round(f));
    }

    private void setTargetMechanismType(String type) {
        Defender def = (Defender) getCurrentObj();
        def.setTargetingMechanism(type);
        if (type.equals("SmartTarget") && getAttributePane().getChildren().contains(defenderNumProjSlider.getNode())) {
            getAttributePane().getChildren().remove(defenderNumProjSlider.getNode());
        }
        if (type.equals("DumbTarget") && !getAttributePane().getChildren().contains(defenderNumProjSlider.getNode())) {
            getAttributePane().getChildren().add(defenderNumProjSlider.getNode());
        }
    }

    void updateProjectileScroll() {
        if (defenderProjectileChoice != null) {
            updateObjectScroll(defenderProjectileChoice.getScroll().getRow(), rb.getString("defenderProjectileChoice").split(",")[1]);
            addActionToProjectileRow();
            Defender obj = (Defender) getCurrentObj();
            defenderProjectileChoice.updateCurrentChoice((Projectile) getAuthoringFeature(obj.getProjectile()));
        }
    }

}
