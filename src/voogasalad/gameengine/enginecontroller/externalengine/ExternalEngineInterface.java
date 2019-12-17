package voogasalad.gameengine.enginecontroller.externalengine;

import voogasalad.gameengine.game.Game;

/**
 * Rough Idea about the External Engine (General Engine) that connects Player and the Engine.
 * This class will hold the game loop. Right now in the design, the Player will make buttons that will
 * save/load/switch game and play/stop/change the speed of game loop, and these public methods exist to
 * support those features
 **/
public interface ExternalEngineInterface {
    /**
     * Called to start the game loop
     */
    void play();

    /**
     * Called to stop the game loop
     */
    void stop();

    /**
     * Called to load previously saved file (not sure if it will be the same Game object.
     * Additionally to the initial information that the 'first' Game object has, it needs to hold
     *
     * i. Status
     * ii. Current Level
     * iii. All the gameElements currently active
     * )
     */
    void loadGame(String filePath);

    Game saveGame();

    void setGame(String filePath);

    void nextLevel();

    void newGame(boolean level);
}
