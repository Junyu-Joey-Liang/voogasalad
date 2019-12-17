package voogasalad.player.playercommand;


import voogasalad.player.IMediator;

public class SendScoreCommand implements ICommand {
    private IMediator mediator;
    private int score;

    public SendScoreCommand(IMediator mediator, int score) {
        this.mediator = mediator;
        this.score = score;
    }

    @Override
    public void execute() {
        mediator.sendScore(score);
    }
}
