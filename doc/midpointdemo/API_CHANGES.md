### Networking
#### Old Networking API
![](https://i.imgur.com/mOwfGZS.jpg)

#### New Networking API
![](https://i.imgur.com/Z1oM3Kg.png)

### Data
```java
/**
 * This interface gives the authoring environement and game player the ability to store and retrieve data.
 */
public interface Data {

    /**
     * Creates a Game object and saves it and the provided macro to a data file.
     * @param macro this is an authoring game object that will be converted into a backend Game object
     */
    public void saveData(GameMacroFeature macro) throws UnknownDataException;

    /**
     * Saves game to the current data filepath.
     */
    public void saveGame(Game game);

    /**
     * Returns a Game object from the current filepath.
     */
    public Game retrieveGame() throws UnknownDataException;

    /**
     * Returns a game macro authoring feature from the current filepath.
     * @return
     */
    public GameMacroFeature retrieveGameMacro();

    /**
     * Set the filepath for the data file.
     */
    public void setFilePath(String filePath);

    /**
     * Return the current filepath used to retrieve the Game or macro objects.
     */
    public String getFilePath();
}
```