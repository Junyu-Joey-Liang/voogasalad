package voogasalad.gameengine.frontend;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import voogasalad.player.util.ImageMaker;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExplosionManager implements IEffectManager {

    private static final String EXPLOSION_FILE_DEFAULT = "/src/voogasalad/gameengine/frontend/resources/Explosion_";
    private static final String PNG = ".png";
    private static final int LENGTH_OF_EFFECT = 25;

    private Map<Integer, ImageView> imageViewMap = new HashMap<>();
    private Map<Integer, Integer> stateOfAnimationMap = new HashMap<>();
    private Map<Integer, Double> mySizes = new HashMap<>();

    private Map<Integer, Image> myImages = new HashMap<>();
    private Map<Integer, Point2D.Double> exploseions = new HashMap<>();
    private ArrayList<Integer> toBeRemoved = new ArrayList<>();

    @Override
    public Node registerEffect(int ID, double size) {
        ImageView imageView = new ImageView(getImage(1));
        mySizes.put(ID, size);
        setImageSize(imageView, ID);
        imageViewMap.put(ID, imageView);
        stateOfAnimationMap.put(ID, 1);
        return imageView;
    }

    @Override
    public void setLocation(int ID) {
        exploseions.put(ID, new Point2D.Double(imageViewMap.get(ID).getX(), imageViewMap.get(ID).getY()));
    }


    @Override
    public void update() {
        for(int ID : imageViewMap.keySet()) {
            int state = stateOfAnimationMap.get(ID) + 1;
            ImageView imageView = imageViewMap.get(ID);
            if(state > LENGTH_OF_EFFECT) {
                toBeRemoved.add(ID);
                ((Pane) imageView.getParent()).getChildren().remove(imageView);
            } else {
                stateOfAnimationMap.put(ID, state);
                imageView.setImage(getImage(state));
                setImageSize(imageView, ID);
            }
        }
        for(int ID : toBeRemoved) {
            stateOfAnimationMap.remove(ID);
            imageViewMap.remove(ID);
            mySizes.remove(ID);
        }
        toBeRemoved.clear();
    }

    @Override
    public void clear() {
        stateOfAnimationMap.clear();
        imageViewMap.clear();
        mySizes.clear();
        exploseions.clear();
        toBeRemoved.clear();
    }

    private Image getImage(int index) {
        if(!myImages.containsKey(index)) {
            myImages.put(index, ImageMaker.getImage(EXPLOSION_FILE_DEFAULT + index + PNG));
        }
        return myImages.get(index);
    }

    private void setImageSize(ImageView imageView, int ID) {
        imageView.setFitWidth(mySizes.get(ID) * 2);
        imageView.setPreserveRatio(true);
        //imageView.setLayoutX(exploseions.get(ID).getX());
       // imageView.setLayoutY(exploseions.get(ID).getY());
    }
}
