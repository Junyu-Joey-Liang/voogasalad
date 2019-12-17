package voogasalad.utilities.gameElements;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.GameElement;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This is the general obstacle. This holds all the information needed for general obstacle usage
 */
public abstract class Obstacle extends GameElement {
    public static final String OBSTACLE = "Obstacle";
    public static final String ROOT = "voogasalad.utilities.backEndCommands.obstacleCommands.";
    private String obstacleType;

    public Obstacle(Map<String, Object> args) {
        super(args);
        setRadius(1);
    }

    @Override
    public List<BackEndCommand> update() {
        try {
            return new ArrayList<>(Arrays.asList((BackEndCommand) Class.forName(ROOT + obstacleType).getDeclaredConstructor(Obstacle.class).newInstance(this)));
        }
        catch(Exception e){
            throw new InvalidParameterException("The obstacle type is not correct");
        }
    }

    @Override
    public String getElementType() {
        return OBSTACLE;
    }

    public void setObstacleType(String obstacleType) {
        this.obstacleType = obstacleType;
    }
}
