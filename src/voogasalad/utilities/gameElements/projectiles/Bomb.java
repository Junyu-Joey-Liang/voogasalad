package voogasalad.utilities.gameElements.projectiles;

import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Projectile;

import java.awt.geom.Point2D;
import java.util.Map;

/**
 * a bomb which does a set amount of damage to a radius of targets
 */
public class Bomb extends Projectile {
    public static final String MOVE_BOMB = "MoveBomb";
    public static final String BLAST_RADIUS = "blastRadius";

    private int blastRadius;

    public Bomb(Map<String, Object> args) {
        super(args);
        setMoveType(MOVE_BOMB);
        blastRadius = (int) args.get(BLAST_RADIUS);
    }

    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new Bomb(getInitialParams());
    }

    public int getBlastRadius() {
        return blastRadius;
    }
}
