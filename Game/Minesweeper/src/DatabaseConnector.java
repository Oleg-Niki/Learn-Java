package Game;

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


    // Save a player's score to the database
    public boolean saveScore(String playerName, String difficulty, int score, int timeSpent) {
        String query = "INSERT INTO player_scores (name, game, score, time_spent) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("Failed to establish a database connection.");
                return false;
            }

            stmt.setString(1, playerName);
            stmt.setString(2, difficulty);
            stmt.setInt(3, score);
            stmt.setInt(4, timeSpent);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Fetch the top 10 scores from the leaderboard for a specific game
    public List<String> getLeaderboard(String game) {
        List<String> leaderboard = new ArrayList<>();
        String query = "SELECT name, score, time_spent FROM player_scores WHERE game = ? ORDER BY score DESC LIMIT 10";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, game);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String playerName = rs.getString("name");
                int playerScore = rs.getInt("score");
                int timeSpent = rs.getInt("time_spent");

                leaderboard.add(playerName + " - Score: " + playerScore + " - Time Spent: " + timeSpent + " seconds");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return leaderboard;
    }

    // Display the leaderboard in the console (optional helper method)
    public void displayLeaderboard() {
        String query = "SELECT name, game, score, time_spent FROM player_scores ORDER BY score DESC LIMIT 10";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (conn == null) {
                System.err.println("Failed to establish a database connection.");
                return;
            }

            System.out.println("Top Scores:");
            while (rs.next()) {
                System.out.printf("Player: %s | Game: %s | Score: %d | Time: %d seconds%n",
                        rs.getString("name"),
                        rs.getString("game"),
                        rs.getInt("score"),
                        rs.getInt("time_spent"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
