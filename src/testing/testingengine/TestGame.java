package testing.testingengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voogasalad.gameengine.GameEngine;
import voogasalad.gameengine.game.ActiveAttackers;
import voogasalad.gameengine.game.ActiveLevelHandler;
import voogasalad.gameengine.game.Game;
import voogasalad.gameengine.game.Level;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import voogasalad.utilities.gameElements.Defender;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TestGame {
    public static final String ONE = "one";
    public static final String COOL_DOWN = "coolDown";
    public static final String NUM_PROJECTILE = "numProjectiles";
    GameEngine engine;
    Level level;
    Game game;
    ActiveAttackers activeAttackers;
    Integer[] arr;
    ActiveLevelHandler activeLevelHandler;
    Map<String,Object> startingMap;
    public static final String fileName = "/Users/joshmedway/Library/Mobile Documents/com~apple~CloudDocs/CS_308/voogasalad_printstacktrace/data/games/Bloon.xml";

    @BeforeEach
    void setUp(){
        engine = new GameEngine(fileName);
        level = new Level();
        game = new Game();
        Map<String,Integer[]> map = new HashMap<>();
        arr = new Integer[]{1, 2, 3};
        map.put(ONE, arr);
        activeAttackers = new ActiveAttackers(map);
        activeLevelHandler = new ActiveLevelHandler();
        startingMap = new HashMap<>();
        startingMap.put(NUM_PROJECTILE,1);
        startingMap.put(COOL_DOWN,1);
    }

    /**
     * GAME ENGINE TESTS
     */
    @Test
    void testInitialize(){
       List<FrontEndCommand> list =  engine.initializeGame();
       assertTrue(list.size() > 0);
    }

    @Test
    void testUpdate(){
        engine.initializeGame();
        List<FrontEndCommand> list = engine.updateGame(new ArrayList<>(),new HashMap<>());
        assertTrue(list.size()>0);
    }

    /**
     * GAME TESTS
     */
    @Test
    void testAddLevel(){
        game.addLevel(level);
        assertTrue(game.getLevel(0).equals(level));
    }

    /**
     * LEVEL TESTS
     */

    @Test
    void testAddAvailableElements(){
        Defender defender = new Defender(startingMap);
        List<Element> list = new ArrayList<>(Arrays.asList(defender));
        level.addAvailableElements(list);
        assertTrue(level.getLevelElements().values().size() == 1);
    }

    /**
     * ACTIVE ATTACKER TESTS
     */
    @Test
    void testAttackerOrder(){
       assertTrue(activeAttackers.getAttackerMap().get(ONE).equals(arr));
    }

    @Test
    void testAddNewAttackers(){
        Map<String,Integer> map = new HashMap<>();
        map.put(ONE,6000);
        activeAttackers.addNewAttackers(0,map);
        List<BackEndCommand> list = activeAttackers.addNewAttackers(0,map);
        assertTrue(list.size() == 1);
    }

    /**
     * ACTIVE LEVEL TESTS
     */

    @Test
    void testRemoveActiveElement(){
        activeLevelHandler.getActiveElements().put(100,new Defender(startingMap));
        activeLevelHandler.removeActiveElement(100);
        assertTrue(activeLevelHandler.getActiveElements().keySet().size() == 0);
    }
}
