package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.DTO.DTO_CongViec;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static app.doan.DAL.DAL_CongViec.cvList;

public class HTtodolist {
    private final BLL_CongViec bllcv = new BLL_CongViec();
    private LocalDate d;
    private int dut = 0;

    private ContextMenu contextMenu;

    @FXML
    public TextField TFthem;

    @FXML
    public Button a;

    @FXML
    public Button b;

    @FXML
    public Button c;

    @FXML
    private Label LBdate;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<DTO_CongViec> listViewItems;

    @FXML
    public void initialize() {
        bllcv.tailist();
        ObservableList<DTO_CongViec> observableList = FXCollections.observableArrayList(cvList);

        //ListView
        listViewItems.setItems(observableList);
        listViewItems.setCellFactory(param -> new ListCell<>() {
            private final Image checkedIcon = new Image(getClass().getResourceAsStream("/app/doan/image/check_box.png"));
            private final Image uncheckedIcon = new Image(getClass().getResourceAsStream("/app/doan/image/check_box_outline_blank.png"));

            private final ImageView checkedView = new ImageView(checkedIcon);
            private final ImageView uncheckedView = new ImageView(uncheckedIcon);
            private final Label checkBoxLabel = new Label();
            private final Text taskName = new Text();
            private final Text taskDate = new Text();
            private final HBox taskLayout = new HBox(10);

            {
                checkedView.setFitWidth(40);
                checkedView.setFitHeight(40);
                uncheckedView.setFitWidth(40);
                uncheckedView.setFitHeight(40);

                checkBoxLabel.setOnMouseClicked(event -> {
                    DTO_CongViec task = getItem();
                    if (task != null) {
                        task.setTrangThai(!task.getTrangThai());
                        checkBoxLabel.setGraphic(task.getTrangThai() ? checkedView : uncheckedView);
                    }
                });

                VBox taskInfo = new VBox(taskName, taskDate);
                taskLayout.getChildren().addAll(checkBoxLabel, taskInfo);
            }

            @Override
            protected void updateItem(DTO_CongViec task, boolean empty) {
                setPrefHeight(50);
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    // Icon khi checkbox được chọn
                    checkBoxLabel.setGraphic(task.getTrangThai() ? checkedView : uncheckedView);
                    taskName.setText(task.getTenCV());
                    taskDate.setText(task.getThoiGian() != null ? task.getThoiGian().toString() : "");
                    setGraphic(taskLayout);

                    // Ngăn ListView chọn hàng khi click vào checkbox
//                    checkBoxLabel.setMouseTransparent(false);
//                    checkBoxLabel.setFocusTraversable(false);

                    Text taskName = new Text(task.getTenCV());
                    taskName.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
                    Text taskDate = new Text();
                    if(task.getThoiGian() == null){
                        taskDate = new Text("");
                    }else{
                        taskDate = new Text(task.getThoiGian().toString());
                    }
                    taskDate.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

                    VBox taskInfo = new VBox(taskName, taskDate);

                    VBox checkBoxContainer = new VBox(checkBoxLabel);
                    checkBoxContainer.setAlignment(Pos.CENTER);
                    HBox.setMargin(checkBoxContainer, new Insets(5, 0, 5, 0));

                    HBox taskLayout = new HBox(10, checkBoxContainer, taskInfo);
                    setGraphic(taskLayout);
                }
            }
        });

        //nut a
        a.setOnAction(this::handleButtonA);

        //nut b
        LBdate.setText("Lich trong");

        datePicker.setOpacity(0); // Ẩn nhưng vẫn có thể tương tác
        datePicker.setDisable(true); // Tạm thời vô hiệu hóa

        // Khi nhấn vào Button, hiển thị DatePicker
        b.setOnAction(event -> {
            datePicker.setOpacity(1);
            datePicker.setDisable(false);
            datePicker.show();
        });

        // Khi chọn ngày, cập nhật Button và ẩn DatePicker
        datePicker.setOnAction(event -> {
            d = datePicker.getValue();
            String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LBdate.setText(selectedDate);
            datePicker.setOpacity(0);
            datePicker.setDisable(true);
        });

        //nut c
        contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("do u tien cao");
        MenuItem item2 = new MenuItem("do u tien vua");
        MenuItem item3 = new MenuItem("do u tien thap");
        MenuItem item4 = new MenuItem("khong");

        item1.setStyle("-fx-text-fill: #333333;");
        item2.setStyle("-fx-text-fill: #333333;");
        item3.setStyle("-fx-text-fill: #333333;");
        item4.setStyle("-fx-text-fill: #333333;");

        item1.setOnAction(e -> dut = 1);
        item2.setOnAction(e -> dut = 2);
        item3.setOnAction(e -> dut = 3);
        item4.setOnAction(e -> dut = 0);

        contextMenu.getItems().addAll(item1, item2, item3, item4);

        // Bắt sự kiện khi nhấn vào Button
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(c, c.localToScreen(0, c.getHeight()).getX(),
                    c.localToScreen(0, c.getHeight()).getY());
        });
    }

    @FXML
    private void handleButtonA(ActionEvent event) {
        DTO_CongViec cv = new DTO_CongViec();
        cv.setMaCV(bllcv.tangma());
        cv.setTenCV(TFthem.getText());
        cv.setThoiGian(d);
        cv.setDoUuTien(dut);

        bllcv.them1(cv);
    }

}
