package com.stockportfoliomanagementsystem;

import java.sql.*;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySqlCon {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "734590955";
    
    public static Connection MysqlMethod() {
        Connection conn = null;
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            
            // Set connection properties
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("ssl", "false");
            
            // Set a connection timeout of 5 seconds
            DriverManager.setLoginTimeout(5);
            
            // Attempt to establish the connection
            conn = DriverManager.getConnection(DB_URL, props);
            
            // Test the connection
            if (conn != null && !conn.isClosed()) {
                System.out.println("Successfully connected to the PostgreSQL database!");
            }
            
        } catch (ClassNotFoundException e) {
            showErrorDialog("Database Driver Error", 
                "PostgreSQL JDBC Driver not found.\n" +
                "Please ensure you have the PostgreSQL JDBC driver in your classpath.",
                e.getMessage());
            
        } catch (SQLException e) {
            String errorMessage = "Failed to connect to the database.\n\n" +
                "Error details: " + e.getMessage() + "\n" +
                "SQL State: " + e.getSQLState() + "\n" +
                "Error Code: " + e.getErrorCode() + "\n\n" +
                "Please check the following:\n" +
                "1. Is PostgreSQL running on port 5432?\n" +
                "2. Is the database 'postgres' created?\n" +
                "3. Are the username and password correct?\n" +
                "4. Is the database configured to accept connections? (check pg_hba.conf)";
                
            showErrorDialog("Database Connection Error", 
                "Cannot connect to the database",
                errorMessage);
        }
        
        return conn;
    }
    
    private static void showErrorDialog(String title, String header, String content) {
        System.err.println(title + ": " + header + "\n" + content);
        
        // Show error dialog if JavaFX is initialized
        try {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            // If JavaFX isn't initialized yet, just print to stderr
            System.err.println("Could not show error dialog: " + e.getMessage());
        }
    }
    
    // Helper method to safely close resources
    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { /* ignored */ }
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
}
