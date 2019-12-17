package voogasalad.gameengine.game;

import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.backEndCommands.CreateAttackerCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActiveAttackers {
    public static final int STEP_SIZE = 60;
    public static final double HUNDRED = 100.0;
    private Map<String, Integer[]> attackerMap;
    private int release;
    public ActiveAttackers(Map<String, Integer[]> attackerMap){
        this.attackerMap = attackerMap;
        release = 0;
    }

    /**
     * this will add a new attacker based on the current time and the multiplier given
     * @param seconds
     * @param multiplier
     * @return
     */
    public List<BackEndCommand> addNewAttackers(int seconds, Map<String,Integer> multiplier){
        List<BackEndCommand> result = new ArrayList<>();
        release ++;
        Random rand = new Random();
        for(Map.Entry<String,Integer[]> entry : attackerMap.entrySet()){
            result.addAll(addSpecificAttacker(seconds,multiplier,entry));
        }
        return result;
    }

    private List<BackEndCommand> addSpecificAttacker(int seconds, Map<String,Integer> multiplier, Map.Entry<String,Integer[]> entry){
        List<BackEndCommand> result = new ArrayList<>();
        Integer[] temp = entry.getValue();
        if(temp.length > seconds && temp[seconds] != 0){
            double releaseTime = (release * temp[seconds] * multiplier.get(entry.getKey())/HUNDRED)/STEP_SIZE;
            if(releaseTime >= 1){
                for(int i = 0; i < (int)releaseTime; i++) {
                    result.add(new CreateAttackerCommand(entry.getKey()));
                }
                release = 0;
            }
        }
        return result;
    }

    /**
     * This sets the attacker map that will be used to release attackers
     * @param attackerMap
     */
    public void setAttackerMap(Map<String, Integer[]> attackerMap) {
        this.attackerMap = attackerMap;
    }

    public Map<String, Integer[]> getAttackerMap() {
        return attackerMap;
    }
}
