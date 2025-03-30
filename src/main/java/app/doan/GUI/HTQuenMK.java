package app.doan.GUI;

import app.doan.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HTQuenMK {
    Main Main = new Main();

    @FXML
    private TextField TFmail;

    @FXML
    private Button BTgui;

    @FXML
    private Label LBquaylai;

    @FXML
    private void guimk() throws IOException {
        showAlert("hello", "gui roi cu");
        Main.switchScene("HTDangNhap.fxml", "Dang nhap");
    }

    @FXML
    private void quaylai() throws IOException {
        Main.switchScene("HTDangNhap.fxml", "Dang nhap");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
