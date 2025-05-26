module com.example.autorent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens com.example.autorent to javafx.fxml;
    exports com.example.autorent;
    exports com.example.autorent.controller.auth;
    exports com.example.autorent.controller.components;
    exports com.example.autorent.controller.admin;
    exports com.example.autorent.controller.penyewa;
    opens com.example.autorent.controller.components to javafx.fxml;
    opens com.example.autorent.controller.auth    to javafx.fxml;
    opens com.example.autorent.controller.admin to javafx.fxml;
    opens com.example.autorent.controller.penyewa to javafx.fxml;
}