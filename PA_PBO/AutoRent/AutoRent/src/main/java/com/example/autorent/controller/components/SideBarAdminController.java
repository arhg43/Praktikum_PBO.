package com.example.autorent.controller.components;

import com.example.autorent.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SideBarAdminController {

    @FXML
    private StackPane dashboardPane;

    @FXML
    private StackPane carPane;

    @FXML
    private FontIcon dashboardIcon;

    @FXML
    private FontIcon carIcon;

    @FXML
    private Label logoutLabel; // Label dari FXML

    @FXML
    private void onDashboardClicked() {
        System.out.println("Dashboard clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/admin/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) carPane.getScene().getWindow();  // Ambil stage dari komponen carPane
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Mobil");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Kamu bisa tambahkan alert error jika perlu
        }
    }

    @FXML
    private void onCarClicked() {
        System.out.println("Car clicked!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/admin/data_penyewa.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) carPane.getScene().getWindow();  // Ambil stage dari komponen carPane
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Penyewa");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Kamu bisa tambahkan alert error jika perlu
        }
    }

    @FXML
    private void onLogoutClicked() {
        System.out.println("Logout clicked!");

        // Hapus user yang sedang login
        UserRepository.getInstance().logout();

        try {
            // Kembali ke halaman login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/auth/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
