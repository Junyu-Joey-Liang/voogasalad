package voogasalad.player;


import javafx.scene.control.Button;

public interface IButton {

    Button render(String key, String label);

   void onClick(String key);
}
