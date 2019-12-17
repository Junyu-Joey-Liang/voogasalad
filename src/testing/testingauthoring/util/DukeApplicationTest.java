package testing.testingauthoring.util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;


/**
 * This class acts as an intermediary between a OpenJFX application and TestFX.
 *
 * It adds support for some extra UI components and employs a hack to get them all to work properly.
 *
 * @author Robert C. Duvall
 */
public class DukeApplicationTest extends ApplicationTest {
    // standard steps to do for all test applications so factor it out here
    @BeforeAll
    public static void setUpClass () {
        // explicitly use the most stable robot implementation to avoid some older versions
        //   https://stackoverflow.com/questions/52605298/simple-testfx-example-fails
        System.setProperty("testfx.robot", "glass");
    }

    // standard steps to do for all test applications so factor it out here
    @AfterEach
    public void tearDown () throws Exception {
        // remove stage of running app
        FxToolkit.cleanupStages();
        // clear any key or mouse presses left unreleased
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }


    // extra utility methods for different UI components
    protected void clickOn (ButtonBase b) {
        simulateAction(b, () -> b.fire());
    }

    protected void setValue (Slider s, double value) {
        simulateAction(s, () -> s.setValue(value));
    }

    protected void setValue (ColorPicker cp, Color value) {
        simulateAction(cp, () -> { cp.setValue(value); cp.fireEvent(new ActionEvent()); });
    }

    protected void select (ComboBox<String> cb, String value) {
        // FIXME: duplicated code - but no common ancestor defines getSelectionModel()
        simulateAction(cb, () -> cb.getSelectionModel().select(value));
    }

    protected void select (ChoiceBox<String> cb, String value) {
        // FIXME: duplicated code - but no common ancestor defines getSelectionModel()
        simulateAction(cb, () -> cb.getSelectionModel().select(value));
    }

    protected void select (ListView<String> lv, String value) {
        // FIXME: duplicated code - but no common ancestor defines getSelectionModel()
        simulateAction(lv, () -> lv.getSelectionModel().select(value));
    }

    // HACK: needed to get simulating an UI action working :(
    private void simulateAction (Node n, Runnable action) {
        // simulate robot motion, not strictly necessary but helps show what tests are being run
        moveTo(n);
        // fire event using given action on the given node
        Platform.runLater(action);
        // make it "later" so the requested event has time to run
        WaitForAsyncUtils.waitForFxEvents();
    }
}
