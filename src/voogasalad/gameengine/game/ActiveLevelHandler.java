package voogasalad.gameengine.game;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import voogasalad.utilities.frontEndCommands.initialization.AddToDefenderTabCommand;
import voogasalad.utilities.frontEndCommands.initialization.SetRowColCommand;
import voogasalad.utilities.frontEndCommands.status.UpdateCoin;
import voogasalad.utilities.frontEndCommands.status.UpdateHealth;
import voogasalad.utilities.frontEndCommands.updateelements.CreateCommand;
import voogasalad.utilities.gameElements.Attacker;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * handles general initializing and updating
 */
public class ActiveLevelHandler extends ActiveLevel {
    public static final String OBSTACLE = "Obstacle";
    public static final String PATH = "path";
    private int steps;
    public ActiveLevelHandler(Level level){
        super(level);
        steps = 0;
    }

    public ActiveLevelHandler(){
        super();
    }

    @Override
    public List<FrontEndCommand> initialize(){
        setSeconds(0);
        steps = 0;
        setCurrentMoney(getLevel().getStartingMoney());
        setCurrentHealth(getLevel().getStartingHealth());
        addToFrontEndCommands(new SetRowColCommand(getLevel().getLevelMap().gridWidth(),getLevel().getLevelMap().gridHeight()));
        for(FrontEndCommand frontEndCommand : getLevel().getLevelMap().initialize()) {
            addToFrontEndCommands(frontEndCommand);
        }
        for(Element element : getLevel().getLevelElements().values()){
            if(element.getElementType().equals(DEFENDER) || element.getElementType().equals(OBSTACLE)){
                addToFrontEndCommands(new AddToDefenderTabCommand(element.getImage(),element.getName(),getLevel().getCost().get(element.getName())));
            }
        }
        handleLiveEditing();
        addToFrontEndCommands(new UpdateHealth(getCurrentHealth()));
        addToFrontEndCommands(new UpdateCoin(getCurrentMoney()));
        List<FrontEndCommand> result = new ArrayList<>(getFrontEndCommands());
        clearFrontEndCommands();
        return result;
    }

    private void handleLiveEditing(){
        Map<Integer,Element> temp = new HashMap<>(getActiveElements());
        getActiveElements().clear();
        for(Map.Entry<Integer,Element> entry : temp.entrySet()) {
            Element update = getLevelElement(entry.getValue().getName());
            if (update == null) {
                getActiveElements().put(entry.getKey(),entry.getValue());
                addToFrontEndCommands(new CreateCommand(entry.getKey(), entry.getValue().getImage(), entry.getValue().getElementType(), entry.getValue().getLocation(), entry.getValue().getHeading(), 1, entry.getValue().getRadius()));
            }
            else{
                if(update.getElementType() == ATTACKER) {
                    update.getInitialParams().put(PATH, ((Attacker)entry.getValue()).getPath());
                }
                update = update.createElementCopy(entry.getValue().getLocation());
                getActiveElements().put(update.getId(),update);
                addToFrontEndCommands(new CreateCommand(update.getId(), update.getImage(), update.getElementType(), update.getLocation(), update.getHeading(), 1, update.getRadius()));
            }
        }
    }


    /**
     * returns true if the current level is completed
     * @return
     */
    public boolean completed(){
        return !activeAttackers() && !attackersLeft();
    }

    /**
     * creates a new element
     * @param elementName
     * @param location
     */
    @Override
    public void newActiveElement(String elementName, Point2D.Double location){
        Element newElement = getLevel().getLevelElements().get(elementName).createElementCopy(location);
        getActiveElements().put(newElement.getId(),newElement);
        addToFrontEndCommands(new CreateCommand(newElement.getId(),newElement.getImage(),newElement.getElementType(),newElement.getLocation(),newElement.getHeading(), 1, newElement.getRadius()));
    }

    @Override
    public List<FrontEndCommand> update(Map<Integer,List<Integer>> collisions){
        List<BackEndCommand> backEndCommands = new ArrayList<>();
        setCollisions(collisions);
        steps++;
        if(steps > STEP_SIZE){
            steps = 1;
            setSeconds(getSeconds()+1);
            setCurrentMoney(getCurrentMoney() + getLevel().getIncome());
        }
        for(Element element : getActiveElements().values()){
            backEndCommands.addAll(element.update());
        }
        backEndCommands.addAll(getLevel().newAttackers(getSeconds()));
        runBackEndCommands(backEndCommands);
        backEndCommands.clear();
        addToFrontEndCommands(new UpdateHealth(getCurrentHealth()));
        addToFrontEndCommands(new UpdateCoin(getCurrentMoney()));
        List<FrontEndCommand> temp = new ArrayList<>(getFrontEndCommands());
        clearFrontEndCommands();
        return temp;
    }
}
