package com.example.autorent.controller.components;

import com.example.autorent.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarPenyewaController {

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView rentIcon;

    @FXML
    private ImageView logoutIcon;

    @FXML
    private Label logoutLabel;

    @FXML
    private StackPane berandaPane;

    @FXML
    private StackPane riwayatTransaksi;

    @FXML
    private void onBerandaClicked() {
        System.out.println("Home clicked!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/penyewa/beranda.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) berandaPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Beranda Penyewa");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRentClicked() {
        System.out.println("Riwayat Transaksi clicked!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/penyewa/riwayat_penyewa.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) riwayatTransaksi.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Riwayat Sewa");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogoutClicked() {
        System.out.println("Logout clicked!");

        UserRepository.getInstance().logout();

        try {
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
