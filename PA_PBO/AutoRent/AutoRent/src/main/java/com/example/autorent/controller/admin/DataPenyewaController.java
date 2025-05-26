package com.example.autorent.controller.admin;

import com.example.autorent.model.Mobil;
import com.example.autorent.model.Sewa;
import com.example.autorent.model.User;
import com.example.autorent.repository.MobilRepository;
import com.example.autorent.repository.SewaRepository;
import com.example.autorent.repository.UserRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DataPenyewaController {

    @FXML
    private TableView<Sewa> rentalTable;

    @FXML
    private TableColumn<Sewa, String> platNomorColumn;

    @FXML
    private TableColumn<Sewa, String> namaPenyewaColumn;

    @FXML
    private TableColumn<Sewa, String> hargaMobilColumn;

    @FXML
    private TableColumn<Sewa, LocalDate> tanggalSewaColumn;

    @FXML
    private TableColumn<Sewa, LocalDate> tanggalPengembalianColumn;

    @FXML
    private TableColumn<Sewa, String> statusColumn;

    @FXML
    private TextField searchField;

    private final SewaRepository sewaRepository = SewaRepository.getInstance();
    private final MobilRepository mobilRepository = MobilRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    private List<Sewa> sewaList;

    @FXML
    private void initialize() {
        rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupTableColumns();
        setupEditableStatusColumn();
        setupSearchField();
        setupRowStyling();
        loadSewaData();
    }

    private void loadSewaData() {
        sewaList = sewaRepository.getSemuaSewa();
        rentalTable.getItems().setAll(sewaList);
    }

    private void setupTableColumns() {
        platNomorColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getIdMobil())
        );

        namaPenyewaColumn.setCellValueFactory(cellData -> {
            User user = userRepository.cariPenyewaById(cellData.getValue().getIdPenyewa());
            return new SimpleStringProperty(user != null ? user.getUsername() : "(Tidak Diketahui)");
        });

        hargaMobilColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getTotalHarga();
            String formatted = String.format("Rp%,.0f", harga);
            return new SimpleStringProperty(formatted);
        });

        tanggalSewaColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTanggalSewa())
        );

        tanggalPengembalianColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTanggalPengembalian())
        );

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus())
        );
    }

    private void setupEditableStatusColumn() {
        statusColumn.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(
                    "Disewakan", "Dibatalkan", "Menunggu Pembayaran", "Selesai"
            ));

            {
                comboBox.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                comboBox.setOnAction(event -> {
                    Sewa sewa = getTableView().getItems().get(getIndex());
                    String selectedStatus = comboBox.getSelectionModel().getSelectedItem();
                    if (sewa != null && selectedStatus != null) {
                        sewa.setStatus(selectedStatus);
                        sewaRepository.updateSewaStatus(sewa, selectedStatus);
                        commitEdit(selectedStatus);
                        getTableView().refresh();
                    }
                });
            }

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else if (isEditing()) {
                    comboBox.getSelectionModel().select(status);
                    comboBox.setDisable("Dibatalkan".equals(status) || "Selesai".equals(status));
                    setGraphic(comboBox);
                    setText(null);
                } else {
                    Label statusLabel = new Label(status);
                    statusLabel.setStyle(getButtonStyleForStatus(status));
                    statusLabel.setMinHeight(26);
                    statusLabel.setMinWidth(80);
                    statusLabel.setMaxWidth(Double.MAX_VALUE);

                    setGraphic(statusLabel);
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                if (!isEmpty() && !getItem().equals("Dibatalkan") && !getItem().equals("Selesai")) {
                    super.startEdit();
                    comboBox.getSelectionModel().select(getItem());
                    setGraphic(comboBox);
                    setText(null);
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                updateItem(getItem(), false);
            }
        });

        statusColumn.setEditable(true);
        rentalTable.setEditable(true);
    }

    private String getButtonStyleForStatus(String status) {
        String base = """
            -fx-font-size: 10px;
            -fx-padding: 4 12 4 12;
            -fx-background-radius: 999px;
            -fx-font-weight: bold;
            -fx-alignment: center;
            -fx-text-alignment: center;
            -fx-content-display: center;
        """;

        return switch (status) {
            case "Dibatalkan" -> base + "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11;";
            case "Disewakan" -> base + "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254;";
            case "Menunggu Pembayaran" -> base + "-fx-background-color: #FFF3CD; -fx-text-fill: #856404;";
            case "Selesai" -> base + "-fx-background-color: #D1ECF1; -fx-text-fill: #0C5460;";
            default -> base + "-fx-background-color: #E0E0E0; -fx-text-fill: black;";
        };
    }

    private void setupSearchField() {
        searchField.setPromptText("Cari nama penyewa...");
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            filterSewaList(searchField.getText());
        });
    }

    private void filterSewaList(String keyword) {
        String searchKeyword = keyword.toLowerCase();
        List<Sewa> filtered = sewaList.stream()
                .filter(sewa -> {
                    User penyewa = userRepository.cariPenyewaById(sewa.getIdPenyewa());
                    return penyewa != null && penyewa.getUsername().toLowerCase().contains(searchKeyword);
                })
                .collect(Collectors.toList());
        rentalTable.getItems().setAll(filtered);
    }

    private void setupRowStyling() {
        rentalTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Sewa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    setStyle(getRowStyle(getIndex()));
                }
            }

            private String getRowStyle(int index) {
                return index % 2 == 0
                        ? "-fx-background-color: #609AFA;"
                        : "-fx-background-color: #609AFA;";
            }
        });
    }
}
