package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML or create main layout
        Button popupButton = new Button("Open Popup");

        // Set the action to open the popup when clicked
        popupButton.setOnAction(e -> {
            PopUpWindow popup = new PopUpWindow();
            popup.show(); // Show the popup window
        });

        VBox layout = new VBox(10, popupButton); // Add the button to the layout
        Scene scene = new Scene(layout, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        Properties props = new Properties();
        try {
            // Load the app.properties file
            FileInputStream fis = new FileInputStream("src/main/resources/com/example/demo/app.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve the properties
        String host = props.getProperty("host");
        String port = props.getProperty("port");
        String database = props.getProperty("database");
        String dbUser = props.getProperty("dbUser");
        String dbPassword = props.getProperty("dbPassword");
        String sslMode = props.getProperty("sslMode");
        String sslRootCert = props.getProperty("sslRootCert");

        // JDBC connection URL
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database + "?ssl=" + sslMode;

        if (sslMode.equals("require")) {
            url += "&sslrootcert=" + sslRootCert;
        }

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Connected to the database successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


