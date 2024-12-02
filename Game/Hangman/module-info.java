module Hangman3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports hangman;
    opens hangman to javafx.graphics, javafx.fxml;
}
