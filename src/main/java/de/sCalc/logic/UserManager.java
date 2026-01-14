package de.sCalc.logic;

import java.sql.*;

public class UserManager{

    public int registerUser(String name, String lastName, String email, String password, Date birthday) throws SQLException {
        String userInsert = "INSERT INTO users(name, lastName, birthday, password, email) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Databasehandler.connect();
            PreparedStatement pStatement = conn.prepareStatement(userInsert, Statement.RETURN_GENERATED_KEYS)) {

            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setDate(3, birthday);
            pStatement.setString(4, password);
            pStatement.setString(5, email);

            int rowsAffected = pStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Hier holen wir die automatisch erstellte ID zurück
                try (ResultSet generatedKeys = pStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        System.out.println("Erfolg: User eingetragen mit ID " + newId);
                        return newId;
                    }
                }
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public String fetchPassword(String email, String password) throws SQLException {
        String sqlPWD = "SELECT password FROM users WHERE email = '" + email + "'";
        String output = null;
        try (Connection conn = Databasehandler.connect();
             PreparedStatement getPWD = conn.prepareStatement(sqlPWD);) {
            ResultSet rs = getPWD.executeQuery();
            if (rs.next()) {
                output = rs.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public boolean registerCheck(String email){
        String checkUserSQL = "SELECT Count(*) FROM users WHERE email = '" + email + "'";
        try (Connection conn = Databasehandler.connect();
            PreparedStatement checkUser = conn.prepareStatement(checkUserSQL)){

            ResultSet rs = checkUser.executeQuery();

            if (rs.next()){
                int anzahlMails = rs.getInt(1);
                return anzahlMails > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
