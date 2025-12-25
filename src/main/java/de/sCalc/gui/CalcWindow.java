package de.sCalc.gui;

import de.sCalc.logic.CalcHistory;
import de.sCalc.logic.CalcLogic;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class CalcWindow extends Application {

    private Label display;
    private CalcLogic logic = new CalcLogic();
    private CalcHistory historyManager = new CalcHistory();
    private BigDecimal firstNumber = null;
    private String operator = "";
    private boolean restart = true;

    @Override
    public void start(Stage primaryStage) {
        LoginWindow loginWindow = new LoginWindow(() -> {
            showCalculatorUI(primaryStage);
        });

        loginWindow.show(primaryStage);

    }

    private void showCalculatorUI(Stage primaryStage){
        BorderPane root = new BorderPane();

        root.setTop(createDisplayArea());
        root.setCenter(createKeyPad());

        Scene scene = new Scene(root, 400, 600);
        loadStyle(scene);
        keyboardInput(scene);

        primaryStage.setTitle("sCalc");
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private VBox createDisplayArea() {
        VBox topCointainer = new VBox();
        Button historyBtn = new Button("\uD83D\uDD52");
        historyBtn.getStyleClass().add("history-button");
        historyBtn.setOnAction(e -> historyManager.showCalcHistory());
        historyBtn.setFocusTraversable(false);

        HBox historyContainer = new HBox(historyBtn);
        historyContainer.setAlignment(Pos.CENTER_LEFT);
        historyContainer.setPadding(new javafx.geometry.Insets(3, 3, 0, 0));

        display = new Label("");
        display.getStyleClass().add("display-label");
        display.setMaxWidth(Double.MAX_VALUE);
        display.setAlignment(Pos.BOTTOM_RIGHT);
        display.setPrefHeight(150);

        topCointainer.getChildren().addAll(historyContainer, display);
        return topCointainer;
    }

    private GridPane createKeyPad(){
        GridPane grid = new GridPane();
        grid.getStyleClass().add("button-grid");
        grid.setAlignment(Pos.CENTER);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        int col = 0;
        int row = 0;

        for (String text : buttons) {
            Button calcBtn = new Button(text);
            calcBtn.getStyleClass().add("calc-button");
            calcBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            calcBtn.setFocusTraversable(false);
            GridPane.setHgrow(calcBtn, Priority.ALWAYS);
            GridPane.setVgrow(calcBtn, Priority.ALWAYS);

            calcBtn.setOnAction(e -> handleButtonClick(text));

            grid.add(calcBtn, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
        return grid;
    }

    private void loadStyle(Scene scene){
        try {
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Fehler: style.css wurde nicht in 'resources' gefunden!");
        }
    }

    private void keyboardInput(Scene scene){

        scene.setOnKeyPressed(event -> {
            javafx.scene.input.KeyCode code = event.getCode();

            if (code == KeyCode.ENTER) {
                handleButtonClick("=");
            } else if (code == KeyCode.C) {
                handleButtonClick("C");
            } else if(code == KeyCode.BACK_SPACE){
                handleButtonClick("REMOVE");
            }
        });

        scene.setOnKeyTyped(event -> {
            String character = event.getCharacter();
            if("0123456789+-*/".contains(character)) {
                handleButtonClick(character);
            }
            else if (character.equals(",") || character.equals(".")) {
                handleButtonClick(".");
            }
        });
    }

    private void handleButtonClick(String command) {
        if (command.equals("C")) {
            display.setText("");
            firstNumber = null;
            operator = "";
            restart = true;

        } else if (command.equals("REMOVE")) {
            String currentText = display.getText();
            if(!currentText.isEmpty()){
                display.setText(currentText.substring(0,currentText.length()-1));
            }
        } else if (command.equals("=")) {
            if (firstNumber != null && !operator.isEmpty()) {
                try {
                    String currentText = display.getText();
                    if (currentText.isEmpty()) return;

                    String[] parts = currentText.split(" ");
                    String secondNumberString = parts[parts.length-1];

                    BigDecimal secondNumber = new BigDecimal(secondNumberString);
                    BigDecimal result = BigDecimal.ZERO;

                    switch (operator) {
                        case "+":
                            result = logic.add(firstNumber, secondNumber);
                            break;
                        case "-":
                            result = logic.sub(firstNumber, secondNumber);
                            break;
                        case "*":
                            result = logic.multiply(firstNumber, secondNumber);
                            break;
                        case "/":
                            result = logic.divide(firstNumber, secondNumber);
                            break;
                    }

                    String calculation = firstNumber + " " + operator + " " + secondNumber + " = " + result;

                    historyManager.addToHistory(calculation);

                    display.setText(result.toString());
                    restart = true;
                    firstNumber = null;
                    operator = "";
                } catch (Exception ex) {
                    display.setText("Error");
                    restart = true;
                }
            }
        } else if ("+-*/".contains(command)) {
            if (!display.getText().isEmpty()) {
                try {
                    String cleanFirstNumber = display.getText().split(" ")[0];

                    firstNumber = new BigDecimal(cleanFirstNumber);
                    operator = command;
                    restart = true;

                    display.setText(cleanFirstNumber + " " + command + " ");

                } catch (Exception e) {
                    display.setText("Error");
                }

            }
        } else {
            if (restart) {
                if (!operator.isEmpty()){
                    display.setText(display.getText() + command);
                } else {
                    display.setText(command);
                }
                restart = false;
            } else {
                display.setText(display.getText() + command);
            }

        }

    }


}