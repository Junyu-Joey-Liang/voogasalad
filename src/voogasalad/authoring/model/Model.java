package voogasalad.authoring.model;

import voogasalad.authoring.authoringfeature.AuthoringFeature;

import java.util.Map;
import java.util.Set;

public interface Model {

    int addAuthoringFeature(String type, AuthoringFeature authoringFeature);

    Set<AuthoringFeature> getAuthoringFeatures(String type);

    AuthoringFeature getAuthoringFeature(int ID);

    int deleteAuthoringFeature(String type, int ID);

    void load(Map<Integer,AuthoringFeature> idMap);

    Map<Integer, AuthoringFeature> getIDMap();

    void replaceAuthoringFeature(String type, int ID, AuthoringFeature newVersion);
}
