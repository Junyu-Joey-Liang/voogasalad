package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.gameselect.GameSelectScreen;

public class ChangeButtonCommand implements ICommand {
    private IMediator mediator;

    public ChangeButtonCommand(IMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void execute() {
        GameSelectScreen gameSelectScreen = new GameSelectScreen(mediator);
        mediator.shutGame();
    }
}
