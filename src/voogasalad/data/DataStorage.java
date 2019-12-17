package voogasalad.data;

import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.gameengine.game.Game;

import java.io.Serializable;

/**
 * This class is the object that is stored and loaded to the XML file and contains instance variables in order to save
 * a Game object for the game engine and a GameMacroFeature for the authoring environment.
 *
 * @author Justin Havas
 */
public class DataStorage implements Serializable {
    private Game myGame;
    private GameMacroFeature macroFeature;

    public DataStorage() {
        myGame = new Game();
        macroFeature = null;
    }

    /**
     * Returns the Game object loaded from XML.
     */
    public Game getGame() {
        return myGame;
    }

    /**
     * Set the Game object to be stored to an XML.
     * @param game game to be stored
     */
    public void setGame(Game game) {
        if (game != null) {
            myGame = game;
        }
    }

    /**
     * Returns the GameMacroFeature object loaded from XML.
     */
    public GameMacroFeature getAuthoringMacro() {
        return macroFeature;
    }

    /**
     * Set the GameMacroFeature object to be stored to an XML.
     * @param macro macro to be stored
     */
    public void setAuthoringMacro(GameMacroFeature macro) {
        if (macro != null) {
            this.macroFeature = macro;
        }
    }
}
