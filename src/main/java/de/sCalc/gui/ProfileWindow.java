package de.sCalc.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileWindow {
    public void show(){
        Stage profileSettings = new Stage();
        profileSettings.setTitle("Profil");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add(".profileMenue");

        Label label = new Label("BRR BRR");

        Scene profileScene = new Scene(layout, 300, 200);
        profileSettings.setScene(profileScene);
        profileSettings.show();
    }
}
