module app.doan {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jakarta.mail;
    requires eu.hansolo.tilesfx;
    //requires eu.hansolo.tilesfx;

    opens app.doan to javafx.fxml;
    exports app.doan;
    exports app.doan.GUI;
    opens app.doan.GUI to javafx.fxml;
}