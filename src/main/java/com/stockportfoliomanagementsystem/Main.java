package com.stockportfoliomanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {
    private Connection conn;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            conn = MySqlCon.MysqlMethod();
            sqlUpdates();
        } catch (RuntimeException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            conn = null;
        }



        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        stage.setHeight(700);
        stage.setWidth(1200);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book Nook");

        stage.setResizable(false);

        // Load the image using ClassLoader
        String relativePath = "/com/stockportfoliomanagementsystem/Images/logoIcon.png";
        InputStream iconStream = Main.class.getResourceAsStream(relativePath);
        Image iconImage = new Image(iconStream);
        stage.getIcons().add(iconImage);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public void sqlUpdates() {
        if (conn == null) {
            return;
        }
        String sql = "Update stock SET Total = Selling_price*Qty";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            if ("42P01".equals(e.getSQLState())) {
                return;
            }
            if ("42703".equals(e.getSQLState())) {
                return;
            }
            System.err.println("Failed to run startup SQL updates: " + e.getMessage());
        }
    }

}