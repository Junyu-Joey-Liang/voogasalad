package voogasalad.gameengine.frontend;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionManger {

    private static final String ATTACKER = "Attacker";
    private static final String PROJECTILE = "projectile";
    private static final String OBSTACLE = "Obstacle";

    private SpriteHolder mySprites;

    public CollisionManger(SpriteHolder spriteHolder) {
        mySprites = spriteHolder;
    }

    public Map<Integer, List<Integer>> getCollisionMap() {
        Map<Integer, List<Integer>> collisionMap = new HashMap<>();
        collisionMap.putAll(getIntersection(mySprites.getSpecificType(PROJECTILE), mySprites.getSpecificType(ATTACKER)));
        collisionMap.putAll(getIntersection(mySprites.getSpecificType(OBSTACLE), mySprites.getSpecificType(ATTACKER)));
        collisionMap.putAll(getIntersection(mySprites.getVisions(), mySprites.getSpecificType(ATTACKER)));
        return collisionMap;
    }

    private Map<Integer, List<Integer>> getIntersection(List<FrontEndObject> list1, List<FrontEndObject> list2) {
        Map<Integer, List<Integer>> intersectionMap = new HashMap<>();
        for(FrontEndObject frontEndObject1 : list1) {
            intersectionMap.put(frontEndObject1.getID(), getIntersects(frontEndObject1.getNode(), list2));
        }
        return intersectionMap;
    }

    private Map<Integer, List<Integer>> getIntersection(Map<Integer, Node> nodes, List<FrontEndObject> frontEndObjects) {
        Map<Integer, List<Integer>> intersectionMap = new HashMap<>();
        for(Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            int ID = entry.getKey();
            List<Integer> list = getIntersects(entry.getValue(), frontEndObjects);
            entry.getValue().setVisible(!list.isEmpty());
            intersectionMap.put(ID, list);
        }
        return intersectionMap;
    }

    private boolean intersect(Node node1, Node node2) {
        return node1.getBoundsInParent().intersects(node2.getBoundsInParent());
    }

    private List<Integer> getIntersects(Node node, List<FrontEndObject> frontEndObjects) {
        List<Integer> list = new ArrayList<>();
        for(FrontEndObject frontEndObject : frontEndObjects) {
            if(intersect(node, frontEndObject.getNode())) {
                list.add(frontEndObject.getID());
            }
        }
        return list;
    }
}
