package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.DTO.DTO_CongViec;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

import static app.doan.BLL.BLL_CongViec.*;
import static app.doan.GUI.HTtodolist.cvht;
import static app.doan.GUI.HTtodolist.mahienthi;
import static app.doan.Main.primaryStage;

public class Pomodoro {
    @FXML
    private Button BTtimer;

    @FXML
    private Text TXTtimer;

    @FXML
    private Label LBcheck;

    @FXML
    private Label LBcv;

    @FXML
    private ListView<DTO_CongViec> listViewItems;

    private Timeline timeline;
    private int remainingSeconds;
    public static boolean isRunning = false;
    private BLL_CongViec bllcv = new BLL_CongViec();
    private DTO_CongViec c = bllcv.tim(cvht);
    static ObservableList<DTO_CongViec> observableList = FXCollections.observableArrayList(today);

    private void hienthicv(){
        DTO_CongViec cv = bllcv.tim(cvht);
        String color = switch (cv.getDoUuTien()) {
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "green";
            default -> "default";
        };
        String state = cv.getTrangThai() ? "checked" : "unchecked";
        LBcheck.setGraphic(IconProvider.getIcon(state + "_" + color));

        LBcheck.setOnMouseClicked(event -> toggleCheckbox(cv));

        LBcv.setText(cv.getTenCV());

        LBcv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                DTO_CongViec selectedItem = listViewItems.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    if (event.getTarget() instanceof CheckBox) {
                        event.consume();
                        return;
                    }
                    try {
                        mahienthi = selectedItem.getMaCV();
                        HienThiCT(selectedItem.getMaCV());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @FXML
    public void initialize(){
        if(cvht == null){
            LBcv.setText("Không có công việc đang thực hiện");
        }else{
            hienthicv();
        }

        loadListView();
        observableList = FXCollections.observableArrayList(today);
        listViewItems.setItems(observableList);
        listViewItems.setCellFactory(param -> new ListCell<>() {
            private final Label checkBoxLabel = new Label();
            private final Text taskName = new Text();
            private final Text taskDate = new Text();
            private final HBox taskLayout = new HBox(10);
            private final Button pomoButton = new Button();

            {
                checkBoxLabel.setOnMouseClicked(event -> {
                    DTO_CongViec task = getItem();
                    task.setTrangThai(!task.getTrangThai());
                    String color = switch (task.getDoUuTien()) {
                        case 1 -> "red";
                        case 2 -> "orange";
                        case 3 -> "green";
                        default -> "default";
                    };
                    String state = task.getTrangThai() ? "checked" : "unchecked";
                    checkBoxLabel.setGraphic(IconProvider.getIcon(state + "_" + color));
                    bllcv.sua(task);
                });

                taskName.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
                taskDate.setStyle("-fx-font-size: 14px;");

                VBox taskInfo = new VBox(taskName, taskDate);
                VBox checkBoxContainer = new VBox(checkBoxLabel);
                checkBoxContainer.setAlignment(Pos.CENTER);
                HBox.setMargin(checkBoxContainer, new Insets(5, 0, 5, 0));

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                ImageView pomoIcon = new ImageView(new Image(String.valueOf(getClass().getResource("/app/doan/image/Play.png"))));
                pomoIcon.setFitWidth(40);
                pomoIcon.setFitHeight(40);
                pomoButton.setGraphic(pomoIcon);
                pomoButton.setStyle("-fx-background-color: transparent;");
                pomoButton.setPrefSize(30, 30);

                pomoButton.setOnAction(e -> {
                    DTO_CongViec task = getItem();
                    cvht = task.getMaCV();
                    hienthicv();
                });

                VBox pomoBox = new VBox(pomoButton);
                pomoBox.setAlignment(Pos.CENTER);
                HBox.setMargin(pomoBox, new Insets(0, 30, 0, 0));

                taskLayout.getChildren().addAll(checkBoxContainer, taskInfo);
            }

            @Override
            protected void updateItem(DTO_CongViec task, boolean empty) {
                setPrefHeight(70);
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setGraphic(null);
                } else {
                    String color = switch (task.getDoUuTien()) {
                        case 1 -> "red";
                        case 2 -> "orange";
                        case 3 -> "green";
                        default -> "default";
                    };
                    String state = task.getTrangThai() ? "checked" : "unchecked";
                    checkBoxLabel.setGraphic(IconProvider.getIcon(state + "_" + color));
                    taskName.setText(task.getTenCV());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    taskDate.setText(task.getThoiGian() != null ? task.getThoiGian().format(formatter) : "");
                    if(task.getThoiGian().isBefore(LocalDate.now())){
                        taskDate.setFill(Color.RED);
                    }else{
                        taskDate.setFill(Color.BLACK);
                    }
                    taskName.setTranslateY(7);
                    taskDate.setTranslateY(7);
                    setGraphic(taskLayout);
                }
            }
        });

        listViewItems.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                DTO_CongViec selectedItem = listViewItems.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {

                    if (event.getTarget() instanceof CheckBox) {
                        event.consume();
                        return;
                    }
                    try {
                        mahienthi = selectedItem.getMaCV();
                        HienThiCT(selectedItem.getMaCV());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @FXML
    private void handleDecrease25Min(){
        if (!isRunning) {
            String timeText = TXTtimer.getText();
            try {
                if (remainingSeconds <= 0) {
                    remainingSeconds = parseTimeToSeconds(timeText);
                    if (remainingSeconds <= 0) {
                        return;
                    }
                }
                startTimer();
                BTtimer.setText("Tạm Ngừng");
                isRunning = true;
                if(cvht != null){
                    c.setThoiGianBatDau(LocalTime.now());
                    System.out.println(c.getThoiGianBatDau());
                }
            } catch (ParseException e) {
                TXTtimer.setText("Định dạng không đúng (phút:giây)");
            }
        } else {
            if (timeline != null) {
                timeline.pause();
                BTtimer.setText("Tiếp Tục");
                isRunning = false;
            }
        }
    }

    private int parseTimeToSeconds(String time) throws ParseException {
        StringTokenizer tokenizer = new StringTokenizer(time, ":");
        if (tokenizer.countTokens() != 2) {
            throw new ParseException("Định dạng không đúng", 0);
        }
        try {
            int minutes = Integer.parseInt(tokenizer.nextToken());
            int seconds = Integer.parseInt(tokenizer.nextToken());
            if (minutes < 0 || seconds < 0 || seconds > 59) {
                throw new ParseException("Giá trị phút hoặc giây không hợp lệ", 0);
            }
            return (minutes * 60) + seconds;
        } catch (NumberFormatException e) {
            throw new ParseException("Giá trị phút hoặc giây không phải là số", 0);
        }
    }

    private void startTimer() {
        if (timeline == null) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        remainingSeconds--;
                        updateTextField();
                        if (remainingSeconds <= 0) {
                            timeline.stop();
                            TXTtimer.setText("00:00");
                            BTtimer.setText("Bắt Đầu");
                            isRunning = false;
                            remainingSeconds = 0;
                            c.setPomoTT(c.getPomoTT() + 1);
                            bllcv.sua(c);
                        }
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } else {
            timeline.play();
        }
    }

    private void updateTextField() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        TXTtimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void HienThiCT(String ma) throws IOException {
        mahienthi = ma;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/doan/CTCV.fxml"));
        Parent root = loader.load();

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

        loadListView();
    }

    public void loadListView() {
        bllcv.tailist();
        bllcv.chialist();
    }

    private void toggleCheckbox(DTO_CongViec cv) {
        cv.setTrangThai(!cv.getTrangThai());
        String color = switch (cv.getDoUuTien()) {
            case 1 -> "red";
            case 2 -> "orange";
            case 3 -> "green";
            default -> "default";
        };
        String state = cv.getTrangThai() ? "checked" : "unchecked";
        LBcheck.setGraphic(IconProvider.getIcon(state + "_" + color));
        bllcv.sua(cv);
    }
}
