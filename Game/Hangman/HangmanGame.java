package hangman;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Hangman game with JavaFX built in SDK 11 Amazon Corretto.
 * Users can guess letters to discover a word based on a chosen topic.
 * The game includes a graphical interface and leaderboard.
 */

public class HangmanGame extends Application {
    private String userName; // Player's username
    private String selectedWord; // The word to guess
    private String topic; // Chosen topic for the word
    private int wrongGuesses = 0; // Count of wrong guesses
    private long startTime;

    private Pane drawingPane; // Pane for drawing the hangman
    private Label wordLabel; // Label to display the guessed word
    private Label promptLabel; // Label to prompt user actions
    private List<Character> guessedLetters = new ArrayList<>(); // List of guessed letters
    private DatabaseConnector dbConnector; // Database connector for saving results and fetching leaderboard

    @Override
    public void start(Stage primaryStage) {
        dbConnector = new DatabaseConnector(); // Initialize the database connector
        showUserNamePrompt(primaryStage);
    }

    /**
     * Step 1: Displays a prompt to enter the user's name.
     *
     * @param primaryStage the primary stage for the JavaFX application
     */

    private void showUserNamePrompt(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: lightblue;");

        Label nameLabel = new Label("Enter your name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        TextField nameField = new TextField();
        Button submitButton = new Button("LET'S GO");
        Button leaderboardButton = new Button("View Leaderboard");

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

        leaderboardButton.setOnAction(e -> showLeaderboardPopup(primaryStage));

        layout.getChildren().addAll(nameLabel, nameField, submitButton, leaderboardButton);


        // Add a fade-in transition for the layout
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), layout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Ensure a new Scene is created with the new VBox
        Scene scene = new Scene(new StackPane(layout), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // Display Leaderboard Popup
    private void showLeaderboardPopup(Stage ownerStage) {
        // Fetch leaderboard using the new method
        List<String[]> leaderboard = dbConnector.getLeaderboardAsTable();

        // Create a TableView
        TableView<String[]> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define columns
        TableColumn<String[], String> playerNameCol = new TableColumn<>("Player Name");
        playerNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        playerNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> topicCol = new TableColumn<>("Topic");
        topicCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        topicCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> attemptsCol = new TableColumn<>("Attempts");
        attemptsCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        attemptsCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> timeCol = new TableColumn<>("Time (s)");
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        timeCol.setStyle("-fx-alignment: CENTER;");

        // Add columns to the table
        tableView.getColumns().addAll(playerNameCol, topicCol, attemptsCol, timeCol);

        // Add data to the table
        leaderboard.forEach(entry -> tableView.getItems().add(entry));

        // Create a layout for the popup
        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(20));
        popupLayout.setStyle("-fx-background-color: lightgoldenrodyellow;");

        Label header = new Label("Leaderboard");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());

        popupLayout.getChildren().addAll(header, tableView, closeButton);

        // Show the popup
        Scene popupScene = new Scene(popupLayout, 500, 400);
        Stage popupStage = new Stage();
        popupStage.initOwner(ownerStage);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

        // Create a popup window to display the leaderboard
//        Stage popupStage = new Stage();
//        popupStage.initOwner(ownerStage);
//        VBox popupLayout = new VBox(10);
//        popupLayout.setPadding(new Insets(20));
//        popupLayout.setStyle("-fx-background-color: lightgoldenrodyellow;");
//
//        Label header = new Label("Leaderboard");
//        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//
//        VBox leaderboardBox = new VBox(5);
//        leaderboard.forEach(entry -> {
//            Label playerLabel = new Label(entry);
//            playerLabel.setFont(Font.font("Arial", 14));
//            leaderboardBox.getChildren().add(playerLabel);
//        });
//
//        Button closeButton = new Button("Close");
//        closeButton.setOnAction(e -> popupStage.close());
//
//        popupLayout.getChildren().addAll(header, leaderboardBox, closeButton);
//
//        Scene popupScene = new Scene(popupLayout, 400, 400);
//        popupStage.setScene(popupScene);
//        popupStage.show();
//    }

    /**
     * Step 2: Displays the topic selection menu.
     *
     * @param primaryStage the primary stage for the JavaFX application
     */

    private void showTopicSelection(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-color: lightcoral;");

        Label topicLabel = new Label("Choose a topic:");
        topicLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
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

        // Add a fade-in transition for the layout
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), layout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    /**
     * Step 3: Starts the Hangman game.
     *
     * @param primaryStage the primary stage for the JavaFX application
     * @param selectedTopic the topic chosen by the user
     */

    private void startGame(Stage primaryStage, String selectedTopic) {
        this.topic = selectedTopic;
        this.selectedWord = getRandomWord(topic);
        this.startTime = System.currentTimeMillis(); // Start the timer

        BorderPane layout = new BorderPane();
        drawingPane = new Pane();
        drawingPane.setPrefSize(400, 300);

        wordLabel = new Label(createWordDisplay());
        wordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        promptLabel = new Label("Guess a letter!");
        promptLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        promptLabel.setTextFill(Color.DARKBLUE);

        VBox topLayout = new VBox(10, promptLabel, wordLabel);
        topLayout.setPadding(new Insets(10));
        layout.setTop(topLayout);
        layout.setCenter(drawingPane);

        drawGallows(); // Add initial gallows to the new drawingPane

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
        inputBox.setAlignment(Pos.CENTER); // Center input layout
        layout.setBottom(inputBox);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the user's guess and updates the game state.
     *
     * @param guess the letter guessed by the user
     */

    private void handleGuess(String guess) {
        if (guess.length() == 1 && !guessedLetters.contains(guess.charAt(0))) {
            guessedLetters.add(guess.charAt(0));

            if (selectedWord.contains(guess)) {
                updateWordLabel();
                promptLabel.setText("Correct! Keep guessing.");
                if (isWordGuessed()) {
                    long timeSpent = (System.currentTimeMillis() - startTime) / 1000; // Time in seconds
                    saveGameResult(true, timeSpent);
                    showGameOver("You win!");
                }
            } else {
                drawNextBodyPartWithFade();
                promptLabel.setText("Wrong guess! Try again.");
                if (wrongGuesses == 6) {
                    long timeSpent = (System.currentTimeMillis() - startTime) / 1000; // Time in seconds
                    saveGameResult(false, timeSpent);
                    showGameOver("You lose! The word was: " + selectedWord);
                }
            }
        } else {
            promptLabel.setText("Invalid input. Enter a single letter.");
        }
    }

    /**
     * Applies a fade transition to the prompt label.
     */
    private void applyPromptFade() {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), promptLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.5);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.play();
    }


