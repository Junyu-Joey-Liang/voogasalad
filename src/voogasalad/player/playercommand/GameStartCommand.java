package voogasalad.player.playercommand;

import voogasalad.player.IMediator;

public class GameStartCommand implements ICommand {
    private IMediator mediator;

    public GameStartCommand(IMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void execute() {
       mediator.startGame();
    }
}
