package app.doan;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import app.doan.DTO.DTO_CongViec;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    private ObservableList<DTO_CongViec> tasks;

    public static void main(String[] args) {
            launch(args);
        }

    public Main() { }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        //màn hinh dang nhap
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/doan/HTDangNhap.fxml"));
//        Scene scene = new Scene(loader.load());
//        primaryStage.setTitle("Dang Nhap");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();

        //trang chu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/doan/HTTrangChu.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Trang chu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setHeight(830);
        primaryStage.show();
    }

    public static void switchScene(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/app/doan/" + fxmlFile));
        Scene scene = new Scene(loader.load(), 1280, 720);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }
}
