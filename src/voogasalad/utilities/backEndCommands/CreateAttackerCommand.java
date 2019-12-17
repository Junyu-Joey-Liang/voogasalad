package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.updateelements.CreateCommand;

import java.awt.geom.Point2D;
import java.util.Queue;

/**
 * This command creates an attacker in the back end and sends the correct methods to the front end
 */
public class CreateAttackerCommand implements BackEndCommand {
    public static final String SPEED = "speed";
    public static final String PATH = "path";

    private String name;
    public CreateAttackerCommand(String name){
        this.name = name;
    }
    @Override
    public void execute(LevelController level) {
        Element element = level.getLevelElement(name);
        Queue<Point2D.Double> list = level.findPath((int)element.getInitialParams().get(SPEED));
        element.getInitialParams().put(PATH,list);
        Point2D.Double spawnP = list.peek();
        element = element.createElementCopy(spawnP);
        level.getActiveElements().put(element.getId(),element);
        level.addToFrontEndCommands(new CreateCommand(element.getId(),element.getImage(),element.getElementType(),element.getLocation(),element.getHeading(), 1, element.getRadius()));
    }
}
