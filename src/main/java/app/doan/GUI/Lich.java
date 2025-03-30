package app.qlht.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lich extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("1");
        System.out.println(getClass().getResource("/app/qlht/HTDangNhap.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/qlht/HTDangNhap.fxml"));
        System.out.println("2");
        Parent root = loader.load();
        System.out.println("FXML Loaded: ");
        Scene scene = new Scene(root, 1440, 965);
        stage.setTitle("Dang Nhap");
        stage.setScene(scene);
        stage.show();
    }
}
