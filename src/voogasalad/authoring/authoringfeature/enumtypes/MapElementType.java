package voogasalad.authoring.authoringfeature.enumtypes;

public enum MapElementType {

    SPAWN("Spawn Point"),
    END("End Point"),
    BUILDABLE("Buildable"),
    NOT_BUILDABLE("Not Buildable"),
    PATH_HORIZONTAL("Horizontal Path"),
    PATH_VERTICAL("Vertical Path"),
    PATH_L_NW("NW L Path"),
    PATH_L_NE("NE L Path"),
    PATH_L_SW("SW L Path"),
    PATH_L_SE("SE L Path"),
    PATH_T_LEFT("Left T Path"),
    PATH_T_RIGHT("Right T Path"),
    PATH_T_TOP("Top T Path"),
    PATH_T_BOTTOM("Bottom T Path"),
    PATH_CROSS("Cross Path");

    private String displayName;

    private MapElementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
