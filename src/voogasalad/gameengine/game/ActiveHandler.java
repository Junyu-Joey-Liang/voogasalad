package voogasalad.gameengine.game;

import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.util.List;
import java.util.Map;

public interface ActiveHandler {

    /**
     * THis initializes the game
     * @return
     */
    List<FrontEndCommand> initialize();

    /**
     * THis will update the game
     * @param collisions
     * @return
     */
    List<FrontEndCommand> update(Map<Integer,List<Integer>> collisions);
}
