package voogasalad.authoring.storageoperator;

public interface StorageOperator {
    /**
     * save the game to the filepath specified
     * @param filePath folder path to save to
     */
    void save(String filePath);

    /**
     * load the game from the filepath specified
     * @param filePath file path of the saved game
     */
    void load(String filePath);
}
