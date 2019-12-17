package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;

import java.awt.geom.Point2D;

/**
 * This places a defender on the map if you have enough money and it can be placed
 */
public class PlaceDefenderCommand implements BackEndCommand {

    private String defenderType;
    private Point2D.Double location;

    public PlaceDefenderCommand(String defenderType, Point2D.Double location) {
        this.defenderType = defenderType;
        this.location = location;
    }

    @Override
    public void execute(LevelController level) {
        int cost = level.getCost(defenderType);
        if(level.canPlace(defenderType, location) && level.getCurrentMoney() >= cost) {
            level.setCurrentMoney(level.getCurrentMoney()-cost);
            level.newActiveElement(defenderType, location);
        }
    }
}
