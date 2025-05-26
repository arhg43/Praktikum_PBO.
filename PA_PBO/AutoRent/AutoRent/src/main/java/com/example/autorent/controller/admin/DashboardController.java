package com.example.autorent.controller.admin;

import com.example.autorent.model.Mobil;
import com.example.autorent.repository.MobilRepository;
import com.example.autorent.repository.SewaRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private TableView<Mobil> rentalTable;
    @FXML private TableColumn<Mobil, String> platNomorColumn;
    @FXML private TableColumn<Mobil, String> mobilColumn;
    @FXML private TableColumn<Mobil, Number> customerColumn;
    @FXML private TableColumn<Mobil, String> tanggalSewaColumn;
    @FXML private TableColumn<Mobil, Void> hargaColumn;

    @FXML private TextField searchField;
    @FXML private Button btnTambah;

    private final MobilRepository mobilRepository = MobilRepository.getInstance();
    private final SewaRepository sewaRepository = SewaRepository.getInstance();
    private ObservableList<Mobil> dataMobil;

    @FXML
    public void initialize() {
        initializeColumns();
        setupSearchField();
        loadMobilData("");
    }

    private void initializeColumns() {
        platNomorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdMobil()));

        mobilColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMerkMobil()));

        customerColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getHargaSewa()));

        tanggalSewaColumn.setCellValueFactory(cellData -> {
            String status = cellData.getValue().isTersedia() ? "Tersedia" : "Tidak Tersedia";
            return new SimpleStringProperty(status);
        });

        setupActionColumn();
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> loadMobilData(newValue));
    }

    private void setupActionColumn() {
        hargaColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = createEditButton();
            private final Button deleteBtn = createDeleteButton();
            private final HBox actionBox = new HBox(5, editBtn, deleteBtn);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }

            private Button createEditButton() {
                Button btn = new Button("Edit");
                btn.setOnAction(event -> handleEditAction(getTableView().getItems().get(getIndex())));
                return btn;
            }

            private Button createDeleteButton() {
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                btn.setOnAction(event -> handleDeleteAction(getTableView().getItems().get(getIndex())));
                return btn;
            }
        });
    }

    private void loadMobilData(String keyword) {
        List<Mobil> allMobil = mobilRepository.getSemuaMobil();
        if (keyword != null && !keyword.isEmpty()) {
            allMobil = allMobil.stream()
                    .filter(mobil -> mobil.getMerkMobil().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }
        dataMobil = FXCollections.observableArrayList(allMobil);
        rentalTable.setItems(dataMobil);
    }

    private void handleEditAction(Mobil mobil) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/admin/EditData.fxml"));
            Parent root = loader.load();

            EditDataController controller = loader.getController();
            controller.setMobil(mobil);

            BoxBlur blur = new BoxBlur(5, 5, 3);
            rentalTable.getScene().getRoot().setEffect(blur);

            Stage stage = new Stage();
            stage.setTitle("Edit Data Mobil");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> {
                rentalTable.getScene().getRoot().setEffect(null);
                loadMobilData(searchField.getText());
            });

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAction(Mobil mobil) {
        if (!mobil.isTersedia()) {
            showAlert(Alert.AlertType.WARNING, "Tidak Bisa Dihapus", "Mobil ini tidak tersedia, jadi tidak bisa dihapus.");
            return;
        }

        // Cek apakah mobil sedang disewa (status aktif)
        boolean sedangDisewa = sewaRepository.getSemuaSewa().stream()
                .anyMatch(sewa ->
                        sewa.getIdMobil().equals(mobil.getIdMobil()) &&
                                !sewa.getStatus().equalsIgnoreCase("Dibatalkan") &&
                                !sewa.getStatus().equalsIgnoreCase("Selesai")
                );

        if (sedangDisewa) {
            showAlert(Alert.AlertType.WARNING, "Tidak Bisa Dihapus", "Mobil ini sedang atau akan disewa, jadi tidak bisa dihapus.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus Data Mobil");
        alert.setContentText("Yakin ingin menghapus mobil dengan plat: " + mobil.getIdMobil() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mobilRepository.hapusMobil(mobil.getIdMobil());
                deleteImageFromFolder(mobil.getImagePath());
                loadMobilData(searchField.getText());
            }
        });
    }

    private void deleteImageFromFolder(String imagePath) {
        File imageFile = new File(imagePath);
        if (imageFile.exists() && imageFile.isFile()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                System.out.println("Gagal menghapus gambar: " + imagePath);
            } else {
                System.out.println("Gambar berhasil dihapus: " + imagePath);
            }
        }
    }

    @FXML
    private void handleTambahData() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autorent/view/admin/TambahData.fxml"));
            Parent root = loader.load();

            BoxBlur blur = new BoxBlur(5, 5, 3);
            rentalTable.getScene().getRoot().setEffect(blur);

            Stage stage = new Stage();
            stage.setTitle("Tambah Data Mobil");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> {
                rentalTable.getScene().getRoot().setEffect(null);
                loadMobilData(searchField.getText());
            });

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
