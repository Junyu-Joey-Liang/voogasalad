package voogasalad.authoring.model;

import voogasalad.authoring.authoringfeature.AuthoringFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;

public class BasicModel implements Model {
    private ResourceBundle rb = ResourceBundle.getBundle("voogasalad.authoring.model.resources.modelmap");
    private Map<String, Set<AuthoringFeature>> authoringFeatureMap;
    private Map<Integer, AuthoringFeature> idMap;
    private int currentId;

    public BasicModel() {
        currentId = 1;
        idMap = new HashMap<>();
        authoringFeatureMap = new HashMap<>();
    }

    @Override
    public int addAuthoringFeature(String type, AuthoringFeature authoringFeature) {
        int id = currentId;
        authoringFeature.addID(id);
        currentId++;
        idMap.put(id, authoringFeature);
        authoringFeatureMap.putIfAbsent(type, new HashSet<>());
        authoringFeatureMap.get(type).add(authoringFeature);
        return id;
    }

    @Override
    public Set<AuthoringFeature> getAuthoringFeatures(String type) {
        return authoringFeatureMap.get(type);
    }

    @Override
    public AuthoringFeature getAuthoringFeature(int ID) {
        return this.idMap.get(ID);
    }

    @Override
    public int deleteAuthoringFeature(String type, int ID) {
        if (this.idMap.containsKey(ID)){
            this.authoringFeatureMap.get(type).remove(this.idMap.get(ID));
            this.idMap.remove(ID);
            return ID;
        }
        return 0;
    }

    @Override
    public void load(Map<Integer, AuthoringFeature> idMap) {
        int maxid = 0;
        this.idMap = idMap;
        authoringFeatureMap = new HashMap<>();
        for (int id : idMap.keySet()){
            maxid = Math.max(maxid,id);
            AuthoringFeature obj = idMap.get(id);
            String key = getClassName(obj.getClass().toString());
            authoringFeatureMap.putIfAbsent(rb.getString(key), new HashSet<>());
            authoringFeatureMap.get(rb.getString(key)).add(obj);
        }
        currentId = maxid+1;
    }

    private String getClassName(String str){
        int i = str.lastIndexOf(".");
        return str.substring(i+1);
    }


    @Override
    public Map<Integer,AuthoringFeature> getIDMap() {
        return idMap;
    }

    @Override
    public void replaceAuthoringFeature(String type, int ID, AuthoringFeature newVersion) {
        deleteAuthoringFeature(type,ID);
        newVersion.addID(ID);
        idMap.put(ID,newVersion);
        authoringFeatureMap.putIfAbsent(type, new HashSet<>());
        authoringFeatureMap.get(type).add(newVersion);
    }


}
