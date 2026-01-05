package de.sCalc.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class Databasehandler {
    private static final String CONNECTION_STRING = "jdbc:sqlite:sCalc.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
            System.out.println("Fehler: SQLite-Treiber nicht gefunden! Hast du Maven neu geladen?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
