package voogasalad.gameengine.game;

import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * THESE ARE ALL THE METHODS THAT COMMANDS NEED IN THE ACTIVE LEVEL. PROVIDES A LAYER OF PROTECTION
 */
public interface LevelController {

    /**
     * returns collisions
     * @return
     */
    Map<Integer, List<Integer>> getCollisions();

    /**
     * returns active elements
     * @return
     */
    Map<Integer, Element> getActiveElements();

    /**
     * returns the id of the oldest element
     * @param attackers
     * @return
     */
    int findOldestElement(List<Element> attackers);

    /**
     * returns a level element given a name
     * @param name
     * @return
     */
    Element getLevelElement(String name);

    /**
     * adds a new element
     * @param elementName
     * @param location
     */
    void newActiveElement(String elementName, Point2D.Double location);

    /**
     * removes an active element from the screen
     * @param id
     */
    void removeActiveElement(int id);

    /**
     * adds a command to the front end command list
     * @param command
     */
    void addToFrontEndCommands(FrontEndCommand command);

    /**
     * Clears all the front end commands
     */
    void clearFrontEndCommands();

    /**
     * returns if an item can be placed
     * @param elementID
     * @param placePoint
     * @return
     */
    boolean canPlace(String elementID, Point2D.Double placePoint);

    /**
     * sets the health of the current level
     * @param health
     */
    void setCurrentHealth(int health);

    /**
     * gets the current health
     * @return
     */
    int getCurrentHealth();

    /**
     * sets the step score by incrementing whatever necessary
     * @param stepScore
     */
    void setStepScore(int stepScore);

    /**
     * finds a path for an attacker to follow
     * @param speed
     * @return
     */
    Queue<Point2D.Double> findPath(int speed);

    /**
     * returns the score an attacker has when it is destroyed
     * @param attacker
     * @return
     */
    int getAttackerScore(String attacker);

    /**
     * gets the current money you have
     * @return
     */
    int getCurrentMoney();

    /**
     * updates the money based off a kill
     * @param currentMoney
     */
    void setCurrentMoney(int currentMoney);

    /**
     * returns the cost of an element
     * @param name
     * @return
     */
    int getCost(String name);

    /**
     * returns the money returned on an attacker kill
     * @param attacker
     * @return
     */
    int getAttackerIncome(String attacker);

}
