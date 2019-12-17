package voogasalad.authoring.controller.gameobjectsbuilder.attacker;

import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.Attacker;
import voogasalad.authoring.controller.AttackerObserver;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.util.VisConstant;
import static voogasalad.authoring.model.ModelTypeConstants.ATTACKER;
import static voogasalad.authoring.util.VisConstant.DEFAULT_ATT_NAME;

/**
 * Controller for the attackers tab. Extend from  {@link AbstractGameObjectController}
 * Three classes chosen are (BasicAuthoringController, which is the central control for all controller in GAE. AttackerController, which is an
 * example controller managed by BasicAuthoringController. Attacker, which is an implementation of AuthoringFeature, represents the class that are used
 * to store each type of game object that the designer created)
 * These three classes are chosen because I think they showcases some main designs spotlights in the code:
 * 1. MVC model is used to create a flexible design. The Model which stores the data is completely separate from the controller, so each part can be changed independently without affecting the other
 * 2. Observer Design Pattern is used. Inter-controller communication is achieved by controller subscribing to other controllers, and extends the specific Observer interface. This is much more efficient then broadcasting to all controllers.
 * 3. minor techniques that improved designs: reflection to initialize all the game object controllers, resource bundle was used to store information, etc
 */
public class AttackerController extends AbstractGameObjectController {
    private static final String title = VisConstant.gameObjectBundle.getString("attackerControllerTitle");

    /**
     * Default constructor, stores a point to the model of the game
     *
     * @param model model for all objects
     */
    public AttackerController(Model model) {
        super(model);
        this.setTitle(title);
    }

    @Override
    public String getType() {
        return ATTACKER;
    } // get the type of this game object controller

    @Override
    public AbstractFeature createNewObj() { // create a default attacker
        return new Attacker();
    }

    @Override
    public String getDefaultName() {
        return DEFAULT_ATT_NAME;
    } // get the default name for an attacker

    @Override
    public AbstractAttributePaneManager createCentralPane() { // initialize the central pane for this tab
        return new AttackerAttributePaneManager(this);
    }

    @Override
    public void updateObservers() { // update the observers when the attackers configurations are updated by calling observer.updateAttackers method
        for (Object obs : getObservers()) {
            AttackerObserver observer = (AttackerObserver) obs;
            observer.updateAttackers();
        }
    }


}
