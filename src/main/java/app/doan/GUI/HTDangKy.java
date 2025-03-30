package app.doan.GUI;

import app.doan.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HTDangKy {
    Main Main = new Main();

    @FXML
    private TextField TFmail;

    @FXML
    private TextField TFmk1;

    @FXML
    private TextField TFmk2;

    @FXML
    private Button BTdangky;

    @FXML
    private Label LBdangnhap;

    @FXML
    private void DangKy() {
        String username = TFmail.getText();
        String password1 = TFmk1.getText();
        String password2 = TFmk2.getText();

        // Kiểm tra dữ liệu nhập
        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra thông tin đăng nhập
        if (username.equals("admin") && password1.equals("1234")) {
            showAlert("Đăng nhập thành công", "Chào mừng " + username + "!");
        } else {
            showAlert("Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void DangNhap() throws IOException {
        Main.switchScene("HTDangNhap.fxml", "Dang Ky");
    }
}
