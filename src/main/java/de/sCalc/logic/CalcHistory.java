package de.sCalc.logic;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

public class CalcHistory {
    private String history1 = null;
    private String history2 = null;
    private String history3 = null;

    public void addToHistory(String calculation){
        history3 = history2;
        history2 = history1;
        history1 = calculation;
    }

    public void showCalcHistory(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setGraphic(null);
        alert.setHeaderText("Letzte Rechnungen:");


        DialogPane dialogPane = alert.getDialogPane();
        try {
            dialogPane.getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
            );
        } catch (Exception e) {
            System.err.println("Style für Alert nicht gefunden.");
        }
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        if (history1 == null){
            alert.setContentText("Noch nichts gerechnet :(");
        } else if (history2 == null) {
            alert.setContentText(history1);
        } else if (history3 == null){
            alert.setContentText(history1 + "\n" + history2);
        } else alert.setContentText(history1 + "\n" + history2 + "\n" + history3);


        alert.showAndWait();
    }
}
