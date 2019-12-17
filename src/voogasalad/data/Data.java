package voogasalad.data;

import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.gameengine.game.Game;

/**
 * This interface specifies all the methods that can be called within the Data API which includes the ability to save and load
 * aspects of a specific game to a file.
 *
 * @author Justin Havas
 */
public interface Data {

    /**
     * Creates a Game object and saves it and the provided GameMacroFeature to a data file.
     * @param macro the GameMacroFeature provided from the authoring environment to create and save a Game object
     */
    public void saveData(GameMacroFeature macro) throws UnknownDataException;

    /**
     * Saves game to a data file.
     * @param game Game object to be saved to the current filepath
     */
    public void saveGame(Game game);

    /**
     * Returns an Game object from the current filepath.
     */
    public Game retrieveGame() throws UnknownDataException;

    /**
     * Returns a GameMacroFeature from the current filepath.
     */
    public GameMacroFeature retrieveGameMacro();

    /**
     * Set the filepath for the data file.
     * @param filePath filePath the Data API should use for saving and loading
     */
    public void setFilePath(String filePath);

    /**
     * Return the current filepath used to retrieve the Game or GameMacroFeature objects.
     */
    public String getFilePath();
}
