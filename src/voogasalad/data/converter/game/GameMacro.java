package voogasalad.data.converter.game;

import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.gameengine.game.Game;

/**
 * This class is used to set or override attributes of a Game object using a GameMacroFeature.
 *
 * @author Justin Havas
 */
public class GameMacro {

    /**
     * Sets or overrides attributes of a Game object using a GameMacroFeature.
     *
     * @param macro GameMacroFeature used to initialize the Game object
     * @param game Game object to be initialized
     */
    public void convert(GameMacroFeature macro, Game game) throws UnknownDataException {
        game.setTitle(macro.getName());
        game.setGameIcon(macro.getImagePath());
        game.setDiscription(macro.getDescription());
        new Levels(macro.getIdMap()).convert(macro.getLevelList(), game);
    }
}
