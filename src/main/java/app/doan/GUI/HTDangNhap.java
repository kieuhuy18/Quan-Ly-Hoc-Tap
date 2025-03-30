package app.doan.GUI;

import app.doan.Main;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HTDangNhap implements Initializable {

    Main Main = new Main();

    @FXML
    private ImageView myImageView;

    @FXML
    private TextField TFmail;

    @FXML
    private TextField TFmk;

    @FXML
    private Button BTdn;

    @FXML
    private Button BTdk;

    @FXML
    private Label LBqmk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void DangKy() throws IOException {
        Main.switchScene("HTDangKy.fxml", "Dang Ky");
    }

    @FXML
    private void QuenMK() throws IOException {
        Main.switchScene("HTQuenMK.fxml", "Quen Mat Khau");
    }

    @FXML
    private void DangNhap() {
        String username = TFmail.getText();
        String password = TFmk.getText();

        // Kiểm tra dữ liệu nhập
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra thông tin đăng nhập
        if (username.equals("admin") && password.equals("1234")) {
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

}
