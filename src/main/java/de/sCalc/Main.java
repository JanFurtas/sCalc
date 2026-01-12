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

//TODO Login funktion mit Email & Password -> Mit eingetragenen daten
//TODO Eingabefilter im Registriermenü

//TODO Profil Menü im Rechner
//TODO Account löschen funktion
//TODO Bezahlmethode hinzufügen

//TODO Premiumversion einbauen

