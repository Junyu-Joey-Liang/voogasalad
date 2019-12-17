package voogasalad.authoring.authoringfeature;


public class BasicGameObjectConfigurations {
    private int value;
    private int health;
    private int projectileID;
    private int size;

    public BasicGameObjectConfigurations(int value, int health, int projectileID, int size) {
        this.value = value;
        this.health = health;
        this.projectileID = projectileID;
        this.size = size;
    }

    public BasicGameObjectConfigurations(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getProjectile() {
        return projectileID;
    }

    public void setProjectile(int projectileID) {
        this.projectileID = projectileID;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean compare(BasicGameObjectConfigurations a){
        if (!(a.getHealth()==health)||!(a.getValue()==value)){
            return false;
        }
        return true;
    }
}
