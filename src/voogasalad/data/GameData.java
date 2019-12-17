package voogasalad.data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.data.converter.game.GameMacro;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.gameengine.game.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the Data API by implementing saving and loading games to an XML file via XStream.
 *
 * @author Justin Havas
 */
public class GameData implements Data {
    private DataStorage myStorage;
    private String filepath;
    private XStream mySerializer = new XStream(new DomDriver());

    public GameData(String filepath) {
        this.filepath = filepath;
        // attempt to read from saved file
        try {
            getStorage();
        } catch (Exception e) {
            // no such filepath exists so create a new storage object
            myStorage = new DataStorage();
        }
    }

    /**
     * @see Data#saveData(GameMacroFeature)
     */
    @Override
    public void saveData(GameMacroFeature macro) throws UnknownDataException {
        myStorage.setAuthoringMacro(macro);
        new GameMacro().convert(macro, myStorage.getGame());

        // save to storage after iterating through the map
        try {
            FileWriter writer = new FileWriter(filepath);
            mySerializer.toXML(myStorage, writer);
        } catch (IOException e) {
            throw new UnknownDataException(e);
        }
    }

    /**
     * @see Data#saveData(GameMacroFeature)
     */
    @Override
    public void saveGame(Game game) {
        myStorage.setGame(game);
        try {
            FileWriter writer = new FileWriter(filepath);
            mySerializer.toXML(myStorage, writer);
        } catch (IOException e) {
            throw new UnknownDataException(e);
        }
    }

    /**
     * @see Data#retrieveGame()
     */
    @Override
    public Game retrieveGame() throws UnknownDataException {
        tryToGetStorage();
        return myStorage.getGame();
    }

    /**
     * @see Data#retrieveGameMacro()
     */
    @Override
    public GameMacroFeature retrieveGameMacro() {
        tryToGetStorage();
        return myStorage.getAuthoringMacro();
    }

    /**
     * @see Data#setFilePath(String)
     */
    @Override
    public void setFilePath(String filePath) {
        if (filePath != null) {
            this.filepath = filePath;
        }
    }

    /**
     * @see Data#getFilePath()
     */
    @Override
    public String getFilePath() {
        return filepath;
    }

    private void tryToGetStorage() {
        try {
            getStorage();
        } catch (Exception e) {
            throw new UnknownDataException(e);
        }
    }

    private void getStorage() throws Exception {
        String mySavedStorage = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
        myStorage = (DataStorage) mySerializer.fromXML(mySavedStorage);
    }
}
