package com.example.autorent.controller.admin;

import com.example.autorent.model.Mobil;
import com.example.autorent.repository.MobilRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EditDataController {

    @FXML private TextField merkMobilField;
    @FXML private TextField platNomorField;
    @FXML private TextField hargaField;
    @FXML private ImageView mobilImageView;
    @FXML private Button btnBrowse;
    @FXML private Button EditButton;

    private File selectedImageFile;
    private Mobil currentMobil;

    private final MobilRepository mobilRepository = MobilRepository.getInstance(); // Instance repository

    /**
     * Mengisi form dengan data dari mobil yang akan diedit
     */
    public void setMobil(Mobil mobil) {
        this.currentMobil = mobil;
        populateForm(mobil);
    }

    /**
     * Handle aksi tombol "Browse"
     */
    @FXML
    private void handleBrowseImage(ActionEvent event) {
        selectedImageFile = chooseImageFile();
        if (selectedImageFile != null) {
            mobilImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    /**
     * Handle aksi tombol "Edit"
     */
    @FXML
    private void handleEditData(ActionEvent event) {
        if (!isInputValid()) return;

        try {
            updateMobilFromForm();
            mobilRepository.updateMobil(currentMobil); // Update mobil melalui repository
            showAlert(Alert.AlertType.INFORMATION, "Data berhasil diperbarui.");
            navigateToScene(event, "/com/example/autorent/view/admin/dashboard.fxml");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Harga harus berupa angka.");
        }
    }

    // ============ Modular Functions ============

    private void populateForm(Mobil mobil) {
        merkMobilField.setText(mobil.getMerkMobil());
        platNomorField.setText(mobil.getIdMobil());
        hargaField.setText(String.valueOf(mobil.getHargaSewa()));

        if (mobil.getImagePath() != null) {
            File file = new File(mobil.getImagePath());
            if (file.exists()) {
                mobilImageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }

    private File chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Gambar Mobil");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenDialog(null);
    }

    private boolean isInputValid() {
        if (merkMobilField.getText().isEmpty() ||
                platNomorField.getText().isEmpty() ||
                hargaField.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Form tidak boleh kosong.");
            return false;
        }

        try {
            double harga = Double.parseDouble(hargaField.getText());
            if (harga <= 0) {
                showAlert(Alert.AlertType.WARNING, "Harga tidak boleh kurang dari 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Harga harus berupa angka.");
            return false;
        }

        return true;
    }

    private void updateMobilFromForm() throws NumberFormatException {
        String merk = merkMobilField.getText();
        String plat = platNomorField.getText();
        double harga = Double.parseDouble(hargaField.getText());
        String imagePath = selectedImageFile != null ?
                selectedImageFile.getAbsolutePath() : currentMobil.getImagePath();

        currentMobil.setMerkMobil(merk);
        currentMobil.setIdMobil(plat);
        currentMobil.setHargaSewa(harga);
        currentMobil.setImagePath(imagePath);
    }

    private void navigateToScene(ActionEvent event, String fxmlPath) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Gagal pindah halaman: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