    // Save Game Result to the Database
    private void saveGameResult(boolean won, long timeSpent) {
        boolean resultSaved = dbConnector.saveGameResult(userName, topic, selectedWord, won, wrongGuesses, timeSpent);
        if (!resultSaved) {
            promptLabel.setText("Error saving game result.");
        }
    }

    // Display Game Over Message with Results
    private void showGameOver(String message) {
        // Fetch leaderboard as a table
        List<String[]> leaderboard = dbConnector.getLeaderboardAsTable();

        // Create a TableView for displaying the leaderboard
        TableView<String[]> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define columns
        TableColumn<String[], String> playerNameCol = new TableColumn<>("Player Name");
        playerNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        playerNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> topicCol = new TableColumn<>("Topic");
        topicCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        topicCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> attemptsCol = new TableColumn<>("Attempts");
        attemptsCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        attemptsCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<String[], String> timeCol = new TableColumn<>("Time (s)");
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        timeCol.setStyle("-fx-alignment: CENTER;");

        // Add columns to the table
        tableView.getColumns().addAll(playerNameCol, topicCol, attemptsCol, timeCol);

        // Add data to the table
        leaderboard.forEach(entry -> tableView.getItems().add(entry));

        // Create a layout for the popup
        VBox popupLayout = new VBox(10);
        popupLayout.setAlignment(Pos.CENTER); // Center everything in the popup
        popupLayout.setPadding(new Insets(20));
        popupLayout.setStyle("-fx-background-color: lightgoldenrodyellow;");

        Label header = new Label(message);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        header.setTextFill(Color.DARKRED);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
            ((Stage) restartButton.getScene().getWindow()).close();
            restartGame();
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        HBox buttonBox = new HBox(10, restartButton, exitButton);
        buttonBox.setPadding(new Insets(10));

        popupLayout.getChildren().addAll(header, tableView, buttonBox);

        // Show the popup
        Scene popupScene = new Scene(popupLayout, 500, 400);
        Stage popupStage = new Stage();
        popupStage.initOwner(drawingPane.getScene().getWindow());
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Restart the Game
    private void restartGame() {
        guessedLetters.clear();
        wrongGuesses = 0;
        selectedWord = null;
        topic = null;
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

    /**
     * Fetches a random word based on the selected topic.
     *
     * @param topic the selected topic
     * @return a random word
     */
    private String getRandomWord(String topic) {
        List<String> words;
        if ("programming".equals(topic)) {
            words = List.of("array", "loops", "class");
        } else if ("cars".equals(topic)) {
            words = List.of("tesla", "jaguar", "volvo");
        } else if ("physics".equals(topic)) {
            words = List.of("force", "light", "atoms");
        } else {
            words = List.of("error");
        }
        return words.get(new Random().nextInt(words.size()));
    }

    // Draw the Gallows
    private void drawGallows() {
        Line base = new Line(50, 250, 150, 250);
        Line post = new Line(100, 50, 100, 250);
        Line beam = new Line(100, 50, 200, 50);
        Line rope = new Line(200, 50, 200, 80);
        drawingPane.getChildren().addAll(base, post, beam, rope);
    }

    // Draw the Hanging Man Progressively
//    private void drawNextBodyPart() {
//        if (wrongGuesses == 0) {
//            drawingPane.getChildren().add(new Circle(200, 100, 20));
//        } else if (wrongGuesses == 1) {
//            drawingPane.getChildren().add(new Line(200, 120, 200, 180));
//        } else if (wrongGuesses == 2) {
//            drawingPane.getChildren().add(new Line(200, 140, 170, 120));
//        } else if (wrongGuesses == 3) {
//            drawingPane.getChildren().add(new Line(200, 140, 230, 120));
//        } else if (wrongGuesses == 4) {
//            drawingPane.getChildren().add(new Line(200, 180, 180, 220));
//        } else if (wrongGuesses == 5) {
//            drawingPane.getChildren().add(new Line(200, 180, 220, 220));
//        }
//        wrongGuesses++;
//    }
    /**
     * Draws the next body part of the hangman with a FadeTransition.
     */
    private void drawNextBodyPartWithFade() {
        Line part = null;
        if (wrongGuesses == 0) {
            Circle head = new Circle(200, 100, 20);
            drawingPane.getChildren().add(head);
            applyFadeToNode(head);
        } else if (wrongGuesses == 1) {
            part = new Line(200, 120, 200, 180); // Body
        } else if (wrongGuesses == 2) {
            part = new Line(200, 140, 170, 120); // Left arm
        } else if (wrongGuesses == 3) {
            part = new Line(200, 140, 230, 120); // Right arm
        } else if (wrongGuesses == 4) {
            part = new Line(200, 180, 180, 220); // Left leg
        } else if (wrongGuesses == 5) {
            part = new Line(200, 180, 220, 220); // Right leg
        }
        if (part != null) {
            drawingPane.getChildren().add(part);
            applyFadeToNode(part);
        }
        wrongGuesses++;
    }

    /**
     * Applies a fade transition to a JavaFX Node.
     *
     * @param node the Node to apply the fade to
     */
    private void applyFadeToNode(javafx.scene.Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
