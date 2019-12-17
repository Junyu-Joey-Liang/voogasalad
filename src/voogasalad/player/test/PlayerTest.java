package voogasalad.player.test;

import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.player.IMediator;
import voogasalad.player.PlayerButton;
import voogasalad.player.gameselect.GameInformation;
import voogasalad.player.playercommand.ICommand;
import voogasalad.player.playercommand.SendScoreCommand;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Kyle Hong
 */

public class PlayerTest {
    private ICommand sendScoreCommand;
    private IMediator mediator;
    private PlayerButton playerButton;

    @Nested
    @DisplayName("Simple Reflection Tests")
    class ReflectionTest {
        @Test
        void TestInvocation() {
            int testInt = 0;
            sendScoreCommand = new SendScoreCommand(mediator, testInt);
            assertNotNull(sendScoreCommand);
        }

        @Test
        void TestButtonNotNull() {
            String testKey = "TestKey";
            String testLabel = "TestLabel";
            playerButton = new PlayerButton(mediator);
            assertThrows(ExceptionInInitializerError.class, () ->{
                Button button = playerButton.render(testKey, testLabel);
            });
        }
    }

    @Nested
    @DisplayName("Simple Configuration Tests")
    class ConfigurationTest {
        @Test
        void TestGame() {
            String wrongFilePath = "wrong";
            assertThrows(UnknownDataException.class, () -> {
                GameInformation gameInformation = new GameInformation(wrongFilePath);
            });
        }

        @Test
        void TestAction() {
            String wrongFilepath = "wrong";
            assertThrows(NullPointerException.class, () -> {
                mediator.changeGameAction(wrongFilepath);
            });
        }
    }
}
