package voogasalad.authoring.controller.levelbuilder;

public interface LevelBuilderViewObserver {

    void storeLevel();

    void loadLevel(String levelName);
}
