package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConfig {

    public static Connection getConnection() {
        Properties props = new Properties();
        try {
            // Load properties file
            FileInputStream fis = new FileInputStream("src/main/resources/app.properties");
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

        // JDBC URL
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        // Create and return the connection
        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
