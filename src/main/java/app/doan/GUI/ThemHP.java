package app.doan.GUI;

import app.doan.BLL.BLL_HocPhan;
import app.doan.DTO.DTO_HocPhan;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;

import static app.doan.GUI.HTDangNhap.MaND;
import static app.doan.GUI.HTtodolist.mahienthi;

public class ThemHP {

    @FXML
    private TextField TXTtieude;

    @FXML
    private TextField TXTgv;

    @FXML
    private Label displayLabel;

    @FXML
    private Label displayLabel1;

    @FXML
    private ImageView IMclose;

    @FXML
    private ComboBox<String> CBBtrangthai;

    @FXML
    private TextArea TAmota;

    BLL_HocPhan bllhp = new BLL_HocPhan();

    @FXML
    public void initialize(){
        CBBtrangthai.getItems().addAll("Chưa hoàn thành", "Hoàn thành");
        CBBtrangthai.setValue("Chưa hoàn thành");

        CBBtrangthai.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-size: 14px;");
                }
            }
        });

        CBBtrangthai.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-size: 16px;");
                }
            }
        });

        displayLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing(TXTtieude, displayLabel);
            }
        });
        displayLabel1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing(TXTgv, displayLabel1);
            }
        });

        TXTtieude.setOnAction(event -> saveText(TXTtieude, displayLabel));
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTtieude, displayLabel);
        });
        TXTgv.setOnAction(event -> saveText(TXTgv, displayLabel1));
        TXTgv.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTgv, displayLabel1);
        });

        IMclose.setOnMouseClicked(event -> {
            Stage stage = (Stage) IMclose.getScene().getWindow();
            if(TXTtieude.getText().isEmpty() && TXTgv.getText().isEmpty()){
                stage.close();
                return;
            }else if(TXTtieude.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Tên của học phần trống!");
                alert.setContentText("Vui lòng nhập tên của học phần trước khi lưu.");
                alert.showAndWait();
                return;
            }else if(TXTgv.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Tên của giảng viên trống!");
                alert.setContentText("Vui lòng nhập tên của giảng viên trước khi lưu.");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Lưu thay đổi?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                DTO_HocPhan h = new DTO_HocPhan();
                h.setMaHP(bllhp.tangma());
                h.setTenHP(TXTtieude.getText());
                h.setGiangVien(TXTgv.getText());
                if(CBBtrangthai.getValue().equals("Chưa hoàn thành") || CBBtrangthai.getValue().isEmpty()){
                    h.setTrangThai(false);
                }else{
                    h.setTrangThai(true);
                }
                h.setMoTa(TAmota.getText());
                h.setMaTK(MaND);
                bllhp.them1(h);
                stage.close();
            }else {
                stage.close();
            }
        });
    }

    private void enableEditing(TextField t, Label l) {
        t.setText(l.getText());
        t.setVisible(true);
        l.setVisible(false);
        t.requestFocus();
        t.selectAll();
    }

    private void saveText(TextField t, Label l) {
        l.setText(t.getText().trim().isEmpty() ? "Nhấn để chỉnh sửa" : t.getText());
        t.setVisible(false);
        l.setVisible(true);
    }
}
