package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.DTO.DTO_CongViec;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static app.doan.GUI.HTtodolist.*;

public class CTCV {
    @FXML
    private Label LBcheck;

    @FXML
    private TextField TXTtieude;

    @FXML
    private TextField TXTut;

    @FXML
    private Label displayLabel1;

    @FXML
    private TextArea TAghichu;

    @FXML
    private Label displayLabel2;

    @FXML
    private Label displayLabel3;

    @FXML
    private ImageView IMclose;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button BTdate;

    @FXML
    private Button BTcancel;

    @FXML
    private Label LBdate;

    @FXML
    private Button BTdut;

    @FXML
    private  Label LBdut;

    private LocalDate d;
    private int dut = 0;
    private ContextMenu contextMenu;
    private final BLL_CongViec bllcv = new BLL_CongViec();
    public DTO_CongViec c = new DTO_CongViec();
    DTO_CongViec cv = bllcv.tim(mahienthi);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize() {
        //setup
        c.setTrangThai(cv.getTrangThai());
        LBcheck.setOnMouseClicked(event -> toggleCheckbox());

        String url1 = String.valueOf(getClass().getResource("/app/doan/image/FlagRed.png"));
        String url2 = String.valueOf(getClass().getResource("/app/doan/image/FlagOrange.png"));
        String url3 = String.valueOf(getClass().getResource("/app/doan/image/FlagGreen.png"));
        String url0 = String.valueOf(getClass().getResource("/app/doan/image/Flag.png"));

        //Hienthi
        String color = switch (cv.getDoUuTien()) {
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "green";
            default -> "default";
        };
        String state = cv.getTrangThai() ? "checked" : "unchecked";
        LBcheck.setGraphic(IconProvider.getIcon(state + "_" + color));

        if(cv.getThoiGian() == null){
            LBdate.setText("Trống");
        }else LBdate.setText(cv.getThoiGian().format(formatter));

        if(cv.getDoUuTien() == 0){
            LBdut.setText("Không ưu tiên");
            BTdut.setStyle("-fx-background-image: url('" + url0 + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
        }else if(cv.getDoUuTien() == 1){
            LBdut.setText("Độ ưu tiên cao");
            BTdut.setStyle("-fx-background-image: url('" + url1 + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
        }else if(cv.getDoUuTien() == 2){
            LBdut.setText("Độ ưu tiên trung bình");
            BTdut.setStyle("-fx-background-image: url('" + url2 + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
        }else {
            LBdut.setText("Độ ưu tiên thấp");
            BTdut.setStyle("-fx-background-image: url('" + url3 + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
        }

        TAghichu.setText(cv.getGhiChu());
        TXTtieude.setVisible(false);
        TXTtieude.setText(cv.getTenCV());
        displayLabel1.setText(TXTtieude.getText());
        displayLabel2.setText(String.valueOf(cv.getPomoTT()));
        TXTut.setVisible(false);
        TXTut.setText(String.valueOf(cv.getPomoUT()));
        displayLabel3.setText(TXTut.getText());

        displayLabel1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing(TXTtieude, displayLabel1);
            }
        });
        displayLabel3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing(TXTut,displayLabel3);
            }
        });

        TXTtieude.setOnAction(event -> saveText(TXTtieude, displayLabel1));
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTtieude, displayLabel1);
        });
        TXTut.setOnAction(event -> saveText(TXTut, displayLabel3));
        TXTut.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText(TXTut, displayLabel3);
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

         //menu uu tien
        contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Độ ưu tiên cao");
        MenuItem item2 = new MenuItem("Độ ưu tiên trung bình");
        MenuItem item3 = new MenuItem("Độ ưu tiên thấp");
        MenuItem item4 = new MenuItem("Không ưu tiên");

        item1.setStyle("-fx-text-fill: #333333;");
        item2.setStyle("-fx-text-fill: #333333;");
        item3.setStyle("-fx-text-fill: #333333;");
        item4.setStyle("-fx-text-fill: #333333;");

        item1.setOnAction(e -> handleMenuItemClick(item1, 1, url1));
        item2.setOnAction(e -> handleMenuItemClick(item2, 2, url2));
        item3.setOnAction(e -> handleMenuItemClick(item3, 3, url3));
        item4.setOnAction(e -> handleMenuItemClick(item4, 0,url0));

        contextMenu.getItems().addAll(item1, item2, item3, item4);

        BTdut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { // Mo menu
            contextMenu.show(BTdut, BTdut.localToScreen(0, BTdut.getHeight()).getX(),
                    BTdut.localToScreen(0, BTdut.getHeight()).getY());
        });

        //Dong man hinh
        IMclose.setOnMouseClicked(event -> {
            c.setMaCV(cv.getMaCV());
            c.setTenCV(displayLabel1.getText());
            if("Trống".equals(LBdate.getText())){
                c.setThoiGian(null);
            }else {
                LocalDate date = LocalDate.parse(LBdate.getText(), formatter);
                c.setThoiGian(date);
            }
            c.setDoUuTien(dut);
            c.setPomoUT(Integer.parseInt(displayLabel3.getText()));
            c.setPomoTT(Integer.parseInt(displayLabel2.getText()));
            c.setGhiChu(TAghichu.getText());
            Stage stage = (Stage) IMclose.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Lưu thay đổi?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                bllcv.sua(c);
                stage.close();
            }else {
                bllcv.sua(cv);
                stage.close();
            }
        });
    }

    private void toggleCheckbox() {
        c.setTrangThai(!c.getTrangThai());
        String color = switch (c.getDoUuTien()) {
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "green";
            default -> "default";
        };
        String state = c.getTrangThai() ? "checked" : "unchecked";
        LBcheck.setGraphic(IconProvider.getIcon(state + "_" + color));
    }

    private void enableEditing(TextField tx, Label lb) {
        tx.setText(lb.getText());
        tx.setVisible(true);
        lb.setVisible(false);
        tx.requestFocus();
        tx.selectAll();
    }

    private void saveText(TextField tx, Label lb) {
        lb.setText(tx.getText().trim().isEmpty() ? "Nhấn đúp để chỉnh sửa" : tx.getText());
        tx.setVisible(false);
        lb.setVisible(true);
    }

    private void handleMenuItemClick(MenuItem item, int value, String url) {
        dut = value;
        LBdut.setText(item.getText());
        BTdut.setStyle("-fx-background-image: url('" + url + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
        String color = switch (c.getDoUuTien()) {
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "green";
            default -> "default";
        };
        String state = c.getTrangThai() ? "checked" : "unchecked";
        LBcheck.setGraphic(IconProvider.getIcon(state + "_" + color));
    }
}
