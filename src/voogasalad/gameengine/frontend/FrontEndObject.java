package voogasalad.gameengine.frontend;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import voogasalad.player.util.ImageMaker;
import java.awt.geom.Point2D;

public class FrontEndObject {

    private static final double OPACITY = 0.15;

    private int ID;
    private String type;
    private ImageView imageView;
    private Circle visibleArea;
    private boolean hasVision = false;

    public FrontEndObject(int ID, String type, String filePath, double height, double width) {
        this.ID = ID;
        this.type = type;
        this.imageView = new ImageView(ImageMaker.getImage(filePath));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }

    public Node getNode() {
        return imageView;
    }

    public String getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public Node getVisibleArea() {
        return visibleArea;
    }

    public void setVisibleArea(double radius, Point2D.Double location) {
        visibleArea = new Circle(location.getX(), location.getY(), radius);
        visibleArea.setOpacity(OPACITY);
        visibleArea.setFill(Color.GREY);
        //visibleArea.setStyle("-fx-background-color: #ACADA8");
        hasVision = true;
    }

    public boolean hasVision() {
        return hasVision;
    }
}
