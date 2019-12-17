package voogasalad.data.test;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voogasalad.authoring.authoringfeature.*;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.authoringfeature.enumtypes.ScoringRuleType;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;
import voogasalad.data.Data;
import voogasalad.data.GameData;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.gameengine.game.Game;
import voogasalad.gameengine.game.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class makes up the JUnit tests used to test the functionality of the Data API.
 *
 * @author Justin Havas
 */
public class TestData {
    private static final String NAME = "testData";
    private static final String DESCRIPTION = "testData description";
    private static final String IMAGE_PATH = "testData imagePath";
    private static final String FILE_PATH = System.getProperty("user.dir") + "/data/test/" + NAME + ".xml";
    private static final String UNKNOWN_FILE_PATH = System.getProperty("user.dir") + "/data/test/unknown_unknown.xml";
    private static final String LEVEL_NAME = "Level1";
    private static final String ATTACKER_NAME = "Attacker1";
    private static final String DEFENDER_NAME = "Defender1";
    private static final String OBSTACLE_NAME = "Obstacle1";
    private static final String PROJECTILE_NAME = "Projectile1";
    private static final String MAP_NAME = "Map1";

    private Data myData;
    private GameMacroFeature macro;
    private Integer id = 0;


    @BeforeEach
    void setUp () {
        myData = new GameData(FILE_PATH);
    }

    @Test
    void testSetFilePath() {
        String expected = "differentTestData.xml";
        myData.setFilePath(expected);
        assertEquals(expected, myData.getFilePath());
    }

    @Test
    void testGetFilePath() {
        assertEquals(FILE_PATH, myData.getFilePath());
    }

    @Test
    void testRetrieveGameWithNoSuchXMLFile() {
        myData.setFilePath(UNKNOWN_FILE_PATH);
        assertThrows(UnknownDataException.class, () -> myData.retrieveGame());
    }

    @Test
    void testRetrieveDataMapWithNoSuchXMLFile() {
        myData.setFilePath(UNKNOWN_FILE_PATH);
        assertThrows(UnknownDataException.class, () -> myData.retrieveGameMacro());
    }

    @Test
    void testRetrieveGameDoesNotThrow() {
        assertDoesNotThrow(() -> myData.retrieveGame());
    }

    @Test
    void testRetrieveGameAndItsAttributes() {
        Game game = myData.retrieveGame();
        assertEquals(game.getTitle(), NAME);
        assertEquals(game.getGameIcon(), IMAGE_PATH);
        assertEquals(game.getDiscription(), DESCRIPTION);
    }

    @Test
    void testRetrieveGameHasCorrectLevelElements() {
        setUpTestSaveData();
        Game game = myData.retrieveGame();
        assertTrue(macro.getLevelList().size() != 0);
        for (int i=0; i<macro.getLevelList().size(); i++) {
            Level level = game.getLevel(i);
            assertTrue(level.getLevelElements().containsKey(ATTACKER_NAME));
            assertTrue(level.getLevelElements().containsKey(DEFENDER_NAME));
            assertTrue(level.getLevelElements().containsKey(PROJECTILE_NAME));
            assertTrue(level.getLevelElements().containsKey(OBSTACLE_NAME));
            voogasalad.utilities.gameElements.Defender defender = (voogasalad.utilities.gameElements.Defender) level.getLevelElements().get(DEFENDER_NAME);
            assertEquals(defender.getProjectile().getName(), PROJECTILE_NAME);
        }
    }

    @Test
    void testRetrieveGameMacroDoesNotThrow() {
        assertDoesNotThrow(() -> myData.retrieveGameMacro());
    }

    @Test
    void testRetrieveGameMacroAndItsAttributes() {
        setUpTestSaveData();
        GameMacroFeature macroFeature = myData.retrieveGameMacro();
        assertEquals(macroFeature.getName(), macro.getName());
        assertEquals(macroFeature.getImagePath(), macro.getImagePath());
        assertEquals(macroFeature.getDescription(), macro.getDescription());
    }

    @Test
    void testRetrieveGameMacroIdMap() {
        setUpTestSaveData();
        GameMacroFeature macroFeature = myData.retrieveGameMacro();
        for (Integer i : macroFeature.getIdMap().keySet()) {
            assertEquals(macroFeature.getIdMap().get(i).getName(), macro.getIdMap().get(i).getName());
        }
    }

    @Test
    void testRetrieveGameMacroLevelList() {
        setUpTestSaveData();
        GameMacroFeature macroFeature = myData.retrieveGameMacro();
        for (int i=0; i<macroFeature.getLevelList().size(); i++) {
            assertEquals(macroFeature.getLevelList().get(i), macro.getLevelList().get(i));
        }
    }

    @Test
    void testSaveGameDoesNotThrow() {
        Game original = myData.retrieveGame();
        Game game = new Game();
        assertDoesNotThrow(() -> myData.saveGame(game));
        assertTrue(myData.retrieveGame().getTitle().isEmpty());
        assertDoesNotThrow(() -> myData.saveGame(original));
    }

