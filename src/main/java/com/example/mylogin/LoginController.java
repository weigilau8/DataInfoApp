package com.example.mylogin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML protected TextField email;
    @FXML protected PasswordField password;
    @FXML protected Label message;
    @FXML private BorderPane loginPage;

    @FXML
    public void userLogin() throws IOException {

        String dbUrl = "jdbc:mysql://localhost:3306/Users";
        String dbUser = "root";
        String dbPassword = "rootLang";


        if (email.getText().isEmpty() && password.getText().isEmpty()) {
            message.setText("Email or Password Required");
        } else if (email.getText().isEmpty()) {
            message.setText("Email is required");
        } else if (password.getText().isEmpty()) {
            message.setText("Password is required");
        }
        else {

            try {

                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Connect to database using try-with-resources
                try (Connection conDB = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                    // Use PreparedStatement to prevent SQL injection
                    String query = "SELECT * FROM user_info WHERE email = ? AND password = ?";

                    try (PreparedStatement beforeQuery = conDB.prepareStatement(query)) {
                        beforeQuery.setString(1, email.getText().trim());
                        beforeQuery.setString(2, password.getText().trim());

                        // Execute query
                        try (ResultSet data = beforeQuery.executeQuery()) {
                            if (data.next()) {
                                message.setText("Login Successful!");

                                // Redirect to dashboard page
                                int userId = data.getInt("id");
                                SceneSwitch sceneSwitch = new SceneSwitch(loginPage, "dashboard-view.fxml");
                                DashboardController dashboardController = sceneSwitch.getController();
                                dashboardController.setUserId(userId);

                            } else {
                                // Login failed
                                message.setText("Wrong email or password");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                // Database error Connection
                message.setText("Database error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                // Database not found
                message.setText("Database driver not found");
            }
        }


    }
}