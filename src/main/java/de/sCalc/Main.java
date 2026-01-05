package de.sCalc;

import de.sCalc.gui.CalcWindow;
import de.sCalc.logic.Databasehandler;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Databasehandler.connect();
        Application.launch(CalcWindow.class, args);
    }
}