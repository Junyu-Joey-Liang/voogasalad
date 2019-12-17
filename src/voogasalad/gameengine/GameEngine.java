package voogasalad.gameengine;

import voogasalad.gameengine.game.Game;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import voogasalad.data.Data;
import voogasalad.data.GameData;
import java.util.List;
import java.util.Map;

/**
 * TODO: seperate game into static vs. active components
 *
 */
public class GameEngine implements engine {
    private Game game;
    private Data data;

    public GameEngine(String fileName){
        data = new GameData(fileName);
        game = data.retrieveGame();
    }

    @Override
    public List<FrontEndCommand> updateGame(List<BackEndCommand> userInputs, Map<Integer,List<Integer>> collisions) {
        game.runBackEndCommands(userInputs);
        return game.update(collisions);
    }

    public List<FrontEndCommand> initializeGame(){
        return game.initializeNewLevel();
    }

    public Game saveGame() {
        return game;
    }

    @Override
    public void restartGame() {
        game.restartGame();
    }

    @Override
    public void restartLevel() {
        game.restartLevel();
    }

}
