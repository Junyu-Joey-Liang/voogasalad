package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.updateelements.MoveCommand;

import java.awt.geom.Point2D;

/**
 * This is the move element. It is responsible for moving an element to a new location
 */
public class MoveElement implements BackEndCommand {
    private int id;
    private Point2D.Double location;
    private int heading;
    public MoveElement(int id, Point2D.Double location, int heading){
        this.id = id;
        this.location = location;
        this.heading = heading;
    }
    @Override
    public void execute(LevelController level) {
        level.addToFrontEndCommands(new MoveCommand(id,location,heading));
    }
}
