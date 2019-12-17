package voogasalad.authoring.util;

import java.util.ResourceBundle;

public class VisConstant {
    private static final String CONTROLLERS_VIS_RESOURCE = "voogasalad.authoring.controller.gameobjectsbuilder.resources.controllersvisualization";
    public static final ResourceBundle gameObjectBundle = ResourceBundle.getBundle(CONTROLLERS_VIS_RESOURCE);
    public static final int TITLE_SIZE = Integer.parseInt(gameObjectBundle.getString("TitleSize"));
    public static final int PADDING = Integer.parseInt(gameObjectBundle.getString("Padding"));
    public static final double SPACING = Double.parseDouble(gameObjectBundle.getString("Spacing"));
    public static final double HUGE_ICON = Double.parseDouble(gameObjectBundle.getString("HugeIconSize"));
    public static final double LARGE_ICON = Double.parseDouble(gameObjectBundle.getString("LargeIconSize"));
    public static final double SMALL_ICON = Double.parseDouble(gameObjectBundle.getString("SmallIconSize"));
    public static final String GAMEOBJECT_IMAGES_FOLDER = "\\src\\voogasalad\\authoring\\resources\\gameobjectimages\\";
    public static final String GAME_IMAGES_FOLDER = "\\src\\voogasalad\\authoring\\resources\\gameimages\\";
    public static final String DEFAULT_ATT_IMG = "\\src\\voogasalad\\authoring\\resources\\gameobjectimages\\"+"defaultAttacker.png";
    public static final String DEFAULT_ATT_NAME = VisConstant.gameObjectBundle.getString("attackerDefaultName");
    public static final String RESOURCES_PATH = "voogasalad/authoring/authoringcontroller/resources/";

}
