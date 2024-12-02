package hangman;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangmanGame extends Application {
    private String userName;
    private String selectedWord;
    private String topic;
    private int wrongGuesses = 0;

    private Pane drawingPane;
    private Label wordLabel;
    private Label promptLabel;
    private List<Character> guessedLetters = new ArrayList<>();
    private DatabaseConnector dbConnector;

    @Override
    public void start(Stage primaryStage) {
        dbConnector = new DatabaseConnector(); // Initialize the database connector
        showUserNamePrompt(primaryStage);
    }

    // Step 1: Prompt for User Name
    private void showUserNamePrompt(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: lightblue;");

        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        Button submitButton = new Button("Submit");

        // Trigger action on Enter key press
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        submitButton.setOnAction(e -> {
            userName = nameField.getText();
            if (!userName.isEmpty()) {
                showTopicSelection(primaryStage);
            }
        });

        layout.getChildren().addAll(nameLabel, nameField, submitButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Step 2: Topic Selection Menu
    private void showTopicSelection(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label topicLabel = new Label("Choose a topic:");
        Button programmingButton = new Button("Programming");
        Button carsButton = new Button("Cars");
        Button physicsButton = new Button("Physics");

        // Trigger actions on Enter key press for each button
        programmingButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                programmingButton.fire();
            }
        });

        carsButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                carsButton.fire();
            }
        });

        physicsButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                physicsButton.fire();
            }
        });

        programmingButton.setOnAction(e -> startGame(primaryStage, "programming"));
        carsButton.setOnAction(e -> startGame(primaryStage, "cars"));
        physicsButton.setOnAction(e -> startGame(primaryStage, "physics"));

        layout.getChildren().addAll(topicLabel, programmingButton, carsButton, physicsButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    // Step 3: Start the Game
    private void startGame(Stage primaryStage, String selectedTopic) {
        this.topic = selectedTopic;
        this.selectedWord = getRandomWord(topic);

        BorderPane layout = new BorderPane();
        drawingPane = new Pane();
        drawingPane.setPrefSize(400, 300);

        wordLabel = new Label(createWordDisplay());
        wordLabel.setStyle("-fx-font-size: 20px;");
        promptLabel = new Label("Guess a letter!");
        promptLabel.setStyle("-fx-font-size: 16px;");

        VBox topLayout = new VBox(10, promptLabel, wordLabel);
        topLayout.setPadding(new Insets(10));
        layout.setTop(topLayout);
        layout.setCenter(drawingPane);

        drawGallows();

        TextField guessField = new TextField();
        guessField.setPromptText("Enter a letter...");
        Button submitGuessButton = new Button("Guess");

        // Trigger action on Enter key press
        guessField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitGuessButton.fire();
            }
        });

        // Clear input after pressing "Guess"
        submitGuessButton.setOnAction(e -> {
            handleGuess(guessField.getText().toLowerCase());
            guessField.clear(); // Clear the input field after guessing
        });

        HBox inputBox = new HBox(10, guessField, submitGuessButton);
        inputBox.setPadding(new Insets(10));
        layout.setBottom(inputBox);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Handle Player's Guess
    private void handleGuess(String guess) {
        if (guess.length() == 1 && !guessedLetters.contains(guess.charAt(0))) {
            guessedLetters.add(guess.charAt(0));

            if (selectedWord.contains(guess)) {
                updateWordLabel();
                promptLabel.setText("Correct! Keep guessing.");
                if (isWordGuessed()) {
                    saveGameResult(true);
                    showGameOver("You win!");
                }
            } else {
                drawNextBodyPart();
                promptLabel.setText("Wrong guess! Try again.");
                if (wrongGuesses == 6) {
                    saveGameResult(false);
                    showGameOver("You lose! The word was: " + selectedWord);
                }
            }
        } else {
            promptLabel.setText("Invalid input. Enter a single letter.");
        }
    }

    // Save Game Result to the Database
    private void saveGameResult(boolean won) {
        boolean resultSaved = dbConnector.saveGameResult(userName, topic, selectedWord, won, wrongGuesses);
        if (!resultSaved) {
            promptLabel.setText("Error saving game result.");
        }
    }

    // Display Game Over Message with Restart Option
    private void showGameOver(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(message);
        alert.setContentText("Would you like to play again?");

        ButtonType restartButton = new ButtonType("Restart");
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(restartButton, exitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == restartButton) {
                restartGame();
            } else {
                System.exit(0); // Exit the game
            }
        });
    }
    // Restart the Game
    private void restartGame() {
        // Reset the game state
        guessedLetters.clear();
        wrongGuesses = 0;
        selectedWord = null;
        topic = null;

        // Show the username prompt again
        Stage primaryStage = (Stage) drawingPane.getScene().getWindow();
        showUserNamePrompt(primaryStage);
    }

    // Update Word Label After Correct Guess
    private void updateWordLabel() {
        wordLabel.setText(createWordDisplay());
    }

    private String createWordDisplay() {
        StringBuilder displayWord = new StringBuilder();
        for (char c : selectedWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                displayWord.append(c).append(" ");
            } else {
                displayWord.append("_ ");
            }
        }
        return displayWord.toString().trim();
    }

    // Check if the Word Is Fully Guessed
    private boolean isWordGuessed() {
        for (char c : selectedWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    // Get Random Word Based on Topic
    private String getRandomWord(String topic) {
        List<String> words;
        if ("programming".equals(topic)) {
            words = Arrays.asList("array", "loops", "class");
        } else if ("cars".equals(topic)) {
            words = Arrays.asList("tesla", "jaguar", "volvo");
        } else if ("physics".equals(topic)) {
            words = Arrays.asList("force", "light", "atoms");
        } else {
            words = Arrays.asList("error");
        }
        return words.get(new Random().nextInt(words.size()));
    }

    // Draw the Gallows
    private void drawGallows() {
        Line base = new Line(50, 250, 150, 250); // Gallows base
        Line post = new Line(100, 50, 100, 250); // Vertical post
        Line beam = new Line(100, 50, 200, 50);  // Horizontal beam
        Line rope = new Line(200, 50, 200, 80);  // Rope
        drawingPane.getChildren().addAll(base, post, beam, rope);
    }

    // Draw the Hanging Man Progressively
    private void drawNextBodyPart() {
        if (wrongGuesses == 0) {
            drawingPane.getChildren().add(new Circle(200, 100, 20)); // Head
        } else if (wrongGuesses == 1) {
            drawingPane.getChildren().add(new Line(200, 120, 200, 180)); // Body
        } else if (wrongGuesses == 2) {
            drawingPane.getChildren().add(new Line(200, 140, 170, 120)); // Left arm
        } else if (wrongGuesses == 3) {
            drawingPane.getChildren().add(new Line(200, 140, 230, 120)); // Right arm
        } else if (wrongGuesses == 4) {
            drawingPane.getChildren().add(new Line(200, 180, 180, 220)); // Left leg
        } else if (wrongGuesses == 5) {
            drawingPane.getChildren().add(new Line(200, 180, 220, 220)); // Right leg
        }
        wrongGuesses++;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
