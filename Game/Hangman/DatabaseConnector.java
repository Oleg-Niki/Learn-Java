package hangman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:postgresql://us-west-1.97ec9ded-3d43-498d-b5ab-59bf6e92effa.aws.yugabyte.cloud:5433/yugabyte";
    private static final String DB_USER = "db_admin";
    private static final String DB_PASSWORD = "securepassword";

    // Get a database connection
    public Connection getConnection() {
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return null;
    }

    // Save a player's progress to the database
    public boolean saveGameResult(String playerName, String topic, String word, boolean won, int attempts, long timeSpent) {
        String query = "INSERT INTO hangman_results (player_name, topic, word, won, attempts, time_spent) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("Failed to establish a database connection.");
                return false;
            }

            stmt.setString(1, playerName);
            stmt.setString(2, topic);
            stmt.setString(3, word);
            stmt.setBoolean(4, won);
            stmt.setInt(5, attempts);
            stmt.setLong(6, timeSpent);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error saving game result: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Fetch leaderboard as structured table data
    public List<String[]> getLeaderboardAsTable() {
        List<String[]> leaderboard = new ArrayList<>();
        String query = "SELECT player_name, topic, attempts, time_spent FROM hangman_results ORDER BY attempts ASC, time_spent ASC LIMIT 10";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String playerName = rs.getString("player_name");
                String topic = rs.getString("topic");
                String attempts = String.valueOf(rs.getInt("attempts"));
                String timeSpent = String.valueOf(rs.getLong("time_spent"));
                leaderboard.add(new String[]{playerName, topic, attempts, timeSpent});
            }

        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return leaderboard;
    }


//    // Display the leaderboard in the console (optional)
//    public void displayLeaderboard() {
//        List<String> leaderboard = getLeaderboard();
//        System.out.println("Top Hangman Players:");
//        for (String entry : leaderboard) {
//            System.out.println(entry);
//        }
//    }
}