    @Test
    void testSaveDataDoesNotThrow() {
        setUpTestSaveData();
        assertDoesNotThrow(() -> myData.saveData(macro));
    }

    private void setUpTestSaveData() {
        setUpGameMacroFeature();
        Integer levelId = addLevelToMacro(LEVEL_NAME);
        Integer attackerId = addAttackerToLevel(ATTACKER_NAME, levelId);
        Integer defenderId = addDefenderToLevel(DEFENDER_NAME, levelId);
        addObstacleToLevel(OBSTACLE_NAME, levelId);
        addProjectileToDefender(PROJECTILE_NAME, defenderId);
        addMapToLevel(MAP_NAME, levelId);
        addLevelAttributes(levelId, attackerId, defenderId);
    }

    private void setUpGameMacroFeature() {
        macro = new GameMacroFeature();
        macro.setIdMap(new HashMap<>());
        macro.setLevelList(new ArrayList<>());
        macro.setName(NAME);
        macro.setDescription(DESCRIPTION);
        macro.setImagePath(IMAGE_PATH);
        Integer macroId = getNewId();
        macro.getIdMap().put(macroId, macro);
    }

    private Integer addLevelToMacro(String name) {
        LevelFeature level = new LevelFeature();
        level.setName(name);
        Integer levelId = getNewId();
        macro.getLevelList().add(levelId);
        macro.getIdMap().put(levelId, level);
        return levelId;
    }

    private Integer addAttackerToLevel(String name, Integer levelId) {
        LevelFeature level = (LevelFeature) macro.getIdMap().get(levelId);
        Attacker attacker = new Attacker("",name,0,0,0);
        Integer attackerId = getNewId();
        level.getAvailabilities(LevelFeature.ATTACKER).put(attackerId, true);
        macro.getIdMap().put(attackerId, attacker);
        return attackerId;
    }

    private Integer addDefenderToLevel(String name, Integer levelId) {
        LevelFeature level = (LevelFeature) macro.getIdMap().get(levelId);
        Defender defender = new Defender("",name,0,0,0,"",0,0,0,0);
        Integer defenderId = getNewId();
        level.getAvailabilities(LevelFeature.DEFENDER).put(defenderId, true);
        macro.getIdMap().put(defenderId, defender);
        return defenderId;
    }

    private Integer addObstacleToLevel(String name, Integer levelId) {
        LevelFeature level = (LevelFeature) macro.getIdMap().get(levelId);
        Obstacle obstacle = new Obstacle(name,"",0,0,"Damage");
        Integer obstacleId = getNewId();
        level.getAvailabilities(LevelFeature.OBSTACLE).put(obstacleId, true);
        macro.getIdMap().put(obstacleId, obstacle);
        return obstacleId;
    }

    private Integer addProjectileToDefender(String name, Integer defenderId) {
        Defender defender = (Defender) macro.getIdMap().get(defenderId);
        Projectile projectile = new Projectile(name,"",0,0,"EXPLODE",0,0);
        Integer projectileId = getNewId();
        defender.setProjectile(projectileId);
        macro.getIdMap().put(projectileId, projectile);
        return projectileId;
    }

    private Integer addMapToLevel(String name, Integer levelId) {
        LevelFeature level = (LevelFeature) macro.getIdMap().get(levelId);
        MapElementType[][] met = new MapElementType[1][1];
        met[0][0] = MapElementType.BUILDABLE;
        MapFeature mapFeature = new MapFeature(name,1,1,new HashMap<>(), new Pair<>(null,null), met);
        Integer mapId = getNewId();
        level.setVal("MapSelectorElement", mapId);
        macro.getIdMap().put(mapId, mapFeature);
        return mapId;
    }

    private void addLevelAttributes(Integer levelId, Integer attackerId, Integer defenderId) {
        LevelFeature level = (LevelFeature) macro.getIdMap().get(levelId);
        Map<Integer,Integer> incomeMap = new HashMap<>();
        Map<Integer,Integer> costMap = new HashMap<>();
        Map<Integer,Integer> attackerValueMap = new HashMap<>();
        incomeMap.put(LevelBuildingConstants.TIME_INCOME_ID, 0);
        costMap.put(defenderId, 0);
        attackerValueMap.put(attackerId, 0);
        level.setVal("IncomeElement", incomeMap);
        level.setVal("DefendersCostElement", costMap);
        level.setVal("AttackerValueElement", attackerValueMap);
        setLevelRules(level);
    }

    private void setLevelRules(LevelFeature level) {
        Map<String,Object> map = new HashMap<>();
        map.put(LevelBuildingConstants.SCORING_RULE, ScoringRuleType.TIME.toString());
        map.put(LevelBuildingConstants.END_CONDITION, EndConditionType.INFINITE.toString());
        map.put(LevelBuildingConstants.STARTING_MONEY, 0);
        map.put(LevelBuildingConstants.STARTING_LIVES, 0);
        map.put(LevelBuildingConstants.ATTACKER_FLOW_TIME, 0);
        level.setVal("LevelRulesElement", map);
    }

    private Integer getNewId() {
        id = id + 1;
        return id;
    }
}
