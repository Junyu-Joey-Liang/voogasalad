# UTILITY

1. The game authoring environment currently contains various JavaFX element builders in the `voogasalad.authoring.util.elementbuilders` package. These utils create elements that can be added to the scene graph and contain methods to set behaviours and retrieve data

    For instance, the `LabelledImageElement` can return a Node containing an image of user-specified size with a text label below that is user-defined, with the image, the text label, and the size of the image all controllable through methods such as `setImage()` and `setName()`. 

    Other `elementbuilder` classes include `ToggleGroupInputElement` and `ImageWithMultipleIntegerSliderElement`. These classes could possibly be reused in other GUI classes. For instance, the Game Player could display the possible games that could be loaded using a `LabelledImageElement`, while the chat room could display active users with the `LabelledImageElement` as well.
    
    API:
    
    `ImageWithCheckBoxElement`
    
    ```java=
    public class ImageWithCheckBoxElement implements Viewable {

    public ImageWithCheckBoxElement(String name, String imagePath, int imageSize, String checkBoxLabel, ChangeListener listener);

    public Node getNode();

    public void setName(String name);

    public void setImage(String imagePath);

    public boolean getSelected();

    public void setSelected(boolean bool);
    }
    ```
    
    `ImageWithMultipleIntegerSliderElement`
    
    ```java=
    public class ImageWithMultipleIntegerSliderElement {

    public ImageWithMultipleIntegerSliderElement(String name, String imagePath, int imageSize, String firstSliderLabel, int firstSliderMinVal, int firstSliderMaxVal, int firstSliderDefaultVal, ChangeListener firstSliderListener, int sliderPrefWidth, String firstSliderName);

    public void addSlider(String sliderName, String sliderLabel, int sliderMinVal, int sliderMaxVal, int sliderDefaultVal, ChangeListener listener);

    public void setName(String name);

    public void setImage(String imagePath);

    public int getVal(String sliderName);

    public void setVal(String sliderName, int val);

    public void setMaxVal(String sliderName, int maxVal);

    public Node getNode();
    }

    ```
    
    `InputIntegerSliderElement`
    
    ```java=
    public class InputIntegerSliderElement {

    public InputIntegerSliderElement(String label, int minValue, int maxValue, int defaultValue, ChangeListener listener, int prefWidth);

    public InputIntegerSliderElement(String label, int minValue, int maxValue, int defaultValue, ChangeListener listener);

    public Node getNode();

    public int getVal();

    public void setVal(int val);

    public void setMaxVal(int maxVal);
    }
    ```
    
    `InputTextElement`
    
    ```java=
    public class InputTextElement {

    public InputTextElement(String label, String defaultValue);

    public Node getNode();

    public TextField getTextField();

    public String getText();

    public void setText(String text);
    }
    ```
    
    `LabelledImageElement`
    
    ```java=
    public class LabelledImageElement {
    public LabelledImageElement(String imageName, String imagePath, int imageSize);

    public Node getNode();

    public void setName(String name);

    public void setImage(String imagePath);
    
    }

    ```
    
    `ToggleGroupInputElement`
    
    ```java=
    public class ToggleGroupInputElement {
        
    public ToggleGroupInputElement(String labelName, ChangeListener listener);
    
    public ToggleGroupInputElement(String labelName);

    public void addToggle(String name);

    public String getVal();

    public void setVal(String name);

    public Node getNode();
    }
    ```
    
2. Game Elements: Game elements are used in both the authoring "creating" side and the game Engine "running" side. For the authoring side these elements serve as a storage system to store the states and values of each element. This could be an attacker's speed, or a defenders projectile type. For the back end, this data is used to properly update the Game that is running. This is through the Game Engine, and since these classes are already initialized, it's easy for the engine to use them.

```java=
public interface Element {
    List<BackEndCommand> update();
    Element createElementCopy(Point2D.Double location);
    int getId();
    String getName();
    int getCost();
    String getElementType();
    Map<String, Object> getInitialParams();
}
```

3. Commands: commands are utilized in the running game part of the project. This is a way for the front end player and game engine to communicate appropriately without having too much interaction with each other. There are "front end commands" and "back end commands" for which either the front end or back end of the engine can create and send to each other. This was implementation is hidden from each side, but whatever needs to be done is accomplished.

```java=
public interface BackEndCommand {

    void execute(Level level);
}

public interface FrontEndCommand {
    void setController(EngineController controller);

    void execute();
}
```

4. Messages: Messages are the objects sent over the network between the server and clients. The various message classes act as command objects which specifically are used to control actions a server takes and actions a clientInterface takes. This are used for updating game high score, changing the avatar, or validating the username and password, etc.

```java=
public abstract ServerMessage implements Message {
    public void execute(ServerInterface server);
}

public abstract ClientMessage implements Message {
    public void execute(ClientInterace clientInterface);
}
```

5. The router is a singleton design because a clientInterface only ever needs one router through which to send and receive data over the newtwork. This is a utility class because it can be used by anyone to send data but is only declared once. One use case is sending a game's state to a router in multiplayer gaming. Another use case is sending an error message to the router that the router's `Message` did not execute correctly.

```java=
public static Router {
    public void sendMessage(Message message);
}
```
6. Mediator pattern is utilized for communicationg among GUI objects in the player. `PlayerMediator` implements the Mediator interface and coordinates communication between concrete GUI objects. This is a utility class because it can be used by anyone to organize communication between objects but is only declared once.

```java=
public interface IMediator {

    void changeGameAction(String filePath);

    void loadGameAction(String filePath);

    void startGame();

    void saveGameAction();

    void playGameAction();

    void stopGameAction();


    void registerSelectScreen(GameSelectScreen gameSelectScreen);

    void registerStartScreen(GameStartScreen gameStartScreen);

    void registerGameMainWindow(GameMainWindow gameMainWindow);

    void newGameAction();
}

```