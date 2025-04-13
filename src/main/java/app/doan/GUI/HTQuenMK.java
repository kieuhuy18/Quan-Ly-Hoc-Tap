package app.doan.GUI;

import app.doan.BLL.BLL_TaiKhoan;
import app.doan.Main;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Properties;

public class HTQuenMK {
    Main Main = new Main();

    @FXML
    private TextField TFmail;

    @FXML
    private Button BTgui;

    @FXML
    private Label LBquaylai;

    BLL_TaiKhoan blltk = new BLL_TaiKhoan();

    @FXML
    private void guimk() throws IOException {
        String s = "Đây là mật khẩu tài khoản của bạn là: " + blltk.tim(TFmail.getText()).getMatKhau();
        sendEmail(TFmail.getText(), "Quên mật khẩu", s);
        showAlert("Thông báo", "Vui lòng kiểm tra email của bạn");
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

    public void sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "kieuhuy180903@gmail.com";
        final String password = "uyjheutfytnhkveo";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Pomodoro App"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Gửi email thành công đến " + toEmail);
        } catch (Exception e) {
            System.out.println("Gửi email thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
