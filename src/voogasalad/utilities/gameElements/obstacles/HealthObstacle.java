package voogasalad.utilities.gameElements.obstacles;

import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Obstacle;

import java.awt.geom.Point2D;
import java.util.Map;

/**
 * a health obstacle which acts as a barrier
 */
public class HealthObstacle extends Obstacle {
    public static final String HEALTH = "health";
    public static final String OBSTACLE_TYPE = "HealthObstacleCommand";
    private int health;
    public HealthObstacle(Map<String,Object> args){
        super(args);
        health = (int)args.get(HEALTH);
        setObstacleType(OBSTACLE_TYPE);
    }
    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new HealthObstacle(getInitialParams());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
