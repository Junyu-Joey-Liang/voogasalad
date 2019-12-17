package testing.testingplayer;

import org.junit.jupiter.api.Test;
import voogasalad.data.Data;
import voogasalad.data.GameData;
import voogasalad.gameengine.game.Game;
import voogasalad.player.gameselect.GameInformation;
import voogasalad.player.gameselect.GameLibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameMainWindowTest {

    @Test
    void testGameLibrarySize() {
        GameLibrary gameLibrary = new GameLibrary(System.getProperty("user.dir") + "/data/test");
        assertEquals(gameLibrary.getGameInformationList().size(), 1);
    }

    @Test
    void testGameInformation() {
        GameLibrary gameLibrary = new GameLibrary(System.getProperty("user.dir") + "/data/test");
        gameLibrary.refreshGamelibrary();
        GameInformation gameInformation = gameLibrary.getGameInformationList().get(0);
        Data data = new GameData(gameInformation.getFilePath());
        Game game = data.retrieveGame();
        assertEquals(game.getTitle(), "testData");
    }
}