package app.doan.GUI;

import app.doan.BLL.BLL_BaiHoc;
import app.doan.DTO.DTO_BaiHoc;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static app.doan.GUI.HTtodolist.mahienthi;

public class ThemBH {
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

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button BTdate;

    @FXML
    private Button BTcancel;

    @FXML
    private Label LBdate;

    BLL_BaiHoc bllbh = new BLL_BaiHoc();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate d;

    @FXML
    public void initialize(){
        LBdate.setText("Trống");

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

        datePicker.setOpacity(0);
        datePicker.setDisable(true);

        // hien thi lich khi chon button
        BTdate.setOnAction(event -> {
            datePicker.setOpacity(1);
            datePicker.setDisable(false);
            datePicker.show();
        });
        datePicker.setOnAction(event -> { //Xu ly su kien khi chon ngay
            d = datePicker.getValue();
            String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LBdate.setText(selectedDate);
            datePicker.setOpacity(0);
            datePicker.setDisable(true);
        });
        BTcancel.setOnAction(event -> { // Huy chon ngay
            datePicker.setValue(null);
            LBdate.setText("Trống");
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
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Lưu thay đổi?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    DTO_BaiHoc b = new DTO_BaiHoc();
                    b.setMaBH(bllbh.tangma());
                    b.setTenBH(displayLabel.getText());
                    b.setGhiChu(TAmota.getText());
                    if(CBBtrangthai.getValue().equals("Chưa hoàn thành")){
                        b.setTrangThai(false);
                    }else b.setTrangThai(true);
                    if("Trống".equals(LBdate.getText())){
                        b.setNgayHoc(null);
                    }else {
                        LocalDate date = LocalDate.parse(LBdate.getText(), formatter);
                        b.setNgayHoc(date);
                    }
                    b.setMaChuong(mahienthi);
                    bllbh.them1(b);
                    stage.close();
                }else {
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
