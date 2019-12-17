package voogasalad.authoring.controller.gameobjectsbuilder.defender;

import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.Defender;
import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.authoring.controller.DefenderObserver;
import voogasalad.authoring.controller.ProjectileObserver;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;


/**
 * Controller for defenders
 */
public class DefenderController extends AbstractGameObjectController implements ProjectileObserver {
    private final String DEFAULT_DEF_NAME = VisConstant.gameObjectBundle.getString("defenderDefaultName");
    private final String DEFAULT_DEF_IMG = "defaultDefender.png";
    private final String type = "Defender";
    private static final String title = VisConstant.gameObjectBundle.getString("defenderControllerTitle");
    private final static Projectile defaultProjectile = new Projectile("default", VisualizationUtil.GAMEOBJECT_IMAGES_FOLDER + "defaultProjectile.png", 1, 1, "EXPLODE",  1, 1);
    private final int projID;

    /**
     * sets title
     *
     * @param model data model
     */
    public DefenderController(Model model) {
        super(model);
        this.setTitle(title);
        projID = model.addAuthoringFeature("Projectile",defaultProjectile);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public AbstractFeature createNewObj() {
        return new Defender(VisualizationUtil.GAMEOBJECT_IMAGES_FOLDER + DEFAULT_DEF_IMG, DEFAULT_DEF_NAME, 1, 1, projID, "DumbTarget", 1, 1, 1, 1);
    }

    @Override
    public String getDefaultName() {
        return DEFAULT_DEF_NAME;
    }

    @Override
    public AbstractAttributePaneManager createCentralPane() {
        return new DefenderAttributePaneManager(this);
    }

    @Override
    public void updateProjectile() {
        ((DefenderAttributePaneManager) getCenterPaneManager()).updateProjectileScroll();
    }

    @Override
    public void updateObservers(){
        getObservers().forEach(observer -> ((DefenderObserver) observer).updateDefenders());
    }

}
