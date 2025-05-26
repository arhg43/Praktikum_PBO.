package com.example.autorent.controller.auth;

import com.example.autorent.dao.UserDAO;
import com.example.autorent.model.Admin;
import com.example.autorent.model.Penyewa;
import com.example.autorent.model.User;
import com.example.autorent.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private final UserDAO userRepository;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label registerLabel;

    public LoginController() {
        this.userRepository = UserRepository.getInstance();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form Tidak Lengkap", "Silakan isi username dan password.");
            return;
        }

        User user = userRepository.login(username, password);
        processLoginResult(user);
    }

    private void processLoginResult(User user) {
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau password salah.");
            return;
        }

        if (user instanceof Admin) {
            showLoginSuccess("Admin");
            loadScene("/com/example/autorent/view/admin/dashboard.fxml", true);
        } else if (user instanceof Penyewa) {
            showLoginSuccess("Penyewa");
            loadScene("/com/example/autorent/view/penyewa/Beranda.fxml", true);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Tipe pengguna tidak dikenali.");
        }
    }

    private void showLoginSuccess(String userType) {
        showAlert(Alert.AlertType.INFORMATION, "Login Berhasil", "Selamat datang, " + userType + "!");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadScene(String fxmlPath, boolean fullScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    public void goToRegister(MouseEvent mouseEvent) {
        switchScene(mouseEvent, "/com/example/autorent/view/auth/daftar.fxml");
    }

    private void switchScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
