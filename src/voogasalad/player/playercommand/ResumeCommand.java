package voogasalad.player.playercommand;

import voogasalad.player.IMediator;

public class ResumeCommand implements ICommand {
    private IMediator mediator;

    public ResumeCommand(IMediator mediator) {
        this.mediator = mediator;
    }


    @Override
    public void execute() {
        mediator.playGame();
    }
}
