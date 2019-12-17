package voogasalad.authoring.controller.gameobjectsbuilder.projectile;

import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.elementbuilders.AuthoringChoice;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;
import voogasalad.authoring.util.elementbuilders.InputTextElement;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Pane manager for projectile
 */
public class ProjectileAttributePaneManager extends AbstractAttributePaneManager {
    private static final ResourceBundle rb = VisConstant.gameObjectBundle;
    private InputTextElement projectileNameInput;
    private InputIntegerSliderElement speedSlider;
    private InputIntegerSliderElement rangeSlider;
    private InputIntegerSliderElement blastRadiusSlider;
    private AuthoringChoice impactTypeChoice;
    private InputIntegerSliderElement impactLevelSlider;


    /**
     * default constructor
     *
     * @param gameObjectController
     */
    public ProjectileAttributePaneManager(AbstractGameObjectController gameObjectController) {
        super(gameObjectController);
    }

    @Override
    public void initPane() {
        projectileNameInput = new InputTextElement(rb.getString("projectileNameInput"));
        projectileNameInput.getTextField().textProperty().addListener((obs, old, newVal) -> changeName(newVal));

        speedSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("projectilespeedSlider").split(",")).iterator());
        speedSlider.getMySlider().valueProperty().addListener(e -> updateSpeed(speedSlider.getMySlider().getValue()));

        rangeSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("projectilerangeSlider").split(",")).iterator());
        rangeSlider.getMySlider().valueProperty().addListener(e -> updateRadius(rangeSlider.getMySlider().getValue()));

        impactTypeChoice = new AuthoringChoice(Arrays.asList(rb.getString("projectileimpacttypeChoice").split(",")).iterator());
        impactTypeChoice.getChoiceBox().valueProperty().addListener(e -> setImpactType((String) impactTypeChoice.getChoiceBox().getValue()));

        blastRadiusSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("projectileBlastRadiusSlider").split(",")).iterator());
        blastRadiusSlider.getMySlider().valueProperty().addListener(e -> updateBlastRadius((int) Math.round(impactLevelSlider.getMySlider().getValue())));

        impactLevelSlider = new InputIntegerSliderElement(Arrays.asList(rb.getString("projectileimpactlevelSlider").split(",")).iterator());
        impactLevelSlider.getMySlider().valueProperty().addListener(e -> updateImpactLevel((int) Math.round(impactLevelSlider.getMySlider().getValue())));

        getAttributePane().getChildren().addAll(projectileNameInput.getNode(), speedSlider.getNode(), rangeSlider.getNode(), impactTypeChoice.getHBox(), impactLevelSlider.getNode());
        updateAttributePane();
    }

    @Override
    public void updateAttributePane() {
        if (projectileNameInput == null) {
            initPane();
        }
        Projectile obj = (Projectile) getCurrentObj();
        this.speedSlider.getMySlider().setValue(obj.getSpeed());
        this.projectileNameInput.getTextField().setText(obj.getName());
        this.impactLevelSlider.getMySlider().setValue(obj.getImpactLevel());
        this.rangeSlider.getMySlider().setValue(obj.getRange());
        this.impactTypeChoice.getChoiceBox().setValue(obj.getImpactType());
        setImpactType(obj.getImpactType());
        this.blastRadiusSlider.getMySlider().setValue((obj.getBlastRadius()));
    }

    private void updateBlastRadius(int r) {
        Projectile projectile = (Projectile) this.getCurrentObj();
        projectile.setBlastRadius(r);
    }

    private void updateSpeed(double speed) {
        Projectile projectile = (Projectile) this.getCurrentObj();
        projectile.setSpeed(speed);
    }

    private void updateRadius(double r) {
        Projectile projectile = (Projectile) this.getCurrentObj();
        projectile.setRange(r);
    }

    private void updateImpactLevel(int l) {
        Projectile projectile = (Projectile) this.getCurrentObj();
        projectile.setImpactLevel(l);
    }

    private void setImpactType(String name) {
        Projectile projectile = (Projectile) this.getCurrentObj();
        projectile.setImpactType(name);
        if (name.equals("SINGLE HIT") && getAttributePane().getChildren().contains(blastRadiusSlider.getNode())) {
            getAttributePane().getChildren().remove(blastRadiusSlider.getNode());
        }
        if (name.equals("EXPLODE") && !getAttributePane().getChildren().contains(blastRadiusSlider.getNode())) {
            getAttributePane().getChildren().add(blastRadiusSlider.getNode());
        }
    }
}
