package voogasalad.authoring.authoringfeature;

import static voogasalad.authoring.util.VisConstant.DEFAULT_ATT_IMG;
import static voogasalad.authoring.util.VisConstant.DEFAULT_ATT_NAME;

/**
 * Class to represent an Attacker game object. Stores all the configurations for a specific attacker object defined by the designer
 * (reason to include this class is specified in {@link voogasalad.authoring.controller.gameobjectsbuilder.attacker.AttackerController}
 */
public class Attacker extends AbstractFeature {
    private BasicGameObjectConfigurations basicConfigurations;
    private int damage;
    private int speed;

    /**
     * Constructor for the class, sets the input values for the class
     * @param img image path of the attacker view
     * @param name attacker's name
     * @param health attacker's health
     * @param damage attacker's damage
     * @param speed attacker's speed
     */
    public Attacker(String img, String name, int health, int damage, int speed) {
        super(name, img);
        this.basicConfigurations = new BasicGameObjectConfigurations(health); // uses composition instead of inheritance for greater flexibility
        this.damage = damage;
        this.speed = speed;
    }

    /**
     * Initializes a default attacker object.
     */
    public Attacker() {
        this(DEFAULT_ATT_IMG, DEFAULT_ATT_NAME, 1, 1, 1);
    }

    @Override
    public AbstractFeature deepCopy() { // creates a new attacker object with the same configuration as this attacker
        Attacker r = new Attacker(getImagePath(), getName(), getBasicConfigurations().getHealth(), getDamage(), getSpeed());
        r.setId(getID());
        return r;
    }

    @Override // compare this attacker to the input attacker obj. If all configurations are the same, this will return true.
    public boolean compare(AbstractFeature o) {
        Attacker obj = (Attacker) o;
        return (obj.getID() == getID() && obj.getImagePath().equals(getImagePath()) && obj.getName().equals(getName()) && obj.damage == damage && obj.speed == speed && obj.getName().equals(getName()) && basicConfigurations.compare(obj.basicConfigurations));
    }

    public BasicGameObjectConfigurations getBasicConfigurations() {
        return basicConfigurations;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
