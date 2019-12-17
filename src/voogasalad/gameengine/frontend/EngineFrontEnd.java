package voogasalad.gameengine.frontend;

import javafx.scene.Node;
import voogasalad.utilities.BackEndCommand;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public interface EngineFrontEnd {

    List<BackEndCommand> getUserInputs();

    void moveNode(int ID, Point2D.Double location, double orientation);

    void createNode(int ID, String filePath, String type, Point2D.Double location, double orientation, double size, double radius);

    void removeNode(int ID);

    void setLevel(int level);

    Map<Integer, List<Integer>> getCollision();

    void drawNode(Node node, int ID, boolean isMapTile, boolean isMouseEvent);

    void addToDefenderTab(String filePath, String defenderType, int cost);

    void setStatus(String statusType, int amount);

    void endOfLevel(String directory);

    void showEffect(int ID, double radius, String effect);

    void sendScore();

    void showSettingPopUp();
}
