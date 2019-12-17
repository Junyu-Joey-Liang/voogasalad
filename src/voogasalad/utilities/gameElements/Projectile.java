package voogasalad.utilities.gameElements;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;
import voogasalad.utilities.backEndCommands.RemoveActiveElement;
import voogasalad.utilities.gameElements.elementFeatures.Damage;
import voogasalad.utilities.gameElements.elementFeatures.Speed;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * the base of a projectile. This holds all of the important information to a projectile. anything more specified (i.e. blast radius) will go in the
 * individual implementations of this projectile
 */
public abstract class Projectile extends GameElement implements Element, Serializable {

    public static final String PROJECTILE = "projectile";
    public static final String ROOT = "voogasalad.utilities.backEndCommands.moveProjectile.";
    public static final String SPEED = "speed";
    public static final String DAMAGE = "damage";
    public static final int FRAMES_PER_SECOND = 60;
    private String moveType;
    private Speed speed;
    private boolean exploded;
    private Damage damage;
    private int counter;
    private int lifeTime;

    public Projectile(Map<String,Object> args){
        super(args);
        exploded = false;
        speed = new Speed(args.get(SPEED));
        damage = new Damage(args.get(DAMAGE));
        lifeTime = getRadius() * FRAMES_PER_SECOND;
        setRadius(0);
        counter = 0;
    }

    @Override
    public List<BackEndCommand> update() {
        List<BackEndCommand> commands = new ArrayList<>();
        setCounter(getCounter()+1);
        if(getLifeTime()/((getCounter()) * getSpeed()) == 0){
            commands.add(new RemoveActiveElement(getId()));
        }
        else{
            try {
                commands.add((BackEndCommand)Class.forName(ROOT + moveType).getDeclaredConstructor(Projectile.class).newInstance(this));
            }
            catch (Exception e){
                throw new InvalidParameterException("Projectile type not valid");
            }
        }
        return commands;
    }

    protected int getCounter() {
        return counter;
    }

    protected void setCounter(int counter) {
        this.counter = counter;
    }

    public int getSpeed() {
        return speed.getSpeed();
    }

    public int getLifeTime(){
        return lifeTime;
    }

    public int getDamage() {
        return damage.getDamage();
    }

    @Override
    public String getElementType() {
        return PROJECTILE;
    }

    protected void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
}
