package de.sCalc;

import de.sCalc.gui.calcWindow;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        // Startet die JavaFX Anwendung über die calcWindow Klasse
        Application.launch(calcWindow.class, args);
    }
}