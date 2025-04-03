package app.doan.GUI;

import app.doan.DTO.DTO_CongViec;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CTCV {
    @FXML
    private Label LBcheck;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField TXTtieude;

    @FXML
    private StackPane container;

    @FXML
    private Label displayLabel;

    @FXML
    private ImageView IMclose;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button BTdate;

    @FXML
    private Label LBdate;

    @FXML
    private Button BTdut;

    @FXML
    private  Label LBdut;

    private LocalDate d;
    private int dut = 0;
    private ContextMenu contextMenu;
    private boolean isChecked = false;

    private final Image uncheckedImage = new Image(getClass().getResourceAsStream("/app/doan/image/check_box_outline_blank.png"));
    private final Image checkedImage = new Image(getClass().getResourceAsStream("/app/doan/image/check_box.png"));

    @FXML
    public void initialize() {
        imageView.setImage(uncheckedImage);
        LBcheck.setOnMouseClicked(event -> toggleCheckbox());

        // Ban đầu chỉ hiển thị Label, ẩn TextField
        TXTtieude.setVisible(false);
        TXTtieude.setText("Huy ne");
        displayLabel.setText(TXTtieude.getText());

        // Gán sự kiện double-click vào Label để chuyển sang TextField
        displayLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing();
            }
        });

        // Sự kiện nhấn Enter hoặc mất focus để lưu nội dung
        TXTtieude.setOnAction(event -> saveText());
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText();
        });

        IMclose.setOnMouseClicked(event -> {
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();
        });

        //An datepicker
        datePicker.setOpacity(0);
        datePicker.setDisable(true);

        // hien thi lich khi chon button
        BTdate.setOnAction(event -> {
            datePicker.setOpacity(1);
            datePicker.setDisable(false);
            datePicker.show();
        });

        // Cap nhat label khi chon ngay
        datePicker.setOnAction(event -> {
            d = datePicker.getValue();
            String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LBdate.setText(selectedDate);
            datePicker.setOpacity(0);
            datePicker.setDisable(true);
        });

        contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("do u tien cao");
        MenuItem item2 = new MenuItem("do u tien vua");
        MenuItem item3 = new MenuItem("do u tien thap");
        MenuItem item4 = new MenuItem("khong");

        item1.setStyle("-fx-text-fill: #333333;");
        item2.setStyle("-fx-text-fill: #333333;");
        item3.setStyle("-fx-text-fill: #333333;");
        item4.setStyle("-fx-text-fill: #333333;");

        item1.setOnAction(e -> handleMenuItemClick(item1, 1));
        item2.setOnAction(e -> dut = 2);
        item3.setOnAction(e -> dut = 3);
        item4.setOnAction(e -> dut = 0);

        contextMenu.getItems().addAll(item1, item2, item3, item4);

        BTdut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(BTdate, BTdate.localToScreen(0, BTdate.getHeight()).getX(),
                    BTdate.localToScreen(0, BTdate.getHeight()).getY());
        });
    }

    private void toggleCheckbox() {
        isChecked = !isChecked;
        imageView.setImage(isChecked ? checkedImage : uncheckedImage);
    }

    private void enableEditing() {
        TXTtieude.setText(displayLabel.getText());
        TXTtieude.setVisible(true);
        displayLabel.setVisible(false);
        TXTtieude.requestFocus();
        TXTtieude.selectAll();
    }

    private void saveText() {
        displayLabel.setText(TXTtieude.getText().trim().isEmpty() ? "Nhấn đúp để chỉnh sửa" : TXTtieude.getText());
        TXTtieude.setVisible(false);
        displayLabel.setVisible(true);
    }

    private void handleMenuItemClick(MenuItem item, int value) {
        dut = value;
        LBdut.setText(String.valueOf(item));
    }
}
