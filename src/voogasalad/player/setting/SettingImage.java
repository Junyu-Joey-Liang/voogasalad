package voogasalad.player.setting;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import voogasalad.player.IMediator;
import voogasalad.player.playercommand.ICommand;
import voogasalad.player.playercommand.StopGameCommand;
import voogasalad.player.util.ImageMaker;

public class SettingImage {
    private static final String IMAGE_PATH = "/data/images/setting.png";
    private static final double LAYOUT_X = 10;
    private static final double LAYOUT_Y = 450;
    private static final double HEIGHT = 40;

    private Node node;
    private SettingPopup mySettingPopup;
    private IMediator mediator;

    public SettingImage(IMediator mediator) {
        this.mediator = mediator;
        mySettingPopup = new SettingPopup(mediator);
        ImageView imageView = new ImageView(ImageMaker.getImage(IMAGE_PATH));
        imageView.setFitHeight(HEIGHT);
        imageView.setPreserveRatio(true);
        node = imageView;
        node.setOnMouseClicked(e -> callNodeAction());
        node.setTranslateX(LAYOUT_X);
        node.setTranslateY(LAYOUT_Y);
    }

    public Node getNode() {
        return node;
    }

    public void showSettingPopUp() {
        mySettingPopup.show();
    }

    private void callNodeAction() {
        ICommand command = new StopGameCommand(mediator);
        command.execute();
        mySettingPopup.show();
    }
}
