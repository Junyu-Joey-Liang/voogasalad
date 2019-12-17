package voogasalad.gameengine.game;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * runs active level information
 */
public abstract class ActiveLevel implements Serializable, LevelController, ActiveHandler {
    private int currentMoney;
    private static final long serialVersionUID = 1L;
    private Level level;
    public static final int STEP_SIZE = 60;
    public static final String DEFENDER = "defender";
    public static final String ATTACKER = "Attacker";


    private Map<Integer,List<Integer>> collisions;
    private int currentHealth;
    private int seconds;
    private Map<Integer,Element> activeElements;
    private List<FrontEndCommand> frontEndCommands;
    private int stepScore;

    public ActiveLevel(Level level){
        this();
        this.level = level;
    }

    public ActiveLevel(){
        seconds = 0;
        frontEndCommands = new ArrayList<>();
        collisions = new HashMap<>();
        this.activeElements = new HashMap<>();
        stepScore = 0;
    }

    /**
     * sets a new level
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    protected boolean activeAttackers(){
        for(Element element : activeElements.values()){
            if(element.getElementType().equals(ATTACKER)){
                return true;
            }
        }
        return false;
    }

    protected boolean attackersLeft(){
        for(Integer[] arr : level.getAttackerMap().values()){
            if(arr.length > seconds){
                return true;
            }
        }
        if(level.isInfinite()){
            seconds = 0;
            level.increaseMultiplier();
            return true;
        }
        return false;
    }

    /**
     * runs a list of back end commands by inputting itself into the command.
     * @param commands
     */
    public void runBackEndCommands(List<BackEndCommand> commands){
        for(BackEndCommand command : commands){
            command.execute(this);
        }
    }

    /**
     * returns if an item can be placed on the map given the element type
     * @param elementID
     * @param placePoint
     * @return
     */
    @Override
    public boolean canPlace(String elementID, Point2D.Double placePoint){
        return level.getLevelMap().canPlace(level.getLevelElements().get(elementID).getElementType(),placePoint);
    }

    /**
     * adds a newly created command to the front end commands
     * @param command
     */
    @Override
    public void addToFrontEndCommands(FrontEndCommand command){
        frontEndCommands.add(command);
    }

    /**
     * Clears the front end commands.
     */
    @Override
    public void clearFrontEndCommands() {
        frontEndCommands.clear();
    }

    @Override
    public Map<Integer, Element> getActiveElements() {
        return activeElements;
    }

    @Override
    public Map<Integer, List<Integer>> getCollisions() {
        return collisions;
    }

    /**
     * removes an active element from the map
     * @param id
     */
    @Override
    public void removeActiveElement(int id){
        if(activeElements.containsKey(id)){
            activeElements.remove(id);
        }
        else{
            //todo THROW ERROR
        }
    }

    @Override
    public void setCurrentHealth(int health){
        this.currentHealth = health;
    }

    @Override
    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * finds the oldest element
     * @param attackers
     * @return
     */
    @Override
    public int findOldestElement(List<Element> attackers){
        return level.getLevelMap().findOldestElement(attackers);
    }

    /**
     * returns the level element given a name
     * @param name
     * @return
     */
    @Override
    public Element getLevelElement(String name){
        return level.getLevelElements().get(name);
    }


    /**
     * updates the score based on what you've gained in a single step
     * @return
     */
    public int updateScore(){
        if(level.isTimeScore()){
            return 1;
        }
        int result = stepScore;
        stepScore = 0;
        return result;
    }

    @Override
    public void setStepScore(int stepScore) {
        this.stepScore += stepScore;
    }

    @Override
    public Queue<Point2D.Double> findPath(int speed){
        return level.getLevelMap().makeElementPath(speed);
    }


    protected int getSeconds() {
        return seconds;
    }


    protected void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    protected void setCollisions(Map<Integer, List<Integer>> collisions) {
        this.collisions = collisions;
    }

    @Override
    public int getCurrentMoney() {
        return currentMoney;
    }


    protected Level getLevel() {
        return level;
    }


    protected List<FrontEndCommand> getFrontEndCommands() {
        return new ArrayList<>(frontEndCommands);
    }

    @Override
    public int getAttackerScore(String attacker){
        return level.getAttackerScore(attacker);
    }

    @Override
    public int getAttackerIncome(String attacker){
        return level.getAttackerIncome(attacker);
    }

    @Override
    public int getCost(String name) {
        return getLevel().getCost().get(name);
    }
}
