package voogasalad.authoring.controller.gameobjectsbuilder.projectile;

import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.authoring.controller.ProjectileObserver;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;


/**
 * controller for projectile
 */
public class ProjectileController extends AbstractGameObjectController {
    private static final String DEFAULT_PROJ_NAME = VisConstant.gameObjectBundle.getString("projectileDefaultName");
    private static final String type = "Projectile";
    private static final String title = VisConstant.gameObjectBundle.getString("projectileControllerTitle");
    private static final String DEFAULT_PROJ_IMG = "defaultProjectile.png";

    public ProjectileController(Model model) {
        super(model);
        this.setTitle(title);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public AbstractFeature createNewObj() {
        return new Projectile("default", VisualizationUtil.GAMEOBJECT_IMAGES_FOLDER + DEFAULT_PROJ_IMG, 1, 1, "EXPLODE",  1, 1);
    }

    @Override
    public String getDefaultName() {
        return DEFAULT_PROJ_NAME;
    }


    @Override
    public AbstractAttributePaneManager createCentralPane() {
        return new ProjectileAttributePaneManager(this);
    }

    @Override
    public void updateObservers() {
        for (Object obs : getObservers()) {
            ProjectileObserver observer = (ProjectileObserver) obs;
            observer.updateProjectile();
        }
    }


}
