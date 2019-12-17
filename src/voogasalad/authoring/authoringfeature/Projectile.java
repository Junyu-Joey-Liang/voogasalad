package voogasalad.authoring.authoringfeature;

public class Projectile extends AbstractFeature {
    private double speed;
    private double range;
    private String impactType;
    private int impactLevel;
    private int blastRadius;
    private static final String defaultImapctType = "EXPLODE";


    public Projectile(String name, String imagepath, double speed, double range, String impactType, int impactLevel, int blastRadius) {
        super(name, imagepath);
        this.speed = speed;
        this.range = range;
        this.impactType = impactType == null ? defaultImapctType : impactType;
        this.impactLevel = impactLevel;
        this.blastRadius = blastRadius;
    }

    @Override
    public boolean compare(AbstractFeature o) {
        Projectile obj = (Projectile) o;
        if (!(obj.getID() == getID()) || !(obj.getImagePath().equals(getImagePath())) || !(obj.getName().equals(getName())) || !(obj.speed == speed)) {
            return false;
        }
        if (!(obj.range == range) || !(obj.blastRadius == blastRadius) || !(obj.impactType.equals(impactType)) || !(obj.impactLevel == impactLevel)) {
            return false;
        }
        return true;
    }

    @Override
    public AbstractFeature deepCopy() {
        Projectile r = new Projectile(getName(), getImagePath(), getSpeed(), getRange(), getImpactType(), getImpactLevel(), getBlastRadius());
        r.setId(getID());
        return r;
    }


    public void setRange(double range) {
        this.range = range;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setImpactType(String impactType) {
        this.impactType = impactType;
    }

    public void setImpactLevel(int impactLevel) {
        this.impactLevel = impactLevel;
    }

    public double getRange() {
        return range;
    }

    public double getSpeed() {
        return speed;
    }

    public int getImpactLevel() {
        return impactLevel;
    }


    public String getImpactType() {
        return impactType;
    }


    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }
}
