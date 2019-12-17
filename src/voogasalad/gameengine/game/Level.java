package voogasalad.gameengine.game;

import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.gameengine.map.GameMap;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level implements Serializable {
    public static final int HUNDRED = 100;
    private GameMap levelMap;

    //holds the sequence of attackers to show up on the screen
    //each index corresponds to one second
    //ex Key: red, blue, green
    //Value Integer[] that holds how many of each type of attacker is released per second

    //available attackers that can be created
    private Map<String,Element> availableElements;
    private ActiveAttackers activeAttackers;
    private int startingMoney;
    private int startingHealth;
    private int income;
    private Map<String,Integer> multiplier;
    private Map<String,Integer> setMultiplier;
    private Map<String,Integer> scores;
    private Map<String,Integer> attackerIncome;
    private Map<String,Integer> cost;
    private boolean infinite;
    private boolean timeScore;

    public Level() {
        availableElements = new HashMap<>();
        setMultiplier = new HashMap<>();
        scores = new HashMap<>();
        cost = new HashMap<>();
        startingHealth=0;
        startingMoney =0;
        income = 0;
        infinite = false;
        timeScore = false;
        activeAttackers = new ActiveAttackers(null);
        multiplier = new HashMap<>();
    }

    /**
     * adds all elements available for a given level into a map of elements
     * @param availableElements
     */
    public void addAvailableElements(List<Element> availableElements){
        for (Element element : availableElements) {
            this.availableElements.put(element.getName(),element);
        }
    }

    /**
     * sets the order for which the attackers will be coming on the map
     * @param myAttackerOrderMap
     */
    public void setAttackerOrder(Map<String, Integer[]> myAttackerOrderMap){
        activeAttackers.setAttackerMap(myAttackerOrderMap);
    }

    /**
     * sends the map to be parsed through based on the grid inputted from the authoring environment.
     * @param grid
     */
    public void setLevelMap(MapElementType[][] grid,Map<MapElementType,String> mapImage){
        levelMap = new GameMap(grid,mapImage);
    }

    public Map<String,Element> getLevelElements(){
        return availableElements;
    }

    public GameMap getLevelMap(){
        return this.levelMap;
    }

    public Map<String, Integer[]> getAttackerMap() {
        return activeAttackers.getAttackerMap();
    }

    public void setStartingHealth(int startingHealth) {
        this.startingHealth = startingHealth;
    }

    public void setStartingMoney(int startingMoney) {
        this.startingMoney = startingMoney;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setMultiplier(Map<String,Integer> multiplier) {
        this.multiplier = multiplier;
        for(String s : multiplier.keySet()){
            setMultiplier.put(s,HUNDRED);
        }
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public Map<String,Integer> getMultiplier() {
        return multiplier;
    }

    public List<BackEndCommand> newAttackers(int seconds){
        return activeAttackers.addNewAttackers(seconds,setMultiplier);
    }
    public void increaseMultiplier(){
        for(Map.Entry<String,Integer> entry : multiplier.entrySet()){
            setMultiplier.put(entry.getKey(),setMultiplier.get(entry.getKey()) * entry.getValue() / HUNDRED);
        }
    }
    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }
    public int getAttackerScore(String attacker){
        if(scores.containsKey(attacker)){
            return scores.get(attacker);
        }
        return 0;
    }

    public int getAttackerIncome(String attacker){
        if(attackerIncome.containsKey(attacker)){
            return attackerIncome.get(attacker);
        }
        return 0;
    }

    public void setCost(Map<String, Integer> cost) {
        this.cost = cost;
    }

    public Map<String, Integer> getCost() {
        return cost;
    }

    public Map<String, Integer> getAttackerIncome() {
        return attackerIncome;
    }

    public void setAttackerIncome(Map<String, Integer> attackerIncome) {
        this.attackerIncome = attackerIncome;
    }

    public boolean isTimeScore() {
        return timeScore;
    }

    public void setTimeScore(boolean timeScore) {
        this.timeScore = timeScore;
    }
}
