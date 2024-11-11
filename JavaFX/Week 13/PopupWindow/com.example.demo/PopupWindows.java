package com.example.demo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class PopUpWindow extends Stage {
    public PopUpWindow() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Popup Window");
        Label label = new Label("This is a popup window.");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        setScene(new Scene(layout, 250, 150));
    }
}
