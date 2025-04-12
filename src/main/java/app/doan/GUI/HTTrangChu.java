package app.doan.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static app.doan.GUI.Pomodoro.isRunning;

public class HTTrangChu {
    @FXML
    private Button BTtrangchu;

    @FXML
    private Button BTtodo;

    @FXML
    private Button BTpomo;

    @FXML
    private Button BTtaikhoan;

    @FXML
    public BorderPane mainPane;

    public void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane loadedPane = loader.load();

            if (mainPane != null) {
                mainPane.setCenter(loadedPane);

                if (fxmlFile.equals("/app/doan/HTtodolist.fxml")) {
                    HTtodolist htTodolistController = loader.getController();
                    htTodolistController.setMainController(this);
                }
            } else {
                System.out.println("ERROR: mainPane is NULL!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPomo() {
        loadView("/app/doan/HTPomodoro.fxml");
    }

    public void showCV() {
        loadView("/app/doan/HTtodolist.fxml");
    }

    public void showTK() {
        loadView("/app/doan/HTNguoiDung.fxml");
    }

    public void showTrangChu() {
        loadView("/app/doan/Menu.fxml");
    }

    @FXML
    public void initialize(){
        showTrangChu();
        BTtrangchu.setOnAction(actionEvent -> {
            if(isRunning){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Bạn muốn tạm dừng Pomodoro");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    showTrangChu();
                }
            }
            else{
                showTrangChu();
            }
        });
    }
}
