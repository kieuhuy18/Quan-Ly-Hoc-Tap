package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.DTO.DTO_CongViec;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import static app.doan.BLL.BLL_CongViec.*;
import static app.doan.DAL.DAL_CongViec.cvList;

public class Menu {
    @FXML private BarChart<String, Number> barChart;
    @FXML private LineChart<String, Number> lineChartPomodoroWeek;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label CVCHT;
    @FXML private Label CVHT;
    @FXML private CategoryAxis lineChartXAxis;
    @FXML private NumberAxis lineChartYAxis;

    BLL_CongViec bllcv = new BLL_CongViec();

    public void initialize() {
        bllcv.chialist();
        bllcv.trangchuCV();
        xAxis.setLabel("Công việc");
        yAxis.setLabel("Số Pomodoro");

        CVCHT.setText(String.valueOf(cht));
        CVHT.setText(String.valueOf(ht));

        XYChart.Series<String, Number> estimatedSeries = new XYChart.Series<>();
        estimatedSeries.setName("Ước tính");

        XYChart.Series<String, Number> actualSeries = new XYChart.Series<>();
        actualSeries.setName("Thực tế");

        for (DTO_CongViec cv : today) {
            String tenGoc = cv.getTenCV();
            String tenHienThi = tenGoc.length() > 15 ? tenGoc.substring(0, 12) + "..." : tenGoc;

            XYChart.Data<String, Number> est = new XYChart.Data<>(tenHienThi, cv.getPomoUT());
            XYChart.Data<String, Number> act = new XYChart.Data<>(tenHienThi, cv.getPomoTT());

            Tooltip.install(est.getNode(), new Tooltip("Ước tính: " + cv.getPomoUT() + "\n" + tenGoc));
            Tooltip.install(act.getNode(), new Tooltip("Thực tế: " + cv.getPomoTT() + "\n" + tenGoc));

            estimatedSeries.getData().add(est);
            actualSeries.getData().add(act);
        }
        barChart.getData().addAll(estimatedSeries, actualSeries);

        LocalDate today = LocalDate.now();
        DayOfWeek currentDayOfWeek = today.getDayOfWeek();
        LocalDate startOfWeek = today.minusDays(currentDayOfWeek.getValue() - 1); // Thứ Hai
        LocalDate endOfWeek = startOfWeek.plusDays(6); // Chủ Nhật

        Map<DayOfWeek, Integer> pomodoroTheoNgay = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            pomodoroTheoNgay.put(day, 0);
        }

        for (DTO_CongViec cv : cvList) {
            LocalDate ngayCV = cv.getThoiGian(); // Giả sử kiểu dữ liệu là LocalDate
            if ((ngayCV.isEqual(startOfWeek) || ngayCV.isAfter(startOfWeek)) &&
                    (ngayCV.isEqual(endOfWeek) || ngayCV.isBefore(endOfWeek))) {

                DayOfWeek day = ngayCV.getDayOfWeek();
                pomodoroTheoNgay.put(day, pomodoroTheoNgay.get(day) + cv.getPomoTT());
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pomodoro thực tế trong tuần");

        DayOfWeek[] thuTu = {
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
        };

        for (DayOfWeek day : thuTu) {
            String tenThu = chuyenSangTiengViet(day);
            int soPomo = pomodoroTheoNgay.getOrDefault(day, 0);
            series.getData().add(new XYChart.Data<>(tenThu, soPomo));
        }

        lineChartPomodoroWeek.getData().clear();
        lineChartPomodoroWeek.getData().add(series);

    }

    private String chuyenSangTiengViet(DayOfWeek day) {
        switch (day) {
            case MONDAY: return "Thứ 2";
            case TUESDAY: return "Thứ 3";
            case WEDNESDAY: return "Thứ 4";
            case THURSDAY: return "Thứ 5";
            case FRIDAY: return "Thứ 6";
            case SATURDAY: return "Thứ 7";
            case SUNDAY: return "CN";
            default: return "";
        }
    }

}
