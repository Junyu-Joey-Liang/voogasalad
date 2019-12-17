package voogasalad.authoring.authoringfeature;

public class Obstacle extends AbstractFeature {
    private BasicGameObjectConfigurations basicConfigurations;
    private int damage;
    private String obstacleType;

    public Obstacle(String name, String imagePath, int health, int damage, String obstacleType) {
        super(name, imagePath);
        this.basicConfigurations = new BasicGameObjectConfigurations(health);
        this.damage = damage;
        this.obstacleType = obstacleType;
    }

    @Override
    public boolean compare(AbstractFeature o) {
        Obstacle obj = (Obstacle) o;
        if (!(obj.getID() == getID()) || !(obj.getImagePath().equals(getImagePath())) || !(obj.getName().equals(getName()))) {
            return false;
        }
        if (!(obj.obstacleType.equals(obstacleType)) || !(obj.damage == damage)) {
            return false;
        }
        return true;
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


    public String getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(String obstacleType) {
        this.obstacleType = obstacleType;
    }

    @Override
    public AbstractFeature deepCopy() {
        Obstacle r = new Obstacle(getName(), getImagePath(), getBasicConfigurations().getHealth(), damage, obstacleType);
        r.setId(getID());
        return r;
    }
}
