package com.example.autorent.controller.penyewa;

import com.example.autorent.model.Mobil;
import com.example.autorent.model.Sewa;
import com.example.autorent.repository.MobilRepository;
import com.example.autorent.repository.SewaRepository;
import com.example.autorent.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BerandaController implements Initializable {

    @FXML
    private GridPane carGrid;

    private final int CARD_WIDTH = 150;
    private final int CARD_HEIGHT = 180;

    private final MobilRepository mobilRepository = MobilRepository.getInstance();
    private final SewaRepository sewaRepository = SewaRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();
    private int nextIdSewa = getInitialNextIdSewa();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMobilCards();
        sewaRepository.periksaDanUpdateKetersediaanMobil(); // opsional jika sudah ada implementasi
    }

    private int getInitialNextIdSewa() {
        return sewaRepository.getSemuaSewa().stream()
                .mapToInt(Sewa::getIdSewa)
                .max()
                .orElse(999) + 1;
    }

    private void loadMobilCards() {
        List<Mobil> mobilList = mobilRepository.getSemuaMobil();
        carGrid.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Mobil mobil : mobilList) {
            if (mobil.isTersedia()) {
                VBox card = createCarCard(mobil);
                carGrid.add(card, column++, row);

                if (column == 5) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    private VBox createCarCard(Mobil mobil) {
        VBox box = new VBox(10);
        box.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #f0f7ff; -fx-background-radius: 10; -fx-cursor: hand;");

        Image image;
        if (mobil.getImagePath() != null) {
            File file = new File(mobil.getImagePath());
            image = file.exists() ? new Image(file.toURI().toString()) : new Image("file:images/placeholder.png");
        } else {
            image = new Image("file:images/placeholder.png");
        }

        ImageView carImage = new ImageView(image);
        carImage.setFitWidth(100);
        carImage.setPreserveRatio(true);

        Label modelLabel = new Label(mobil.getMerkMobil());
        modelLabel.setFont(Font.font("Arial", 14));
        modelLabel.setStyle("-fx-font-weight: bold;");

        Label priceLabel = new Label("Rp" + mobil.getHargaSewa() + "/hari");
        priceLabel.setFont(Font.font("Arial", 12));
        priceLabel.setTextFill(Color.GRAY);

        box.getChildren().addAll(carImage, modelLabel, priceLabel);
        box.setOnMouseClicked(event -> showDatePickerDialog(mobil));

        return box;
    }

    private void showDatePickerDialog(Mobil mobil) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Pilih Tanggal Sewa");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Sewa: " + mobil.getMerkMobil());
        titleLabel.setFont(Font.font("Arial", 16));
        titleLabel.setStyle("-fx-font-weight: bold;");

        DatePicker tanggalSewaPicker = new DatePicker();
        tanggalSewaPicker.setPromptText("Tanggal Sewa");
        tanggalSewaPicker.setEditable(false);

        DatePicker tanggalKembaliPicker = new DatePicker();
        tanggalKembaliPicker.setPromptText("Tanggal Kembali");
        tanggalKembaliPicker.setEditable(false);

        // Ambil semua tanggal yang diblokir
        List<Sewa> semuaSewa = sewaRepository.getSemuaSewa();
        List<LocalDate> tanggalTerblokir = semuaSewa.stream()
                .filter(sewa -> sewa.getIdMobil().equals(mobil.getIdMobil()) && !sewa.getStatus().equalsIgnoreCase("Dibatalkan"))
                .flatMap(sewa -> {
                    LocalDate start = sewa.getTanggalSewa().minusDays(2);
                    LocalDate end = sewa.getTanggalPengembalian().plusDays(2);
                    return start.datesUntil(end.plusDays(1)); // inclusive
                })
                .distinct()
                .toList();

        // Setup disable tanggal diblokir dengan method modular
        setupDatePickerBlocking(tanggalSewaPicker, tanggalTerblokir);
        setupDatePickerBlocking(tanggalKembaliPicker, tanggalTerblokir);

        Button confirmButton = new Button("Konfirmasi");
        confirmButton.setOnAction(e -> {
            LocalDate tanggalSewa = tanggalSewaPicker.getValue();
            LocalDate tanggalKembali = tanggalKembaliPicker.getValue();

            if (!validateRentalDates(tanggalSewa, tanggalKembali)) return;

            if (!isMobilTersediaUntukTanggal(mobil, tanggalSewa, tanggalKembali)) {
                showSimpleAlert("Tidak Tersedia", "Mobil ini sudah dibooking oleh penyewa lain dalam rentang waktu tersebut, termasuk 2 hari sebelumnya dan sesudahnya.");
                return;
            }

            int idSewaBaru = nextIdSewa++;
            int idPenyewaDummy = userRepository.getCurrentPenyewaId();

            Sewa sewaBaru = new Sewa(
                    idSewaBaru,
                    idPenyewaDummy,
                    mobil.getIdMobil(),
                    tanggalSewa,
                    tanggalKembali,
                    "Menunggu Pembayaran",
                    0
            );

            mobilRepository.calculateRentalPrice(sewaBaru);
            System.out.println("Harga Total: " + sewaBaru.getTotalHarga());

            sewaRepository.tambahSewa(sewaBaru);
            loadMobilCards();
            dialogStage.close();
            showSimpleAlert("Berhasil", "Mobil berhasil disewa.");
        });

        dialogVBox.getChildren().addAll(titleLabel, tanggalSewaPicker, tanggalKembaliPicker, confirmButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 250);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    // Modular method untuk disable tanggal pada DatePicker
    private void setupDatePickerBlocking(DatePicker datePicker, List<LocalDate> tanggalTerblokir) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) return;

                if (tanggalTerblokir.contains(date)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                    setTooltip(new Tooltip("Sudah dibooking"));
                }
            }
        });
    }

    // Modular method validasi tanggal sewa dan kembali
    private boolean validateRentalDates(LocalDate tanggalSewa, LocalDate tanggalKembali) {
        if (tanggalSewa == null || tanggalKembali == null) {
            showSimpleAlert("Error", "Harap pilih kedua tanggal.");
            return false;
        }

        LocalDate today = LocalDate.now();
        if (tanggalSewa.isBefore(today)) {
            showSimpleAlert("Error", "Tanggal sewa tidak boleh di masa lalu.");
            return false;
        }

        if (tanggalKembali.isBefore(tanggalSewa)) {
            showSimpleAlert("Error", "Tanggal kembali harus setelah tanggal sewa.");
            return false;
        }

        return true;
    }

    // Modular method alert popup sederhana
    private void showSimpleAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isMobilTersediaUntukTanggal(Mobil mobil, LocalDate tanggalSewaBaru, LocalDate tanggalKembaliBaru) {
        List<Sewa> semuaSewa = sewaRepository.getSemuaSewa();

        for (Sewa sewa : semuaSewa) {
            if (!sewa.getIdMobil().equals(mobil.getIdMobil())) continue;
            if (sewa.getStatus().equalsIgnoreCase("Dibatalkan")) continue;

            // Rentang blokir: 2 hari sebelum sewa hingga 2 hari setelah pengembalian
            LocalDate mulaiBlokir = sewa.getTanggalSewa().minusDays(2);
            LocalDate akhirBlokir = sewa.getTanggalPengembalian().plusDays(2);

            boolean overlap = !tanggalKembaliBaru.isBefore(mulaiBlokir) && !tanggalSewaBaru.isAfter(akhirBlokir);
            if (overlap) return false;
        }

        return true;
    }
}
