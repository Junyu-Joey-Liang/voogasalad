package voogasalad.player.util;

import javafx.scene.image.Image;
import voogasalad.player.PlayerException;
import java.io.FileInputStream;

public class ImageMaker {

    public static final String ERROR_IMAGE_FOLDER = "/src/voogasalad/authoring/resources/defaultimage/";
    public static final String ERROR_IMAGE_PATH = "ERROR_IMAGE.png";

    public static Image getImage(String filePath) {
        return new ImageMaker().returnImage(filePath);
    }

    private Image returnImage(String imagePath) {
        try {
            imagePath = imagePath.replace("\\", "/");
            return new Image(new FileInputStream(System.getProperty("user.dir") + imagePath));
        } catch (Exception e) {
            try {
                return new Image(new FileInputStream(System.getProperty("user.dir") + ERROR_IMAGE_FOLDER + ERROR_IMAGE_PATH));
            } catch (Exception ee) {
                throw new PlayerException("No file", ee);
            }
        }
    }
}
