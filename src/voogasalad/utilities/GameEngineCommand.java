package voogasalad.utilities;

import voogasalad.gameengine.enginecontroller.EngineController;

public interface GameEngineCommand {
    void setController(EngineController controller);

    void execute();
}
