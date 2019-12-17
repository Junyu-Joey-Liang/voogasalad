package voogasalad.gameengine.enginecontroller.frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import voogasalad.gameengine.frontend.EngineFrontEnd;
import voogasalad.player.util.ImageMaker;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class TowerDefenseFrontEndController extends FrontEndController {

    private static final double HEIGHT = 740;
    private static final double WIDTH = 800;
    private static final double INITIAL_GRID_WIDTH = 37;
    private static final double INITIAL_GRID_HEIGHT = 40;
    private static final int CIRCLE_ID = -10;
    private static final String LOSE_GAME_DIRECTORY = "voogasalad.player.resources.losegame.";
    private static final String NEXT_LEVEL_DIRECTORY = "voogasalad.player.resources.nextlevel.";
    private static final String WIN_GAME_DIRECTORY = "voogasalad.player.resources.wingame.";

    private double gridWidth = INITIAL_GRID_WIDTH;
    private double gridHeight = INITIAL_GRID_HEIGHT;
    private Map<String, Image> filePathMap = new HashMap<>();

    public TowerDefenseFrontEndController(EngineFrontEnd engineFrontEnd) {
        super(engineFrontEnd);
    }

    public void setLevel(int level) {
        getMyFrontEnd().setLevel(level);
    }

    public void moveNode(int ID, Point2D.Double location, double orientation) {
        getMyFrontEnd().moveNode(ID, gridToLocation(location), orientation);
    }

    public void createNode(int ID, String filePath, String nodeType, Point2D.Double location, double orientation, double size, double radius) {
        getMyFrontEnd().createNode(ID, filePath, nodeType, gridToLocation(location), orientation, size, radius);
    }

    public void removeNode(int ID) {
        getMyFrontEnd().removeNode(ID);
    }

    public void drawCircle(Color color, double radius) {
        Circle circle = new Circle(radius * gridHeight, color);
        circle.setOpacity(0.2);
        getMyFrontEnd().drawNode(circle, CIRCLE_ID, false, true);
    }

    public void addMapTile(String filePath, int row, int col) {
        if(!filePathMap.containsKey(filePath)) {
            filePathMap.put(filePath, ImageMaker.getImage(filePath));
        }
        ImageView mapTile = new ImageView(filePathMap.get(filePath));
        mapTile.setFitHeight(gridHeight);
        mapTile.setFitWidth(gridWidth);
        mapTile.setTranslateX(gridWidth * col);
        mapTile.setTranslateY(gridHeight * row);
        getMyFrontEnd().drawNode(mapTile, mapTile.hashCode(), true, false);

    }

    public void setRowAndCol(int row, int col) {
        gridHeight = HEIGHT/row;
        gridWidth = WIDTH/col;
    }

    public void addToDefenderTab(String filePath, String defenderType, int cost) {
        getMyFrontEnd().addToDefenderTab(filePath, defenderType, cost);
    }

    public void setStatus(String statusType, int amount) {
        getMyFrontEnd().setStatus(statusType, amount);
    }

    public void loseGame() {
        getMyFrontEnd().sendScore();
        getMyFrontEnd().endOfLevel(LOSE_GAME_DIRECTORY);
    }

    public void winGame() {
        getMyFrontEnd().sendScore();
        getMyFrontEnd().endOfLevel(WIN_GAME_DIRECTORY);
    }

    public void nextLevel() {
        getMyFrontEnd().endOfLevel(NEXT_LEVEL_DIRECTORY);
    }

    public void showEffect(int ID, double radius, String type) {
        getMyFrontEnd().showEffect(ID, radius, type);
    }

    private Point2D.Double gridToLocation(Point2D.Double gridPoint) {
        return new Point2D.Double(gridPoint.getY() * gridWidth, gridPoint.getX() * gridHeight);
    }
}
