package app.doan.GUI;

import app.doan.BLL.BLL_TaiKhoan;
import app.doan.DTO.DTO_TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

import static app.doan.GUI.HTDangNhap.MaND;
import static app.doan.Main.primaryStage;

public class ThayDoiThongTinND {
    @FXML
    private TextField TXTemail;

    @FXML
    private TextField TXTten;

    @FXML
    private Button BTxacnhan;

    @FXML
    private Button BTdoimk;

    @FXML
    private ImageView IMclose;

    private final BLL_TaiKhoan blltk = new BLL_TaiKhoan();
    private boolean xn = false;

    @FXML
    public void initialize(){
        DTO_TaiKhoan tk = blltk.tim(MaND);
        TXTemail.setText(MaND);
        TXTten.setText(tk.getTenND());

        BTxacnhan.setOnAction(actionEvent -> {
            if(TXTemail.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Vui lòng nhập Email!");
                alert.showAndWait();
            }else if(TXTten.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Vui lòng nhập tên người dùng!");
                alert.showAndWait();
            }else{
                tk.setEmail(TXTemail.getText());
                tk.setTenND(TXTten.getText());
                blltk.sua1(tk);
                xn = true;
            }
        });

        BTdoimk.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/doan/DoiMK.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.setResizable(false);

            if (primaryStage.getScene() != null) {
                GaussianBlur blurEffect = new GaussianBlur(20);
                primaryStage.getScene().getRoot().setEffect(blurEffect);
                popupStage.setOnHidden(event -> primaryStage.getScene().getRoot().setEffect(null));
            }
            popupStage.showAndWait();
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
                    stage.close();
                }else {
                    stage.close();
                }
            }
        });
    }


}
