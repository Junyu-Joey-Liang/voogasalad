package voogasalad.utilities;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public interface Element {
    /**
     * updates the element based on whatever needs to be done to update the specific element
     * @return
     */
    List<BackEndCommand> update();

    String getImage();

    /**
     * is able to create a copy of itself based on a new location given.
     * @param location the location given
     * @return
     */
    Element createElementCopy(Point2D.Double location);

    /**
     * returns the ID (hashcode) of a specific element
     * @return
     */
    int getId();

    /**
     * returns the given name of the specific object
     * @return
     */
    String getName();

    /**
     * returns the type of element it is i.e. attacker defender etc.
     * @return
     */
    String getElementType();

    /**
     * returns the initial parameters it used to initialize itself.
     * @return
     */
    Map<String, Object> getInitialParams();

    /**
     * returns the location of the element
     * @return
     */
    Point2D.Double getLocation();

    /**
     * returns the heading of the element
     * @return
     */
    int getHeading();

    /**
     * returns the radius of the element
     * @return
     */
    int getRadius();
}
