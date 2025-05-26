package com.example.autorent.controller.admin;

import com.example.autorent.model.Mobil;
import com.example.autorent.repository.MobilRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TambahDataController {

    @FXML private TextField merkMobilField;
    @FXML private TextField platNomorField;
    @FXML private TextField hargaField;
    @FXML private Button browseButton;
    @FXML private ImageView gambarView;
    @FXML private Button tambahButton;

    private File gambarFile;
    private final MobilRepository mobilRepository = MobilRepository.getInstance();

    @FXML
    private void initialize() {
        browseButton.setOnAction(event -> handleBrowseImage());
        tambahButton.setOnAction(event -> handleTambahData());
    }

    private void handleBrowseImage() {
        File selectedFile = openImageFileChooser();
        if (selectedFile != null) {
            gambarFile = selectedFile;
            gambarView.setImage(new Image(gambarFile.toURI().toString()));
        }
    }

    private void handleTambahData() {
        if (!isInputValid()) return;

        try {
            Mobil mobilBaru = createMobilFromInput();
            mobilRepository.tambahMobil(mobilBaru);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data mobil berhasil ditambahkan.");
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Format Salah", "Harga harus berupa angka.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Gagal Menyimpan Gambar", "Tidak bisa menyimpan file gambar.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Terjadi kesalahan: " + e.getMessage());
        }
    }

    private File openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Gambar Mobil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        return fileChooser.showOpenDialog(browseButton.getScene().getWindow());
    }

    private boolean isInputValid() {
        if (merkMobilField.getText().isEmpty() ||
                platNomorField.getText().isEmpty() ||
                hargaField.getText().isEmpty() ||
                gambarFile == null) {

            showAlert(Alert.AlertType.WARNING, "Data Tidak Lengkap", "Harap isi semua field dan pilih gambar.");
            return false;
        }

        try {
            double harga = Double.parseDouble(hargaField.getText());
            if (harga <= 0) {
                showAlert(Alert.AlertType.WARNING, "Harga Tidak Valid", "Harga tidak boleh kurang dari 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Format Harga Salah", "Harga harus berupa angka.");
            return false;
        }

        return true;
    }

    private Mobil createMobilFromInput() throws IOException {
        String merk = merkMobilField.getText();
        String plat = platNomorField.getText();
        int harga = Integer.parseInt(hargaField.getText());
        String imagePath = saveImageToLocalFolder(plat);
        return new Mobil(plat, merk, harga, true, imagePath);
    }

    private String saveImageToLocalFolder(String plat) throws IOException {
        String folderPath = "assets/images";
        File folderTujuan = new File(folderPath);

        if (!folderTujuan.exists() && !folderTujuan.mkdirs()) {
            throw new IOException("Gagal membuat folder untuk menyimpan gambar.");
        }

        String fileName = plat.replaceAll("\\s+", "_") + "_" + gambarFile.getName();
        File fileTujuan = new File(folderTujuan, fileName);
        Files.copy(gambarFile.toPath(), fileTujuan.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return fileTujuan.getPath();
    }

    private void closeWindow() {
        Stage stage = (Stage) tambahButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
