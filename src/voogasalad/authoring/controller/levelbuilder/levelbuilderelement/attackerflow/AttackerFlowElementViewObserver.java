package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackerflow;

public interface AttackerFlowElementViewObserver {
    void updateTimeChange(int ID, int newTime);

    void updateNumberChange(int ID, int time, int newNumber);
}
