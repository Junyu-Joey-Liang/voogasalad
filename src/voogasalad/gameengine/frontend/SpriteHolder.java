package voogasalad.gameengine.frontend;

import javafx.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteHolder {
    private Map<Integer, FrontEndObject> mySprites = new HashMap<>();
    private Map<Integer, Node> myVisions = new HashMap<>();

    public void addObject(FrontEndObject object) {
        mySprites.put(object.getID(), object);
    }

    public void addVision(int ID) {
        myVisions.put(ID, mySprites.get(ID).getVisibleArea());
    }

    public List<FrontEndObject> getSpecificType(String type) {
        List<FrontEndObject> list = new ArrayList<>();
        for(FrontEndObject myObject : mySprites.values()) {
            if(myObject.getType().equals(type)) {
                list.add(myObject);
            }
        }
        return list;
    }

    public Map<Integer, Node> getVisions() {
        Map<Integer, Node> map = new HashMap<>();
        map.putAll(myVisions);
        return map;
    }

    public void clearAll() {
        mySprites.clear();
        myVisions.clear();
    }

    public FrontEndObject get(int ID) {
        return mySprites.get(ID);
    }

    public void remove(int ID) {
        mySprites.remove(ID);
        myVisions.remove(ID);
    }
}
