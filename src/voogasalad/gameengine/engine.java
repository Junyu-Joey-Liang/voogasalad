package voogasalad.gameengine;

import voogasalad.gameengine.game.Game;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.util.List;
import java.util.Map;

public interface engine {
    /**
     * updates the game given a list of user inputs (in the form of back end commands)
     * and a map of collisions between objects. This will allow for minimal communication between the front and back end
     * will still ensuring everything is achieved.
     * @param userInputs
     * @param collisions
     * @return
     */
    List<FrontEndCommand> updateGame(List<BackEndCommand> userInputs, Map<Integer,List<Integer>> collisions);

    List<FrontEndCommand> initializeGame();

    Game saveGame();

    void restartGame();

    void restartLevel();
}
