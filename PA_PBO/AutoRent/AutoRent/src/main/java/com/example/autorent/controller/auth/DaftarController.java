package com.example.autorent.controller.auth;

import com.example.autorent.dao.UserDAO;
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

public class DaftarController {

    private UserDAO userRepository;
    private static int nextPenyewaId = 3; // Dummy auto-increment

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField noHPField;

    @FXML
    private Button registerButton;

    @FXML
    private Label loginLabel;

    public DaftarController() {
        this.userRepository = UserRepository.getInstance();
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String noHP = noHPField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || noHP.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form Tidak Lengkap", "Silakan isi semua field.");
            return;
        }

        if (!noHP.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Nomor HP Tidak Valid", "Nomor HP hanya boleh berisi angka.");
            return;
        }

        boolean success = userRepository.registerPenyewa(nextPenyewaId++, username, password, noHP);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Registrasi Berhasil", "Akun berhasil dibuat untuk: " + username);
            clearFields();
            goToLoginScene(); // otomatis pindah ke login
        } else {
            showAlert(Alert.AlertType.ERROR, "Registrasi Gagal", "Username sudah digunakan.");
        }
    }

    @FXML
    public void goToLogin(MouseEvent event) {
        switchScene(event, "/com/example/autorent/view/auth/login.fxml");
    }

    private void goToLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/auth/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman login.");
            e.printStackTrace();
        }
    }

    private void switchScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        noHPField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
