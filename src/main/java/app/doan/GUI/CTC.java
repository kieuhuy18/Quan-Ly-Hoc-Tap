package app.doan.GUI;

import app.doan.BLL.BLL_Chuong;
import app.doan.DTO.DTO_Chuong;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;

import static app.doan.GUI.HTtodolist.mahienthi;

public class CTC {
    @FXML
    private TextField TXTtieude;

    @FXML
    private Label displayLabel;

    @FXML
    private ImageView IMclose;

    @FXML
    private ComboBox<String> CBBtrangthai;

    @FXML
    private TextArea TAmota;

    BLL_Chuong bllc = new BLL_Chuong();

    @FXML
    public void initialize(){
        DTO_Chuong c = bllc.tim(mahienthi);
        TXTtieude.setVisible(false);
        TXTtieude.setText(c.getTenChuong());
        displayLabel.setText(TXTtieude.getText());
        TAmota.setText(c.getMoTa());
        CBBtrangthai.getItems().addAll("Chưa hoàn thành", "Hoàn thành");
        if(c.getTrangThai()){
            CBBtrangthai.setValue("Hoàn thành");
        }else CBBtrangthai.setValue("Chưa hoàn thành");
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
        TXTtieude.setOnAction(event -> saveText(TXTtieude, displayLabel));
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTtieude, displayLabel);
        });

        IMclose.setOnMouseClicked(event -> {
            DTO_Chuong chuong = new DTO_Chuong();
            if(TXTtieude.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Không có tiêu đề");
                alert.setContentText("Vui lòng nhập tiêu đề.");
                alert.showAndWait();
            }else{
                chuong.setMaChuong(c.getMaChuong());
                chuong.setTenChuong(displayLabel.getText());
                chuong.setMoTa(TAmota.getText());
                chuong.setMaHP(c.getMaHP());
                if(CBBtrangthai.getValue().equals("Chưa hoàn thành")){
                    chuong.setTrangThai(false);
                }else chuong.setTrangThai(true);
                Stage stage = (Stage) IMclose.getScene().getWindow();
                if(chuong.equals(c)){
                    stage.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận");
                    alert.setHeaderText("Lưu thay đổi?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK){
                        bllc.sua(chuong);
                        stage.close();
                    }else {
                        bllc.sua(c);
                        stage.close();
                    }
                }
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
        l.setText(t.getText().trim().isEmpty() ? "Nhấn đúp để chỉnh sửa" : t.getText());
        t.setVisible(false);
        l.setVisible(true);
    }
}
