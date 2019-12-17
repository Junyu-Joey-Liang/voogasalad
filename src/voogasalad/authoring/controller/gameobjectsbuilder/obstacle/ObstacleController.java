package voogasalad.authoring.controller.gameobjectsbuilder.obstacle;

import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.Obstacle;
import voogasalad.authoring.controller.ObstacleObserver;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractAttributePaneManager;
import voogasalad.authoring.controller.gameobjectsbuilder.AbstractGameObjectController;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;


/**
 * Constroller for obstacles
 */
public class ObstacleController extends AbstractGameObjectController {
    private static final String DEFAULT_OBSTACLE_NAME = VisConstant.gameObjectBundle.getString("obstacleDefaultName");
    private static final String DEFAULT_OBS_IMG = "defaultObstacle.png";
    private static final String type = "Obstacle";
    private static final String title = VisConstant.gameObjectBundle.getString("obstacleControllerTitle");

    /**
     * constructor, sets title for the tab
     *
     * @param model data model
     */
    public ObstacleController(Model model) {
        super(model);
        this.setTitle(title);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public AbstractFeature createNewObj() {
        return new Obstacle(DEFAULT_OBSTACLE_NAME, VisualizationUtil.GAMEOBJECT_IMAGES_FOLDER + DEFAULT_OBS_IMG, -1, 0, "Damage");
    }

    @Override
    public String getDefaultName() {
        return DEFAULT_OBSTACLE_NAME;
    }

    @Override
    public AbstractAttributePaneManager createCentralPane() {
        return new ObstacleAttributePaneManager(this);
    }

    @Override
    public void updateObservers() {
        getObservers().forEach(observer -> ((ObstacleObserver) observer).updateObstacles());
    }


}
