package app.doan.GUI;

import app.doan.BLL.BLL_TaiKhoan;
import app.doan.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.doan.Main.switchScene;

public class HTDangKy {

    @FXML
    private TextField TFmail;

    @FXML
    private TextField TFmk1;

    @FXML
    private TextField TFmk2;

    @FXML
    private TextField TFtennd;

    @FXML
    private Button BTdangky;

    @FXML
    private Label LBdangnhap;

    BLL_TaiKhoan blltk = new BLL_TaiKhoan();
    private static final String EMAIL_REGEX =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    private void DangKy() throws IOException {
        String username = TFmail.getText();
        String password1 = TFmk1.getText();
        String password2 = TFmk2.getText();
        String tennd = TFtennd.getText();

        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || tennd.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if(!isValidEmail(username)){
            showAlert("Lỗi", "Vui lòng nhập email hợp lệ!");
        }
        if(!password1.equals(password2)){
            showAlert("Lỗi", "Mật khẩu nhập lại không trùng khớp!");
            return;
        }

        blltk.DangKy(username, password1, tennd);
        showAlert("Hoàn tất", "Đăng ký thành công!");
        switchScene("HTTrangChu.fxml", "Trang chủ");
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
        switchScene("HTDangNhap.fxml", "Dang Ky");
    }
}
