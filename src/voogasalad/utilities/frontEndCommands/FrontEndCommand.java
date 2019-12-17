package voogasalad.utilities.frontEndCommands;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.GameEngineCommand;

public abstract class FrontEndCommand implements GameEngineCommand {
    @Override
    public abstract void setController(EngineController controller);
}
