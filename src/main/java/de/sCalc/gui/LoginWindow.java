package de.sCalc.gui;

import de.sCalc.logic.Databasehandler;
import de.sCalc.logic.UserManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Objects;

public class LoginWindow {
    private final Runnable onLoginSuccess;
    private Date birthdate;

    public LoginWindow(Runnable onLoginSuccess){
        this.onLoginSuccess = onLoginSuccess;
    }

    UserManager userManager = new UserManager();

    public void show(Stage stage){
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2E2E2E; -fx-padding: 40;");

        Label title = new Label("Anmeldung");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold");

        Region spacerBIG = new Region();
        VBox.setVgrow(spacerBIG, Priority.ALWAYS);

        Region spacerMID = new Region();
        VBox.setVgrow(spacerMID, Priority.SOMETIMES);

        TextField email = new TextField();
        email.setPromptText("E-Mail");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");

        Button loginButton = new Button();
        loginButton.getStyleClass().add("calc-button");
        loginButton.setText("Anmelden");

        Button registerButton = new Button();
        registerButton.setText("Hier registrieren!");
        registerButton.getStyleClass().add("login-register-dialog");
        registerButton.setOnAction( e -> {
            stage.close();
            RegisterWindow registerWindow = new RegisterWindow(this.onLoginSuccess);
            Stage registerStage = new Stage();
            registerWindow.show(registerStage);
        });

        loginButton.setOnAction( e -> {
            String mail = email.getText();
            String pwd = passwordField.getText();


            if (mail.isEmpty() || pwd.isEmpty()){
                errorLabel.setText("Bitte alle Felder ausfüllen.");
            } else {
                try {
                     if(Objects.equals(userManager.fetchPassword(mail, pwd), pwd)){
                         onLoginSuccess.run();
                     } else {
                         errorLabel.setText("Falsche Benutzerdaten");
                     }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });

        root.getChildren().addAll(spacerMID, email, passwordField, loginButton, errorLabel, spacerBIG, registerButton);

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
