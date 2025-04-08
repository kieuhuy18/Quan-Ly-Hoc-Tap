package app.doan.GUI;

import app.doan.BLL.BLL_HocPhan;
import app.doan.DTO.DTO_HocPhan;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

import static app.doan.GUI.HTDangNhap.MaND;
import static app.doan.GUI.HTtodolist.mahienthi;

public class CTHP {
    @FXML
    private StackPane container;

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    BLL_HocPhan bllhp = new BLL_HocPhan();

    @FXML
    public void initialize(){
        DTO_HocPhan hp = bllhp.tim(mahienthi);
        TXTtieude.setVisible(false);
        TXTtieude.setText(hp.getTenHP());
        displayLabel.setText(TXTtieude.getText());
        TXTgv.setVisible(false);
        TXTgv.setText(hp.getGiangVien());
        displayLabel1.setText(TXTgv.getText());
        TAmota.setText(hp.getMoTa());
        CBBtrangthai.getItems().addAll("Chưa hoàn thành", "Hoàn thành");
        if(hp.getTrangThai()){
            CBBtrangthai.setValue("Hoàn thành");
        }else CBBtrangthai.setValue("Chưa hoàn thành");

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
            DTO_HocPhan h = new DTO_HocPhan();
            h.setMaHP(hp.getMaHP());
            h.setTenHP(TXTtieude.getText());
            h.setGiangVien(TXTgv.getText());
            h.setTrangThai(!CBBtrangthai.getValue().equals("Chưa hoàn thành"));
            h.setMoTa(TAmota.getText());
            h.setMaTK(MaND);
            if(bllhp.sua(h)){
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
        l.setText(t.getText().trim().isEmpty() ? "Nhấn đúp để chỉnh sửa" : t.getText());
        t.setVisible(false);
        l.setVisible(true);
    }
}
