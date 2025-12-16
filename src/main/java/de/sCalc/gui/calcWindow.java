package de.sCalc.gui;

import de.sCalc.logic.calcLogic;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;

public class calcWindow extends Application {

    private Label display;
    private calcLogic logic = new calcLogic();

    // Speicher für die Rechnung
    private BigDecimal firstNumber = null;
    private String operator = "";
    private boolean restart = true;
    private ArrayList<String> calcHistory = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        VBox topCointainer = new VBox();
        Button historyBtn = new Button("\uD83D\uDD52");
        historyBtn.getStyleClass().add("history-button");
        historyBtn.setOnAction(e -> showCalcHistory());

        HBox historyContainer = new HBox(historyBtn);
        historyContainer.setAlignment(Pos.CENTER_LEFT);
        historyContainer.setPadding(new javafx.geometry.Insets(3, 3, 0, 0));

        display = new Label("");
        display.getStyleClass().add("display-label");
        display.setMaxWidth(Double.MAX_VALUE);
        display.setAlignment(Pos.BOTTOM_RIGHT);
        display.setPrefHeight(150);

        topCointainer.getChildren().addAll(historyContainer, display);
        root.setTop(topCointainer);

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
        root.setCenter(grid);

        Scene scene = new Scene(root, 400, 600);
        try {
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Fehler: style.css wurde nicht in 'resources' gefunden!");
        }

        primaryStage.setTitle("sCalc");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(String command) {
        if (command.equals("C")) {
            display.setText("");
            firstNumber = null;
            operator = "";
            restart = true;
        } else if (command.equals("=")) {
            if (firstNumber != null && !operator.isEmpty()) {
                try {
                    String currentText = display.getText();
                    if (currentText.isEmpty()) return;

                    BigDecimal secondNumber = new BigDecimal(currentText);
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

                    String rechnung = firstNumber + " " + operator + " " + secondNumber + " = " + result;
                    calcHistory.add(rechnung);

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
                firstNumber = new BigDecimal(display.getText());
                operator = command;
                restart = true;
            }
        } else {
            if (restart) {
                display.setText(command);
                restart = false;
            } else {
                display.setText(display.getText() + command);
            }
        }

    }

    private void showCalcHistory(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Verlauf");
        alert.setHeaderText("Letzte Rechnungen:");

        StringBuilder historyContent = new StringBuilder();
        if (calcHistory.isEmpty()){
            historyContent.append("Noch keine Rechnungen vorhanden.");
        } else {
            for (String entry : calcHistory){
                historyContent.append(entry).append("\n");
            }
        }
        alert.setContentText(historyContent.toString());
        alert.showAndWait();
    }
}