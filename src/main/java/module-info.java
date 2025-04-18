module app.doan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    requires java.sql;
    requires jakarta.mail;

    // Các thư viện bên ngoài
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    // Mở các package cho JavaFX sử dụng FXML và reflection
    opens app.doan to javafx.fxml;
    exports app.doan;

    opens app.doan.GUI to javafx.fxml;
    exports app.doan.GUI;
}
