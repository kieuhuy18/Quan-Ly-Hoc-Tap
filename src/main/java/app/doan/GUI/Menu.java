package app.doan.GUI;

import app.doan.BLL.BLL_CongViec;
import app.doan.DTO.DTO_CongViec;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

import static app.doan.BLL.BLL_CongViec.*;

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

        // Giả sử bạn có danh sách:
        for (DTO_CongViec cv : today) {
            estimatedSeries.getData().add(new XYChart.Data<>(cv.getTenCV(), cv.getPomoUT()));
            actualSeries.getData().add(new XYChart.Data<>(cv.getTenCV(), cv.getPomoTT()));
        }
        barChart.getData().addAll(estimatedSeries, actualSeries);

        Map<DayOfWeek, Integer> pomodoroTheoNgay = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            pomodoroTheoNgay.put(day, 0);
        }

        for (DTO_CongViec cv : today) {
            DayOfWeek day = cv.getThoiGian().getDayOfWeek();
            pomodoroTheoNgay.put(day, pomodoroTheoNgay.get(day) + cv.getPomoTT());
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pomodoro thực tế");

// Tuần bắt đầu từ Monday đến Sunday
        DayOfWeek[] thuTu = {
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
        };

        for (DayOfWeek day : thuTu) {
            String tenThu = chuyenSangTiengViet(day); // bạn có thể tự tạo hàm này nếu muốn
            int soPomo = pomodoroTheoNgay.getOrDefault(day, 0);
            series.getData().add(new XYChart.Data<>(tenThu, soPomo));
        }

        lineChartPomodoroWeek.getData().clear(); // xóa dữ liệu cũ nếu có
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
