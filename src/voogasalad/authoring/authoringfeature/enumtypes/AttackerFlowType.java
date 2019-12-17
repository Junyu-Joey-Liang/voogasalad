package voogasalad.authoring.authoringfeature.enumtypes;

public enum AttackerFlowType {
    FREQUENCY("Frequency"),
    TIME_SPECIFIC("Time Specific");

    private String displayName;

    private AttackerFlowType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
