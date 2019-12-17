package voogasalad.utilities.gameElements;


import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;
import voogasalad.utilities.backEndCommands.DamageTowerCommand;
import voogasalad.utilities.backEndCommands.MoveElement;
import voogasalad.utilities.backEndCommands.RemoveActiveElement;
import voogasalad.utilities.gameElements.elementFeatures.Damage;
import voogasalad.utilities.gameElements.elementFeatures.Speed;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This is the attacker. This is in charge of moving along the path and trying to damage the tower.
 */
public class Attacker extends GameElement implements Serializable {
    private static final long serialVersionUID = 0L;
    public static final String ATTACKER = "Attacker";
    public static final String HEALTH = "health";
    public static final String SPEED = "speed";
    public static final String DAMAGE = "damage";
    public static final String PATH = "path";
    private int health;
    private Speed speed;
    private Damage damage;
    private Queue<Point2D.Double> path;

    public Attacker(Map<String,Object> args){
        super(args);
        health = (Integer)args.get(HEALTH);
        speed = new Speed(args.get(SPEED));
        setId(this.hashCode());
        damage = new Damage(args.get(DAMAGE));
        path = (LinkedList<Point2D.Double>) args.get(PATH);
    }

    @Override
    public List<BackEndCommand> update() {
        List<BackEndCommand> commands = new ArrayList<>();
        if(path.isEmpty()){
            commands.add(new DamageTowerCommand(getDamage()));
            commands.add(new RemoveActiveElement(getId()));
        }
        else{
            setLocation(path.poll());
            commands.add(new MoveElement(getId(),getLocation(),getHeading()));
        }
        return commands;
    }

    private int getDamage() {
        return damage.getDamage();
    }



    @Override
    public Element createElementCopy(Point2D.Double location) {
        super.createElementCopy(location);
        return new Attacker(getInitialParams());
    }

    @Override
    public String getElementType() {
        return ATTACKER;
    }

    public int getSpeed() {
        return speed.getSpeed();
    }

    public Queue<Point2D.Double> getPath() {
        return new LinkedList<>(path);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
