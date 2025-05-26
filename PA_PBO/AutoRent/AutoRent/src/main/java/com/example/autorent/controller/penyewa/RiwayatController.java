package com.example.autorent.controller.penyewa;

import com.example.autorent.model.Mobil;
import com.example.autorent.model.Sewa;
import com.example.autorent.repository.MobilRepository;
import com.example.autorent.repository.SewaRepository;
import com.example.autorent.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.util.List;

public class RiwayatController {

    @FXML
    private GridPane carGrid;

    private final MobilRepository mobilRepository = MobilRepository.getInstance();
    private final SewaRepository sewaRepository = SewaRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    @FXML
    public void initialize() {
        loadSewaCards();
    }

    private void loadSewaCards() {
        int currentUserId = userRepository.getCurrentPenyewaId();  // Dapatkan ID user yang login
        List<Sewa> sewaList = sewaRepository.getSemuaSewa();

        carGrid.getChildren().clear();
        int row = 0;

        for (Sewa sewa : sewaList) {
            if (sewa.getIdPenyewa() == currentUserId) { // Filter berdasarkan idPenyewa
                HBox card = createSewaCard(sewa);
                carGrid.add(card, 0, row++);
            }
        }
    }


    private HBox createSewaCard(Sewa sewa) {
        HBox box = new HBox(20);
        box.setPrefSize(600, 180);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: #f0f7ff; -fx-background-radius: 10;");

        // Ambil data mobil terkait
        Mobil mobil = mobilRepository.cariMobilById(sewa.getIdMobil());

        Image image;
        if (mobil != null && mobil.getImagePath() != null && !mobil.getImagePath().isEmpty()) {
            File file = new File(mobil.getImagePath());
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                image = new Image("file:images/placeholder.png");
            }
        } else {
            image = new Image("file:images/placeholder.png");
        }

        ImageView carImage = new ImageView(image);
        carImage.setFitWidth(150);
        carImage.setPreserveRatio(true);

        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Label namaMobilLabel = new Label(mobil != null ? mobil.getMerkMobil() : "Mobil Tidak Diketahui");
        namaMobilLabel.setFont(Font.font("Arial", 18));
        namaMobilLabel.setStyle("-fx-font-weight: bold;");

        Label tanggalSewaLabel = new Label("Tanggal Sewa: " + sewa.getTanggalSewa());
        tanggalSewaLabel.setFont(Font.font("Arial", 13));

        Label tanggalKembaliLabel = new Label("Tanggal Kembali: " + sewa.getTanggalPengembalian());
        tanggalKembaliLabel.setFont(Font.font("Arial", 13));

        Label hargaLabel = new Label("Total Harga: " + mobilRepository.calculateRentalPrice(sewa));
        hargaLabel.setFont(Font.font("Arial", 13));

        Label statusLabel = new Label("Status: " + sewa.getStatus());
        statusLabel.setFont(Font.font("Arial", 13));
        statusLabel.setStyle(getStatusStyle(sewa.getStatus()));

        infoBox.getChildren().addAll(
                namaMobilLabel,
                tanggalSewaLabel,
                tanggalKembaliLabel,
                hargaLabel,
                statusLabel
        );

        box.getChildren().addAll(carImage, infoBox);

        return box;
    }

    private String getStatusStyle(String status) {
        String baseStyle = "-fx-font-weight: bold; -fx-padding: 4 8 4 8; -fx-background-radius: 5; ";

        return switch (status) {
            case "Dibatalkan" -> baseStyle + "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11;"; 
            case "Disewakan" -> baseStyle + "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254;"; 
            case "Menunggu Pembayaran" -> baseStyle + "-fx-background-color: #f1c40f; -fx-text-fill: black;"; 
            case "Selesai" -> baseStyle + "-fx-background-color: #3498db; -fx-text-fill: white;"; 
            default -> baseStyle + "-fx-background-color: #f1c40f; -fx-text-fill: black;"; // default kuning
        };
    }
}
