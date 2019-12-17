package voogasalad.player.gameselect;

import javafx.scene.image.Image;
import voogasalad.data.Data;
import voogasalad.data.GameData;
import voogasalad.gameengine.game.Game;
import voogasalad.player.util.ImageMaker;

public class GameInformation {
    private Image image;
    private String title;
    private String description;
    private String filePath;

    public GameInformation(String filePath) {
        Data data = new GameData(filePath);
        Game game = data.retrieveGame();
        this.image = ImageMaker.getImage(game.getGameIcon());
        this.title = game.getTitle();
        this.description = game.getDiscription();
        this.filePath = filePath;
    }

    public Image getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }
}
