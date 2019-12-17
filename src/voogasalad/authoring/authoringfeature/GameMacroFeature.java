package voogasalad.authoring.authoringfeature;

import java.util.List;
import java.util.Map;

public class GameMacroFeature implements  AuthoringFeature {
    private static final String defaultImagePath = "/voogasalad/authoring/resources/gameimages/defaultGame.png";
    private int id = -1;
    private String name;
    private String imagePath= defaultImagePath;
    private String description;
    private List<Integer> LevelList;
    private Map<Integer,AuthoringFeature> idMap;


    @Override
    public void addID(int ID) {
        this.id = ID;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getLevelList() {
        return LevelList;
    }

    public void setLevelList(List<Integer> levelList) {
        LevelList = levelList;
    }

    public void setIdMap(Map<Integer, AuthoringFeature> idMap) {
        this.idMap = idMap;
    }

    public Map<Integer, AuthoringFeature> getIdMap() {
        return idMap;
    }
}
