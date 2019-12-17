package voogasalad.utilities;

import java.awt.geom.Point2D;
import java.util.Map;

public abstract class GameElement implements Element {
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String STARTING_LOCATION = "startingLocation";
    public static final String HEADING = "heading";
    public static final String RADIUS = "radius";
    private Map<String,Object> intialParams;
    private String name;
    private String elementImage;
    private Point2D.Double location;
    private int id;
    private int heading;
    private int radius;

    /**
     * is the basic game element for all back end elements. does not implement the update method
     * @param args
     */
    public GameElement(Map<String,Object> args){
        intialParams = args;
        name = (String)args.get(NAME);
        elementImage = (String)args.get(IMAGE);
        heading = args.containsKey(HEADING) ? (Integer) args.get(HEADING) : 0;
        location = (Point2D.Double) args.get(STARTING_LOCATION);
        radius = args.containsKey(RADIUS) ? (Integer) args.get(RADIUS) : 0;
        id = this.hashCode();
    }

    @Override
    public Map<String, Object> getInitialParams() {
        return intialParams;
    }

    @Override
    public Point2D.Double getLocation() {
        return location;
    }

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    @Override
    public int getRadius(){
        return radius;
    }

    protected void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String getImage() {
        return elementImage;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public Element createElementCopy(Point2D.Double location) {
        getInitialParams().put(STARTING_LOCATION,location);
        return null;
    }
}
