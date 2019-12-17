package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.gameElements.Projectile;
import voogasalad.utilities.frontEndCommands.updateelements.MoveCommand;
import voogasalad.utilities.gameElements.Attacker;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The general command for moving a projectile. This will do the general movements and then defer to the specified projectile
 * command on how to explode or not
 */
public class MoveProjectile implements BackEndCommand {
    public static final double FRAMES_PER_SECOND = 60.0;
    private Projectile projectile;
    public MoveProjectile(Projectile projectile){
        this.projectile = projectile;
    }

    @Override
    public void execute(LevelController level) {
        checkCollisions(level);
        projectile.setLocation(new Point2D.Double(
                (projectile.getLocation().getX() + (projectile.getSpeed() * Math.sin(Math.toRadians(projectile.getHeading()))/FRAMES_PER_SECOND)),
                (projectile.getLocation().getY() + (projectile.getSpeed() * Math.cos(Math.toRadians(projectile.getHeading()))/FRAMES_PER_SECOND))
        ));
        Point2D.Double point = new Point2D.Double(projectile.getLocation().getX(), projectile.getLocation().getY());
        level.addToFrontEndCommands(new MoveCommand(projectile.getId(),projectile.getLocation(),projectile.getHeading()));
    }
    private void checkCollisions(LevelController level){
        boolean collided = false;
        if(level.getCollisions().containsKey(projectile.getId()) && level.getCollisions().get(projectile.getId()).size() > 0){
            collided = true;
            List<Element> collisions = new ArrayList<>();
            for (Integer s : level.getCollisions().get(projectile.getId())) {
                if(level.getActiveElements().containsKey(s)) {
                    collisions.add(level.getActiveElements().get(s));
                }
            }
            int i = level.findOldestElement(collisions);
            if(i == -1){
                return;
            }
                BackEndCommand trigger = new DamageElementCommand((Attacker)level.getActiveElements().get(i),projectile.getDamage());
                trigger.execute(level);
                explode(level);
        }
        if(projectile.isExploded()) {
            RemoveActiveElement remove = new RemoveActiveElement(projectile.getId());
            remove.execute(level);
        }
        projectile.setExploded(collided);
    }

    public Projectile getProjectile() {
        return projectile;
    }

    protected void explode(LevelController level){
        return;
    }
}
