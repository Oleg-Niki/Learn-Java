package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private GameEngine gameEngine;
    private Label timerLabel;
    private int secondsElapsed;
    private Timeline timer;
    private String playerName;
    private Difficulty difficulty;
    private GridPane gameGrid;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        // Prompt for player name
        playerName = promptPlayerName();
        System.out.println("Player name: " + playerName); // Debug

        // Difficulty selection
        difficulty = selectDifficulty();
        System.out.println("Selected difficulty: " + difficulty); // Debug

        // Initialize game
        initializeGame();

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void initializeGame() {
        gameEngine = new GameEngine(difficulty.getRows(), difficulty.getCols(), difficulty.getMines());
        System.out.println("Game initialized with rows: " + difficulty.getRows() + ", cols: " + difficulty.getCols() + ", mines: " + difficulty.getMines()); // Debug

        initializeTimer();

        // Create game grid and UI
        root = new BorderPane();
        gameGrid = createGameGrid();
        root.setCenter(gameGrid);

        // Add timer UI
        timerLabel = new Label("Time: 0");
        root.setTop(timerLabel);
    }

    private void initializeTimer() {
        secondsElapsed = 0;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            timerLabel.setText("Time: " + secondsElapsed);
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private String promptPlayerName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Enter your name:");
        dialog.setContentText("Name:");
        return dialog.showAndWait().orElse("Player");
    }

    private Difficulty selectDifficulty() {
        ChoiceDialog<Difficulty> dialog = new ChoiceDialog<>(Difficulty.BEGINNER, Difficulty.values());
        dialog.setTitle("Select Difficulty");
        dialog.setHeaderText("Choose your difficulty level:");
        return dialog.showAndWait().orElse(Difficulty.BEGINNER);
    }

    private GridPane createGameGrid() {
        GridPane grid = new GridPane();
        for (int row = 0; row < gameEngine.getRows(); row++) {
            for (int col = 0; col < gameEngine.getCols(); col++) {
                Button cell = new Button();
                cell.setPrefSize(60, 60); // Adjusted cell size
                final int r = row, c = col; // Capture row and col for lambda

                cell.setOnMouseClicked(event -> {
                    if (event.isPrimaryButtonDown()) { // Left-click
                        System.out.println("Left-click at cell: (" + r + ", " + c + ")"); // Debug
                        if (gameEngine.isMine(r, c)) {
                            System.out.println("Mine hit at: (" + r + ", " + c + ")"); // Debug
                            // Show mine
                            ImageView mineImage = new ImageView(gameEngine.getMineImage());
                            mineImage.setFitWidth(50);
                            mineImage.setFitHeight(50);
                            cell.setGraphic(mineImage);
                            timer.stop();
                            showGameOverDialog();
                        } else {
                            revealCell(cell, r, c);
                        }
                    } else if (event.isSecondaryButtonDown()) { // Right-click
                        System.out.println("Right-click at cell: (" + r + ", " + c + ")"); // Debug
                        if (!gameEngine.isRevealed(r, c)) {
                            if (!gameEngine.isFlagged(r, c)) {
                                ImageView flagImage = new ImageView(gameEngine.getFlagImage());
                                flagImage.setFitWidth(50);
                                flagImage.setFitHeight(50);
                                cell.setGraphic(flagImage);
                                gameEngine.toggleFlag(r, c);
                            } else {
                                cell.setGraphic(null);
                                gameEngine.toggleFlag(r, c);
                            }
                        }
                    }
                });

                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private void revealCell(Button cell, int row, int col) {
        System.out.println("Revealing cell: (" + row + ", " + col + ")"); // Debug
        if (!gameEngine.isRevealed(row, col)) {
            int neighboringMines = gameEngine.getNeighboringMines(row, col);
            cell.setText(neighboringMines > 0 ? String.valueOf(neighboringMines) : "");
            cell.setDisable(true);
            gameEngine.revealCell(row, col);

            if (neighboringMines == 0) {
                // Recursively reveal adjacent cells
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int newRow = row + dr;
                        int newCol = col + dc;
                        if (newRow >= 0 && newRow < gameEngine.getRows() && newCol >= 0 && newCol < gameEngine.getCols()) {
                            Button neighbor = (Button) getNodeByRowColumnIndex(newRow, newCol, gameGrid);
                            if (neighbor != null) {
                                revealCell(neighbor, newRow, newCol);
                            }
                        }
                    }
                }
            }
        }
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You hit a mine! Game over.");
        alert.showAndWait();
        restartGame();
    }

    private void restartGame() {
        initializeGame();
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
