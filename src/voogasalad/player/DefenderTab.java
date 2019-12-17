package voogasalad.player;

import javafx.scene.Node;
import javafx.scene.layout.VBox;


public class DefenderTab {
    private static final double HEIGHT = 745;
    private static final double WIDTH = 200;
    private static final double X_LOCATION = 800;
    private static final String STYLE_SHEET = "/voogasalad/player/resources/stylesheet/DefenderTabStyle.css";
    private static final String CLICKED = "clicked";

    private DefenderBlock selectedBlock;
    private VBox myVBox = new VBox();
    private String selectedDefender = "";
    private int coin = 0;

    public DefenderTab() {
        myVBox.setPrefHeight(HEIGHT);
        myVBox.setPrefWidth(WIDTH);
        myVBox.setLayoutX(X_LOCATION);
        myVBox.getStylesheets().add(getClass().getResource(STYLE_SHEET).toExternalForm());
        myVBox.getStyleClass().add("root");
    }

    public Node getNode() {
        return myVBox;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void addDefenderBlock(String filePath, String defenderName, int cost) {
        DefenderBlock defenderBlock = new DefenderBlock(filePath, defenderName, cost);
        defenderBlock.getNode().setStyle("-fx-cursor: hand");
        defenderBlock.getNode().setOnMouseClicked(e -> {
            if(defenderBlock.getCost() <= coin) {
                boolean alreadySelected = selectedDefender.equals(defenderName);
                selectedDefender = alreadySelected ? "" : defenderName;
                if (selectedBlock != null || alreadySelected) {
                    selectedBlock.getNode().getStyleClass().remove(CLICKED);
                }
                else if(!alreadySelected) {
                    defenderBlock.getNode().getStyleClass().add(CLICKED);
                }
                selectedBlock = alreadySelected ? null : defenderBlock;
            }
        });
        myVBox.getChildren().add(defenderBlock.getNode());
    }

    public void clearDefenderBlock() { myVBox.getChildren().clear(); }

    public String getPressedDefender() {
        return selectedDefender;
    }

    public void dragReleased() {
        if(selectedBlock!=null) {
            selectedBlock.getNode().getStyleClass().remove("clicked");
            selectedBlock=null;
        }
        selectedDefender = "";
    }
}