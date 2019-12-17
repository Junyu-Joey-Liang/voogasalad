package voogasalad.gameengine.frontend;

import javafx.scene.Node;
import voogasalad.player.GameMainWindow;
import voogasalad.player.GamePlayScreen;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.backEndCommands.DragDefenderCommand;
import voogasalad.utilities.backEndCommands.PlaceDefenderCommand;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowerDefenseFrontEnd implements EngineFrontEnd {

    private static final int CIRCLE_ID = -10;

    private List<BackEndCommand> frontEndInput = new ArrayList<>();
    private List<Node> addedNode = new ArrayList<>();
    private SpriteHolder mySpriteHolder = new SpriteHolder();
    private CollisionManger myCollisionManager = new CollisionManger(mySpriteHolder);
    private GameMainWindow myGameWindow;
    private GamePlayScreen myPlayScreen;
    private Map<Integer, Node> initializeNodes = new HashMap<>();
    private Point2D.Double mouseLocation;
    private IEffectManager effectManager;
    private double gamePlayScreenXLoc = 0;
    private double gamePlayScreenYLoc = 0;
    private double gridHeight;
    private double gridWidth ;

    public TowerDefenseFrontEnd(GameMainWindow gameMainWindow) {
        System.out.println(gameMainWindow.getGamePane().getBoundsInParent().getMinY());
        myGameWindow = gameMainWindow;
        myPlayScreen = myGameWindow.getGamePlayScreen();
        effectManager = new ExplosionManager();
    }

    @Override
    public List<BackEndCommand> getUserInputs() {
        frontEndInput.clear();
        getDefenderChooseAction();
        List<BackEndCommand> list = new ArrayList<>();
        list.addAll(frontEndInput);
        effectManager.update();
        return list;
    }

    @Override
    public void moveNode(int ID, Point2D.Double location, double orientation) {
        setNodeLocation(mySpriteHolder.get(ID).getNode(), location);
    }

    @Override
    public void createNode(int ID, String filePath, String type, Point2D.Double location , double orientation, double size, double radius) {
        FrontEndObject frontEndObject = new FrontEndObject(ID, type, filePath, gridHeight*size, gridWidth*size);
        mySpriteHolder.addObject(frontEndObject);
        myPlayScreen.getPane().getChildren().add(frontEndObject.getNode());
        setNodeLocation(frontEndObject.getNode(), location);
        if(radius>0) {
            addVisionToFrontEndObject(frontEndObject, radius);
            mySpriteHolder.addVision(ID);
        }
        addedNode.add(frontEndObject.getNode());
    }

    @Override
    public void removeNode(int ID) {
        FrontEndObject frontEndObject = mySpriteHolder.get(ID);
        Node node = frontEndObject.getNode();
        addedNode.remove(node);
        myPlayScreen.getPane().getChildren().remove(node);
        if(frontEndObject.hasVision()) {
            myPlayScreen.getPane().getChildren().remove(frontEndObject.getVisibleArea());
        }
        mySpriteHolder.remove(ID);
    }

    @Override
    public void setLevel(int level) {
        clearScreen();
    }

    @Override
    public Map<Integer, List<Integer>> getCollision() {
        return myCollisionManager.getCollisionMap();
    }

    @Override
    public void drawNode(Node node, int ID, boolean isMapTile, boolean isMouseEvent) {
        if(initializeNodes.containsKey(ID)) {
            myPlayScreen.getPane().getChildren().remove(initializeNodes.get(ID));
        }
        initializeNodes.put(ID, node);
        myPlayScreen.getPane().getChildren().add(node);
        if(isMapTile) {
            gridHeight = node.getBoundsInLocal().getHeight();
            gridWidth = node.getBoundsInLocal().getWidth();
        }
        if(isMouseEvent) {
            setNodeLocation(node, mouseLocation);
        }
        adjustNodeLocation(node);
    }

    @Override
    public void addToDefenderTab(String filePath, String defenderType, int cost) {
        myGameWindow.addToDefenderTab(filePath, defenderType, cost);
    }

    @Override
    public void setStatus(String statusType, int amount) {
        myGameWindow.setStatus(statusType, amount+"");
    }

    @Override
    public void endOfLevel(String directory) {
        myGameWindow.endOfGameAction(directory);
    }

    @Override
    public void showEffect(int ID, double radius, String effect) {
        FrontEndObject frontEndObject = mySpriteHolder.get(ID);
        if(!frontEndObject.hasVision()) {
            addVisionToFrontEndObject(frontEndObject, radius);
            mySpriteHolder.addVision(ID);
        }
        Node node = effectManager.registerEffect(ID, radius * gridWidth);
        myPlayScreen.getPane().getChildren().add(node);
        node.setTranslateX(frontEndObject.getNode().getTranslateX()
                + frontEndObject.getNode().getBoundsInParent().getWidth()/2- node.getBoundsInParent().getWidth()/2);
        node.setTranslateY(frontEndObject.getNode().getTranslateY()
                + frontEndObject.getNode().getBoundsInParent().getHeight()/2- node.getBoundsInParent().getHeight()/2);
        effectManager.setLocation(ID);
    }

    @Override
    public void sendScore() {
        myGameWindow.updateScore();
    }

    public void showSettingPopUp() {
        myGameWindow.showSettingPopup();
    }

    private void clearScreen() {
        mySpriteHolder.clearAll();
        addedNode.clear();
        myGameWindow.newLevel();
        myPlayScreen.getPane().getChildren().clear();
        effectManager.clear();
    }

    private void setNodeLocation(Node node, Point2D.Double location) {
        node.setTranslateX(location.getX());
        node.setTranslateY(location.getY());
        adjustNodeLocation(node);
    }

    private void adjustNodeLocation(Node node) {
        node.setTranslateX(node.getTranslateX() + gamePlayScreenXLoc);
        node.setTranslateY(node.getTranslateY() + gamePlayScreenYLoc);
    }

    private void getDefenderChooseAction() {
        String defenderType = myGameWindow.getPressedDefender();
        if(!defenderType.equals("") && myPlayScreen.isMouseOnScreen()) {
            mouseLocation = myPlayScreen.getMouseLocation();
            Point2D.Double mouseGridCoordinate = new Point2D.Double((int) (mouseLocation.getY() / gridHeight),
                    (int) (mouseLocation.getX()/gridWidth));
            if(myPlayScreen.isPressed()) {
                frontEndInput.add(new PlaceDefenderCommand(myGameWindow.getPressedDefender(), mouseGridCoordinate));
                deleteCircle();
                myGameWindow.setDefenderReleased();
            } else {
                frontEndInput.add(new DragDefenderCommand(myGameWindow.getPressedDefender(), mouseGridCoordinate));
            }
        }
        else if(!myPlayScreen.isMouseOnScreen()) {
            deleteCircle();
        }
    }

    private void addVisionToFrontEndObject(FrontEndObject frontEndObject, double radius) {
        Node node = frontEndObject.getNode();
        Point2D.Double location = new Point2D.Double(node.getTranslateX() + node.getBoundsInParent().getWidth()/2,
                node.getTranslateY() + node.getBoundsInParent().getHeight()/2);

        frontEndObject.setVisibleArea(radius * gridWidth, location);
        myPlayScreen.getPane().getChildren().add(frontEndObject.getVisibleArea());
        adjustNodeLocation(frontEndObject.getVisibleArea());
    }

    private void deleteCircle() {
        myPlayScreen.getPane().getChildren().remove(initializeNodes.get(CIRCLE_ID));
        initializeNodes.remove(CIRCLE_ID);
    }
}