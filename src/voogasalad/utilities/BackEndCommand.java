package voogasalad.utilities;

import voogasalad.gameengine.game.LevelController;

public interface BackEndCommand {
    /**
     * is in charge of executing a specific command given the level. THis could be adding elements, removing elements etc.
     * @param level
     */
    void execute(LevelController level);
}
