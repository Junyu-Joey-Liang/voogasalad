package voogasalad.authoring.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import voogasalad.authoring.util.elementbuilders.AuthoringIcon;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import static voogasalad.authoring.util.VisConstant.PADDING;

/**
 *
 */
public class VisualizationUtil {
    public static final String GAMEOBJECT_IMAGES_FOLDER = "\\src\\voogasalad\\authoring\\resources\\gameobjectimages\\";
    private static final String RELATIVE_PATH_SOURCE = "\\src\\voogasalad\\";
    private static final String USER_DIR = "user.dir";
    private static final String DOUBLE_SLASH = "[\\\\/]";

    /**
     * @param header
     */
    public static void showAlert(String header) {
        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * @param buttonName
     * @param e
     * @return
     */
    public static Button makeButton(String buttonName, EventHandler<ActionEvent> e) {
        Button button = new Button(buttonName);
        button.setOnAction(e);
        return button;
    }

    /**
     * @param buttonName
     * @param e
     * @return
     */
    public static Button makeCancelButton(String buttonName, EventHandler<ActionEvent> e) {
        Button button = new Button(buttonName);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setOnAction(e);
        return button;
    }


    /**
     * @param pane
     * @param color
     */
    public static void setDefaultPaddingAndSpacing(Pane pane, Color color) {
        pane.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

//    public static AuthoringIcon createIcon(int ID, Image image, String name, double height, double width) {
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(height);
//        imageView.setFitWidth(width);
//        return new AuthoringIcon(ID, imageView, name);
//    }
//
//    public static AuthoringIcon createIcon(int ID, Image image, String name, double height, double width, EventHandler<MouseEvent> e) {
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(height);
//        imageView.setFitWidth(width);
//        return new AuthoringIcon(ID, imageView, name, e);
//    }

    /**
     * @param ID
     * @param view
     * @param name
     * @param height
     * @param width
     * @return
     */
    public static AuthoringIcon createIcon(int ID, ImageView view, String name, double height, double width) {
        ImageView imageView = new ImageView(view.getImage());
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return new AuthoringIcon(ID, imageView, name);

    }

    /**
     * @param ID
     * @param imagePath
     * @param name
     * @param height
     * @param width
     * @return
     */
    public static AuthoringIcon createIcon(int ID, String imagePath, String name, double height, double width) {
        ImageView imageView = initImageView(imagePath);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return new AuthoringIcon(ID, imageView, name);

    }

    /**
     * @param imagePath
     * @return
     */
    public static ImageView initImageView(String imagePath) {
        ImageView imageView = new ImageView();
        imagePath = imagePath.replace("\\", "/");
        Image image;
        try {
            image = new Image(new FileInputStream(System.getProperty(USER_DIR) + imagePath));
            imageView.setImage(image);
            return imageView;
        } catch (Exception e) {
            showAlert("Cannot load image!");
        }
        return null;
    }


    /**
     * @param imageFile
     * @param folderPath
     * @return
     */
    public static String copyImageAndReturnNewFilePath(File imageFile, String folderPath) {
        try {
            String actualFileName = imageFile.getPath().split(DOUBLE_SLASH)[imageFile.getPath().split(DOUBLE_SLASH).length - 1];
            String imagePathString = System.getProperty(USER_DIR) + folderPath + actualFileName;
            try {
                String newimagePathString = imagePathString.replace("\\", "/");
                Files.copy(imageFile.toPath(), Paths.get(newimagePathString));
            } catch (FileAlreadyExistsException e) {
                //Do nothing
            }
            imagePathString = imagePathString.substring(imagePathString.indexOf(RELATIVE_PATH_SOURCE));
            return imagePathString;
        } catch (Exception e) {
            showAlert("Cannot load image!");
        }
        return null;
    }
//
//    public static String[] getEnumAsStringArray(Class c) {
//        ArrayList<String> list = new ArrayList<>();
//        for (Object t : EnumSet.allOf(c)) {
//            list.add(t.toString());
//        }
//        return list.toArray(new String[list.size()]);
//    }

    private static HBox createHBox(int spacing, Pos pos, int height, Color color) {
        HBox r = new HBox(spacing);
        r.setAlignment(pos);
        r.setPrefHeight(height);
        VisualizationUtil.setDefaultPaddingAndSpacing(r, color);
        return r;
    }

    /**
     * @param it
     * @return
     */
    public static HBox createHBox(Iterator<String> it) {
        return createHBox(Integer.parseInt(it.next()), Pos.valueOf(it.next()), Integer.parseInt(it.next()), Color.valueOf(it.next()));
    }

    private static VBox createVBox(int spacing, Pos pos, int width, Color color) {
        VBox r = new VBox(spacing);
        r.setAlignment(pos);
        r.setPrefWidth(width);
        VisualizationUtil.setDefaultPaddingAndSpacing(r, color);
        return r;
    }

    /**
     * @param it
     * @return
     */
    public static VBox createVBox(Iterator<String> it) {
        return createVBox(Integer.parseInt(it.next()), Pos.valueOf(it.next()), Integer.parseInt(it.next()), Color.valueOf(it.next()));
    }


}
