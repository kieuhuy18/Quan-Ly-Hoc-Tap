package app.doan.GUI;

import app.doan.BLL.BLL_TaiKhoan;
import app.doan.DTO.DTO_TaiKhoan;
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

import static app.doan.Main.switchScene;

public class HTDangNhap implements Initializable {

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

    public  static String MaND;

    BLL_TaiKhoan BLLtk = new BLL_TaiKhoan();
    public static DTO_TaiKhoan tk = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void DangKy() throws IOException {
        switchScene("HTDangKy.fxml", "Dang Ky");
    }

    @FXML
    private void QuenMK() throws IOException {
        switchScene("HTQuenMK.fxml", "Quen Mat Khau");
    }

    @FXML
    private void DangNhap() throws IOException {
        String username = TFmail.getText();
        String password = TFmk.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        tk = BLLtk.DangNhap(username, password);
        if (tk != null) {
            showAlert("Đăng nhập thành công", "Chào mừng " + tk.getTenND() + "!");
            MaND = tk.getEmail();
            switchScene("HTTrangChu.fxml", "Trang chủ");
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
