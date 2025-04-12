package app.doan.GUI;

import app.doan.BLL.BLL_TaiKhoan;
import app.doan.DTO.DTO_TaiKhoan;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Optional;

import static app.doan.GUI.HTDangNhap.MaND;

public class DoiMK {
    @FXML
    private TextField TXTmk;

    @FXML
    private TextField TXTmk1;

    @FXML
    private TextField TXTmk2;

    @FXML
    private Button BTxacnhan;

    @FXML
    private ImageView IMclose;

    private BLL_TaiKhoan blltk = new BLL_TaiKhoan();
    public boolean xn = false;

    @FXML
    public void initialize(){
        DTO_TaiKhoan tk = blltk.tim(MaND);

        BTxacnhan.setOnAction(actionEvent -> {
            if(TXTmk.getText().equals(tk.getMatKhau())){
                if(TXTmk2.getText().equals(TXTmk1.getText())){
                    tk.setMatKhau(TXTmk2.getText());
                    blltk.sua(tk);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Đổi mật khẩu thành công!");
                    alert.showAndWait();
                    xn = true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cảnh báo");
                    alert.setHeaderText("Mật khẩu nhập lại không trùng khớp với mật khẩu mới!");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("mật khẩu hiện tại không chính xác!");
                alert.showAndWait();
            }
        });

        IMclose.setOnMouseClicked(event -> {
            Stage stage = (Stage) IMclose.getScene().getWindow();
            if(xn){
                stage.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Lưu thay đổi?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    blltk.sua(tk);
                    stage.close();
                }else {
                    stage.close();
                }
            }
        });
    }
}
