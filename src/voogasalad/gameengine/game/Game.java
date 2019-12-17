package voogasalad.gameengine.game;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import voogasalad.utilities.frontEndCommands.endoflevel.EndOfLevelCommand;
import voogasalad.utilities.frontEndCommands.endoflevel.WinGameCommand;
import voogasalad.utilities.frontEndCommands.initialization.SetLevelCommand;
import voogasalad.utilities.frontEndCommands.status.UpdateScore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game implements Serializable {
private static final long serialVersionUID = 0L;
private List<Level> myLevelList;
private int currentLevel;
private ActiveLevelHandler activeLevel;
private String gameIcon;
private String title;
private String discription;
private int score;

    public Game() {
        this.myLevelList = new ArrayList<>();
        gameIcon = "";
        title = "";
        discription = "";
        currentLevel = 0;
        activeLevel = new ActiveLevelHandler();
        score = 0;
    }

    /**
     * adds a new level to the games
     * @param level
     */
    public void addLevel(Level level){
        myLevelList.add(level);
    }

    public Level getLevel(int levelNum){
            return myLevelList.get(levelNum);
    }

    public void restartLevel(){
        activeLevel.getActiveElements().clear();
        activeLevel.setSeconds(0);
    }

    public void restartGame(){
        activeLevel.getActiveElements().clear();
        currentLevel = 0;
        activeLevel.setLevel(myLevelList.get(currentLevel));
        activeLevel.setSeconds(0);
    }

    /**
     * clears the level list. used for live editing
     */
    public void clearLevels(){
        myLevelList.clear();
    }

    /**
     * after live editing happens it will call this to refresh the game
     */
    public void resetCurrentLevel(){
        if(currentLevel >= myLevelList.size()){
            currentLevel = 0;
            restartLevel();
        }
        activeLevel.setLevel(myLevelList.get(currentLevel));
    }

    /**
     * updates the specific level being played
     * @param collisions
     * @return
     */
    public List<FrontEndCommand> update(Map<Integer,List<Integer>> collisions){
        if(activeLevel.completed()){
            currentLevel++;
            if(currentLevel < myLevelList.size()){
                activeLevel.getActiveElements().clear();
                return new ArrayList<>(Arrays.asList(new EndOfLevelCommand()));
            }
            return new ArrayList<>(Arrays.asList(new WinGameCommand()));
        }
        List<FrontEndCommand> arrayList = activeLevel.update(collisions);
        score += activeLevel.updateScore();
        arrayList.add(new UpdateScore(score));
        return arrayList;
    }

    /**
     * runs back end commands if back end commands are called
     * @param commands
     */
    public void runBackEndCommands(List<BackEndCommand> commands){
        activeLevel.runBackEndCommands(commands);
    }

    public List<FrontEndCommand> initializeNewLevel(){
        activeLevel.addToFrontEndCommands(new SetLevelCommand(currentLevel));
        activeLevel.setLevel(myLevelList.get(currentLevel));
        return activeLevel.initialize();
    }

    public String getDiscription() {
        return discription;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
