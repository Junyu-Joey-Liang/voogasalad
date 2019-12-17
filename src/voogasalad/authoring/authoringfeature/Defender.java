package voogasalad.authoring.authoringfeature;


public class Defender extends AbstractFeature {
    private BasicGameObjectConfigurations basicConfigurations;
    private String targetingMechanism;
    private int radiusOfVision;
    private int coolDown;
    private int projectileID;
    private int numProjectile;
    private String defaultMechanism = "DumbTarget";

    public Defender() {
        super();
    }

    public Defender(String imagepath, String name, int value, int health, int projectileID,
                    String targetingMechanism, int radiusOfVision, int coolDown, int size, int numProjectile) {
        super(name, imagepath);
        this.basicConfigurations = new BasicGameObjectConfigurations(value, health, projectileID, size);
        this.targetingMechanism = targetingMechanism == null ? defaultMechanism : targetingMechanism;
        this.radiusOfVision = radiusOfVision;
        this.coolDown = coolDown;
        this.projectileID = projectileID;
        this.numProjectile = numProjectile;
    }

    @Override
    public boolean compare(AbstractFeature o) {
        Defender obj = (Defender) o;
        if (!(obj.getID()==getID())|| !(obj.getImagePath().equals(getImagePath())) ||!(obj.getName().equals(getName()))||!(obj.getProjectile()==projectileID)) {
            return false;
        }
        if (!(obj.radiusOfVision==radiusOfVision) ||!(obj.coolDown == coolDown)||!(basicConfigurations.compare(obj.basicConfigurations))||!(obj.targetingMechanism.equals(targetingMechanism))) {
            return false;
        }
        return true;
    }

    @Override
    public AbstractFeature deepCopy() {
        Defender r = new Defender(getImagePath(), getName(), getBasicConfigurations().getValue(), getBasicConfigurations().getHealth(), getProjectile(), getTargetingMechanism(), getRadiusOfVision(), getCoolDown(), getBasicConfigurations().getSize(), getNumProjectile());
        r.setId(getID());
        return r;
    }

    public BasicGameObjectConfigurations getBasicConfigurations() {
        return basicConfigurations;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getRadiusOfVision() {
        return radiusOfVision;
    }

    public void setRadiusOfVision(int radiusOfVision) {
        this.radiusOfVision = radiusOfVision;
    }

    public String getTargetingMechanism() {
        return targetingMechanism;
    }

    public void setTargetingMechanism(String targetingMechanism) {
        this.targetingMechanism = targetingMechanism;
    }

    public void setProjectile(int projectileID) {
        this.projectileID = projectileID;
    }

    public int getProjectile() {
        return projectileID;
    }

    public int getNumProjectile() {
        return numProjectile;
    }

    public void setNumProjectile(int numProjectile) {
        this.numProjectile = numProjectile;
    }

}

