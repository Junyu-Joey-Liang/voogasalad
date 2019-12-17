package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.backendcommandresponse.AbleToPlaceCommand;
import voogasalad.utilities.frontEndCommands.backendcommandresponse.UnableToPlaceCommand;

import java.awt.geom.Point2D;

/**
 * This returns whether or not a defender can be placed in a given location. THis is also used for obstacles
 */
public class DragDefenderCommand implements BackEndCommand {

    private String defenderType;
    private Point2D.Double location;

    public DragDefenderCommand(String defenderType, Point2D.Double location) {
        this.defenderType = defenderType;
        this.location = location;
    }
    @Override
    public void execute(LevelController level) {
        if(level.canPlace(defenderType, location)) {
            level.addToFrontEndCommands(new AbleToPlaceCommand(level.getLevelElement(defenderType).getRadius()));
        } else {
            level.addToFrontEndCommands(new UnableToPlaceCommand(level.getLevelElement(defenderType).getRadius()));
        }
    }
}
