package voogasalad.utilities.gameElements.obstacles;

import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Obstacle;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * a damage obstacle which stays in the path and does damage to attackers
 */
public class DamageObstacle extends Obstacle {
    public static final String DAMAGE = "damage";
    public static final String OBSTACLE_TYPE = "DamageObstacleCommand";
    private int damage;
    private Set<Integer> attackers;
    public DamageObstacle(Map<String, Object> args) {
        super(args);
        damage = (int) args.get(DAMAGE);
        setObstacleType(OBSTACLE_TYPE);
        attackers = new HashSet<>();
    }

    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new DamageObstacle(getInitialParams());
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Set<Integer> getAttackers() {
        return attackers;
    }
}
