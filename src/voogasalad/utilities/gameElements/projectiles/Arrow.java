package voogasalad.utilities.gameElements.projectiles;

import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Projectile;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

/**
 * an arrow which does a set amount of damage to one target
 */
public class Arrow extends Projectile implements Serializable {
    private static final long serialVersionUID = 0L;
    public static final String MOVE_ARROW = "MoveArrow";
    public Arrow(Map<String,Object> args){
        super(args);
        setMoveType(MOVE_ARROW);
    }

    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new Arrow(getInitialParams());
    }
}
