package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Defender;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the smart targeting system which will shoot a projectile to the attacker furthest along in the path. This takes speed
 * into account in this calculation
 */
public class SmartTarget implements BackEndCommand {
    public static final String HEADING = "heading";
    private Defender defender;

    public SmartTarget(Defender defender){
        this.defender = defender;
    }

    @Override
    public void execute(LevelController level) {
        List<Integer> ids = level.getCollisions().containsKey(defender.getId()) ? level.getCollisions().get(defender.getId()) : new ArrayList<>();
        if(!ids.isEmpty()) {
            defender.setCounter(0);
            List<Element> collisions = new ArrayList<>();
            for (Integer s : ids) {
                if(level.getActiveElements().containsKey(s)) {
                    collisions.add(level.getActiveElements().get(s));
                }
            }
            int i = level.findOldestElement(collisions);
            if(i == -1){
                return;
            }
            Element target = (level.getActiveElements().get(i));
            Point2D.Double targetLoc = new Point2D.Double(target.getLocation().getY() - defender.getLocation().getY(), target.getLocation().getX() - defender.getLocation().getX());
            int heading = (int) (Math.toDegrees(Math.atan2(targetLoc.getY(),targetLoc.getX())));
            level.getLevelElement(defender.getProjectile().getName()).getInitialParams().put(HEADING, heading);
            level.newActiveElement(defender.getProjectile().getName(), defender.getLocation());
        }
    }
}
