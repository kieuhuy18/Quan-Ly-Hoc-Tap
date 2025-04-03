package app.doan.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HTTrangChu {
    @FXML
    public BorderPane mainPane;

    public void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane loadedPane = loader.load(); // Đổi từ BorderPane -> AnchorPane

            if (mainPane != null) {
                mainPane.setCenter(loadedPane);
            } else {
                System.out.println("ERROR: mainPane is NULL!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void showHome() {
//        loadView("/app/doan/home.fxml");
//    }

    public void showTasks() {
        loadView("/app/doan/HTtodolist.fxml");
    }

    public void showSettings() {
        loadView("/app/doan/settings.fxml");
    }
}
