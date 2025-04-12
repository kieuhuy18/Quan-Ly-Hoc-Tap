package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.BLL.BLL_TaiKhoan;
import app.doan.DTO.DTO_CongViec;
import app.doan.DTO.DTO_TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static app.doan.DAL.DAL_CongViec.cvList;
import static app.doan.GUI.HTDangNhap.MaND;
import static app.doan.Main.primaryStage;
import static app.doan.Main.switchScene;

public class NguoiDung {
    @FXML
    private Label TTGTT;

    @FXML
    private Label TGTTHN;

    @FXML
    private Label TCVTH;

    @FXML
    private Label CVTHHN;

    @FXML
    private Label TPTH;

    @FXML
    private Label PTHHN;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthYearLabel;

    @FXML
    private Label LBemail;

    @FXML
    private Label LBten;

    @FXML
    private Button BTthaydoi;

    @FXML
    private Button BTdx;

    private YearMonth currentYearMonth;

    private Map<LocalDate, Integer> pomodoroData = new HashMap<>();
    private BLL_CongViec bllcv = new BLL_CongViec();
    private BLL_TaiKhoan blltk = new BLL_TaiKhoan();

    @FXML
    public void initialize() {
        TTGTT.setText(bllcv.thongke(1) + " Phút");
        TGTTHN.setText(bllcv.thongke(2) + " Phút");
        TCVTH.setText(bllcv.thongke(3));
        CVTHHN.setText(bllcv.thongke(4));
        TPTH.setText(bllcv.thongke(5));
        PTHHN.setText(bllcv.thongke(6));

        DTO_TaiKhoan tk = blltk.tim(MaND);
        LBemail.setText(tk.getEmail());
        LBten.setText(tk.getTenND());

        BTthaydoi.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/doan/ThayDoiThongTinND.fxml"));
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

        BTdx.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Bạn có muốn đăng xuất không?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                try {
                    switchScene("HTDangNhap.fxml", "Đăng nhập");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        currentYearMonth = YearMonth.now();
        calendarGrid.setPrefWidth(350);
        calendarGrid.setPrefHeight(300);
        calendarGrid.setPadding(new Insets(10));
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);
        setDanhSachCongViec();
    }

    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }

    public void setDanhSachCongViec() {
        bllcv.tailist();
        updatePomodoroData();
        updateCalendar();
    }

    private void updatePomodoroData() {
        pomodoroData.clear(); // Xóa dữ liệu cũ
        if (cvList != null) {
            for (DTO_CongViec cv : cvList) {
                LocalDate ngayThucHien = cv.getThoiGian();
                int soPomodoro = cv.getPomoTT();
                pomodoroData.put(ngayThucHien, pomodoroData.getOrDefault(ngayThucHien, 0) + soPomodoro);
            }
        }
    }

    private void updateCalendar() {
        monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM")));

        calendarGrid.getChildren().clear();

        String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        for (int i = 0; i < dayNames.length; i++) {
            Text dayName = new Text(dayNames[i]);
            dayName.setTextAlignment(TextAlignment.CENTER);
            GridPane.setHalignment(dayName, javafx.geometry.HPos.CENTER);
            calendarGrid.add(dayName, i, 0);
        }

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentYearMonth.lengthOfMonth();
        LocalDate today = LocalDate.now(java.time.ZoneId.of("Asia/Ho_Chi_Minh"));

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = currentYearMonth.atDay(day);
            int row = (dayOfWeek + day - 1) / 7 + 1;
            int column = (dayOfWeek + day - 1) % 7;

            Text dayText = new Text(String.valueOf(day));
            dayText.setTextAlignment(TextAlignment.CENTER);

            Rectangle background = new Rectangle(40, 40);
            background.setArcWidth(10);
            background.setArcHeight(10);
            background.setStroke(Color.LIGHTGRAY); // Đặt viền mặc định là xám nhạt

            if (currentDate.isAfter(today)) {
                background.setFill(Color.LIGHTGRAY);
            } else {
                int pomodoroCount = pomodoroData.getOrDefault(currentDate, 0);
                if (pomodoroCount == 0) {
                    background.setFill(Color.LIGHTGRAY);
                } else if (pomodoroCount == 2) {
                    background.setFill(Color.web("#e0f7fa")); // Xanh nhạt
                } else if (pomodoroCount == 4) {
                    background.setFill(Color.web("#b2ebf2")); // Xanh nhạt hơn
                } else if (pomodoroCount == 8) {
                    background.setFill(Color.web("#80deea")); // Xanh vừa
                } else if (pomodoroCount == 16) {
                    background.setFill(Color.web("#4dd0e1")); // Xanh đậm hơn
                } else if (pomodoroCount > 16) {
                    background.setFill(Color.web("#26c6da")); // Xanh đậm nhất hoặc màu khác
                } else {
                    background.setFill(Color.WHITE);
                }

                // Highlight ngày hiện tại bằng màu nền và viền đen
                if (currentYearMonth.equals(YearMonth.from(today)) && day == today.getDayOfMonth()) {
                    background.setFill(Color.web("#b3e6ff")); // Màu nền highlight
                    background.setStroke(Color.BLACK); // Viền đen cho ngày hiện tại
                }
            }

            StackPane dayCell = new StackPane(background, dayText);
            StackPane.setAlignment(dayText, Pos.CENTER);
            dayCell.setPrefWidth(40);
            dayCell.setPrefHeight(40);
            GridPane.setHalignment(dayCell, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(dayCell, javafx.geometry.VPos.CENTER);
            calendarGrid.add(dayCell, column, row);
        }
    }
}