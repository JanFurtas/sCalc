package de.sCalc.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class LoginWindow {
    private final Runnable onLoginSuccess;

    public LoginWindow(Runnable onLoginSuccess){
        this.onLoginSuccess = onLoginSuccess;
    }

    public void show(Stage stage){
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2E2E2E; -fx-padding: 40;");

        Label title = new Label("Anmeldung");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold");

        javafx.scene.control.TextField userField = new TextField();
        userField.setPromptText("Benutzername");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");

        Button loginButton = new Button();
        loginButton.getStyleClass().add("calc-button");
        loginButton.setText("Login");

        loginButton.setOnAction( e -> {
            String user = userField.getText();
            String passwd = passwordField.getText();

            if (user.equals("admin") && passwd.equals("123")) {
                onLoginSuccess.run();
            } else {
                errorLabel.setText("Falsche Logindaten");
            }
        });

        root.getChildren().addAll(title, userField, passwordField, loginButton, errorLabel);

        Scene scene = new Scene(root, 300, 400);

        try {
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception ex) {
            System.err.println("Style nicht gefunden");
        }

        stage.setTitle("Login - sCalc");
        stage.setScene(scene);
        stage.show();
    }
}
