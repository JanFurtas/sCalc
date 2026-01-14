package de.sCalc.gui;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

import de.sCalc.logic.UserManager;

public class RegisterWindow {
    private final Runnable onRegisterSuccess;
    private Date birthdate;

    public RegisterWindow(Runnable onRegisterSuccess){
        this.onRegisterSuccess = onRegisterSuccess;
    }

    UserManager userManager = new UserManager();

    public void show(Stage stage){
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2E2E2E; -fx-padding: 40;");

        Label title = new Label("Anmeldung");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold");

        javafx.scene.control.TextField name = new TextField();
        name.setPromptText("Vorname");

        TextField lastName = new TextField();
        lastName.setPromptText("Nachname");

        TextField email = new TextField();
        email.setPromptText("E-Mail");

        DatePicker birthday = new DatePicker();
        birthday.setPromptText("Geburtstag");
        birthday.setOnAction(e -> {
            birthdate = Date.valueOf(birthday.getValue());
        });

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");

        Button loginButton = new Button();
        loginButton.setText("Schon Angemeldet?");
        loginButton.getStyleClass().add("login-register-dialog");
        loginButton.setOnAction( e -> {
            stage.close();
            LoginWindow loginWindow = new LoginWindow(null);
            Stage loginStage = new Stage();
            loginWindow.show(loginStage);
        });

        Button registerButton = new Button();
        registerButton.getStyleClass().add("calc-button");
        registerButton.setText("Registrieren");



        registerButton.setOnAction( e -> {
            String preName = name.getText();
            String lName = lastName.getText();
            String mail = email.getText();
            Date bday = Date.valueOf(birthday.getValue());
            String pwd = passwordField.getText();

            if (preName.isEmpty() || lName.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
                errorLabel.setText("Bitte alle Felder ausfüllen.");
            } else {
                try {
                    if (userManager.registerCheck(mail)){
                        errorLabel.setText("E-Mail ist schon vergeben!");
                    } else {

                        userManager.registerUser(preName, lName, mail, pwd, bday);

                        stage.close();
                        LoginWindow loginWindow = new LoginWindow(this.onRegisterSuccess);
                        Stage loginStage = new Stage();
                        loginWindow.show(loginStage);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }


        });

        root.getChildren().addAll(name, lastName, email, birthday, passwordField, registerButton, errorLabel, loginButton);

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
