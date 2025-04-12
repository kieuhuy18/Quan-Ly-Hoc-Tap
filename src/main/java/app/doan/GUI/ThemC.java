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

public class ThemC {
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
        TXTtieude.setVisible(false);
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
        TXTtieude.setOnAction(event -> saveText(TXTtieude, displayLabel));
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTtieude, displayLabel);
        });

        IMclose.setOnMouseClicked(event -> {
            Stage stage = (Stage) IMclose.getScene().getWindow();
            if(TXTtieude.getText().isEmpty() && TAmota.getText() != null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Không có tiêu đề");
                alert.setContentText("Vui lòng nhập tiêu đề.");
                alert.showAndWait();
            }else if(TXTtieude.getText().isEmpty() && TAmota.getText().isEmpty()){
                stage.close();
            } else{
                DTO_Chuong ch = new DTO_Chuong();
                ch.setMaChuong(bllc.tangma());
                ch.setTenChuong(TXTtieude.getText());
                if(CBBtrangthai.getValue().equals("Chưa hoàn thành") || CBBtrangthai.getValue().isEmpty()){
                    ch.setTrangThai(false);
                }else{
                    ch.setTrangThai(true);
                }
                ch.setMoTa(TAmota.getText());
                ch.setMaHP(mahienthi);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Lưu?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    bllc.them1(ch);
                    stage.close();
                }else{
                    stage.close();
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
