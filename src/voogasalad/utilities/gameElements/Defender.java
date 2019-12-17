package voogasalad.utilities.gameElements;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the defender. This defender can shoot projectiles and recognize the surrounding area. It updates based on back end commands
 */
public class Defender extends GameElement implements Serializable {

    private static final long serialVersionUID = 0L;
    public static final String BASE_DIR = "voogasalad.utilities.backEndCommands.";
    public static final String DEFENDER = "defender";
    public static final String PROJECTILE = "projectile";
    public static final String NUM_PROJECTILE = "numProjectiles";
    public static final String TARGET_TYPE = "targetType";
    public static final String COOL_DOWN = "coolDown";
    public static final double CONVERSION = 0.06;

    private int numProjectiles;
    private Projectile projectile;
    private String targetType;
    private int coolDown;
    private int counter;


    public Defender(Map<String,Object> args){
        super(args);
            projectile = (Projectile) args.get(PROJECTILE);
            numProjectiles = (Integer) args.get(NUM_PROJECTILE);
            targetType = (String) args.get(TARGET_TYPE);
            coolDown = (int)((Integer) args.get(COOL_DOWN) * CONVERSION);
            counter = coolDown;
    }

    @Override
    public List<BackEndCommand> update() {
        List<BackEndCommand> commands = new ArrayList<>();
        if(counter >= coolDown){
            try {
                commands.add((BackEndCommand) Class.forName(BASE_DIR + targetType).getDeclaredConstructor(Defender.class).newInstance(this));
            }
            catch (Exception e){
                throw new InvalidParameterException("Incorrect game parameters");
            }
        }
        else{
            counter++;
        }
        return commands;
    }

    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new Defender(getInitialParams());
    }

    @Override
    public String getElementType() {
        return DEFENDER;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public int getNumProjectiles() {
        return numProjectiles;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
