package com.example.mylogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class DashboardController {

//    protected String userEmail;
    private int userId;

    @FXML protected Label userLabel;
    @FXML protected Label firstNameLabel;
    @FXML protected Label lastNameLabel;
    @FXML protected Label emailLabel;
    @FXML protected Button logoutButton;
    @FXML private BorderPane dashboardPage;

    public void setUserId(int id) {
        this.userId = id;
        loadUserData(id);
    }

    private void loadUserData(int id) {

        String dbUrl = "jdbc:mysql://localhost:3306/Users";
        String dbUser = "root";
        String dbPassword = "rootLang";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conDB = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

                String query = "SELECT first_name, last_name, email FROM user_info WHERE id = ?";

                try (PreparedStatement beforeQuery = conDB.prepareStatement(query)) {
                    // Use theEmail parameter instead of userEmail
                    beforeQuery.setInt(1, id);

                    try (ResultSet data = beforeQuery.executeQuery()) {
                        if (data.next()) {  // Changed while to if since email is unique
                            firstNameLabel.setText(data.getString("first_name"));
                            lastNameLabel.setText(data.getString("last_name"));
                            emailLabel.setText(data.getString("email"));
                            userLabel.setText(data.getString("first_name") + " " + data.getString("last_name"));

                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickLogout(ActionEvent event) throws IOException {
        userId = 0;
        new SceneSwitch(dashboardPage, "login-view.fxml");
    }


}
