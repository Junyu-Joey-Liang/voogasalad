package voogasalad.utilities.gameElements.elementFeatures;

/**
 * the damage composition class
 */
public class Damage {
    private int damage;
    public Damage(Object damage){
        this.damage = (int) damage;
    }

    public int getDamage() {
        return damage;
    }
}
