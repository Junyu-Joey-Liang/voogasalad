package gameengineandplayerexamples;//The following use case will demonstrate how the gameEngine and voogasalad.player will interact with each other during each
//frame.

import java.util.List;

/**
 * GameEngine API that all tower defense game engines will extend
 */
public interface GameEngine{
    public void createElement(Game game);
    public list<Command> update();
    public boolean canPlace(String elementType, Point location);
        }

/**
 * Player API that all game players will extend
 * Can be specific to a game if in the future, we want to extend what the voogasalad.player can do (such as live game editing)
  */
public interface Player{
    public void addElement(GameObject item, Point location);
    public void moveElement(GameObject item, Point location);
    public void update(List<Command> myCommands);
        }

/**
 * Command API that all game commands will extend
 * There will be a specific Abstract command class for each game to allow for flexibility
  */
public interface Command{
    public void execute();
}

/**
 * GameObject api that all game items will extend (e.g. attackers, defenders, road items, towers, etc)
 */
public interface GameObject{
    public void createObject(String attributes);
        }

/**
 * Abstract class that all different tower defense games will extend
 * This object holds all the properties of the game and the inital starting conditions of the game
  */
public abstract class Game{
    list<GameObject> attacker;
    list<GameObject> defender;
    list<Game> level;
    public Game(){
        attacker = new ArrayList<>();
        defender = new ArrayList<>();
    }
    public abstract void addAttacker(GameObject attacker);
    public abstract void addDefender(GameObject defender);
    public abstract void addLeve(Game level);
}

// The following is a specific implementation of a specific game (Balloons)
public class BalloonsGameEngine implements GameEngine{
    public void createElement(Game game);
}

/**
 * responsible for how the GUI looks like for Balloons
 * can be the default GUI or can be customized for Balloons
 */
public class BalloonsGamePlayer implements Player{

}

/**
 * all commands specific to Balloons will subclass this abstract class
 * the specific commands will be created through reflection
 */
public abstract class BalloonsCommand implements Command{
    public abstract void execute()
}

/**
 * holds all the BalloonsGame specific items created in the authoring environment and holds all the levels
 */
public class BalloonsGame extends Game{
}

/**
 * creates all the Balloons game specific game objects that are specified in the authoring environment
 */
public class BalloonsGameObject implements GameObject{

}

    /**
     * the following shows how the voogasalad.player and game engine interacts with each other during each frame of the game.
     * In each frame, the game engine will update the backend parameters of all the game objects and will return a list of
     * command objects to the voogasalad.player to update the GUI in the front end.
     */
    public static void main (String[] args) {
    //assuming all objects were created through reflection from reading in the Game object passed from Data in the actual implementation
    GameEngine myEngine = new BalloonsGameEngine();
    Player myPlayer = new BalloonsGamePlayer();
    var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> myPlayer.update(myEngine.update()));
    animation.getKeyFrames().add(frame);
    animation.play();

}