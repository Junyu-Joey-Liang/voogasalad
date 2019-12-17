package voogasalad.authoring.authoringfeature;

import javafx.scene.image.ImageView;
import voogasalad.authoring.util.VisualizationUtil;

/**
 *
 */
public abstract class AbstractFeature implements AuthoringFeature {
    // if id=-1, then not added to model yet
    private int id = -1;
    private String name;
    private String imagePath;

    /**
     *
     */
    public AbstractFeature(){
        // do nothing
    }

    /**
     * @param name
     * @param imagePath
     */
    public AbstractFeature(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    /**
     * @return
     */
    public abstract AbstractFeature deepCopy();

    public abstract boolean compare(AbstractFeature obj);

    @Override
    public void addID(int ID) {
        this.id = ID;
    }

    @Override
    public int getID() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public ImageView getView() {
        return VisualizationUtil.initImageView(imagePath);
    }

    /**
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return 
     */
    public String getImagePath() {
        return imagePath;
    }
}
