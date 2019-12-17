package testing.testingauthoring;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.testingauthoring.util.DukeApplicationTest;
import voogasalad.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapBuilderTest extends DukeApplicationTest {
    private static final int AUTHORING_TAB = 1;
    private static final int MAP_BUILDER = 4;

    private TabPane mainTabPane;
    private TabPane authoringTabPane;
    private Button mapStoreButton;
    private ComboBox<String> mapStoredMaps;
    private TextField mapNameField;
    private Slider mapRowSlider;

    // run this method BEFORE EACH test to set up application in a fresh state
    @BeforeEach
    public void setUp() throws Exception {
        // start GUI new for each test
        launch(Main.class);
        mainTabPane = lookup("#MainTabPane").queryAs(TabPane.class);
        authoringTabPane = lookup("#TABPANE").queryAs(TabPane.class);
        mapStoreButton = lookup("#MapStoreButton").queryButton();
        mapStoredMaps = lookup("#MapStoredMaps").queryComboBox();
        mapNameField = lookup("#MapNameField").queryAs(TextField.class);
        mapRowSlider = lookup("#MapRowSlider").queryAs(Slider.class);
    }

    @Test
    public void storeMapTest() {
        mainTabPane.getSelectionModel().select(AUTHORING_TAB);
        authoringTabPane.getSelectionModel().select(MAP_BUILDER);
        changeMapName("TestingName");
        clickOn(mapStoreButton);
        assertEquals("TestingName", mapStoredMaps.getValue());
    }

    @Test
    public void storeMultipleMapTest() {
        int rowVal1 = 10;
        int rowVal2 = 20;
        String firstName = "Name 1";
        String secondName = "Name 2";
        mainTabPane.getSelectionModel().select(AUTHORING_TAB);
        authoringTabPane.getSelectionModel().select(MAP_BUILDER);
        changeMapName(firstName);
        setValue(mapRowSlider, rowVal1);
        clickOn(mapStoreButton);
        changeMapName(secondName);
        setValue(mapRowSlider, rowVal2);
        clickOn(mapStoreButton);
        select(mapStoredMaps, firstName);
        assertEquals(rowVal1, mapRowSlider.getValue());
    }

    private void changeMapName(String name) {
        clickOn(mapNameField);
        for (int i = 0; i < 15; i++) {
            push(KeyCode.RIGHT);
        }
        for (int i = 0; i < 15; i++) {
            push(KeyCode.BACK_SPACE);
        }
        clickOn(mapNameField).write(name);
    }
}
