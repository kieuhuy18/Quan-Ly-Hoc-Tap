package app.doan.GUI;

import app.doan.BLL.BLL_BaiHoc;
import app.doan.BLL.BLL_Chuong;
import app.doan.BLL.BLL_CongViec;
import app.doan.BLL.BLL_HocPhan;
import app.doan.DTO.DTO_BaiHoc;
import app.doan.DTO.DTO_Chuong;
import app.doan.DTO.DTO_CongViec;
import app.doan.DTO.DTO_HocPhan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static app.doan.DAL.DAL_CongViec.cvList;
import static app.doan.DAL.DAL_HocPhan.hpList;
import static app.doan.DAL.DAL_Chuong.cList;
import static app.doan.DAL.DAL_BaiHoc.bhList;

import static app.doan.GUI.HTtodolist.mahienthi;
import static app.doan.GUI.Pomodoro.isRunning;
import static app.doan.Main.primaryStage;

public class HTTrangChu {
    @FXML
    private Button BTtrangchu;

    @FXML
    private TextField TXTtimkiem;

    @FXML
    public BorderPane mainPane;

    @FXML
    private VBox vboxSuggestions;

    @FXML
    private StackPane rootPane; // Tham chiếu đến StackPane cha

    private AnchorPane overlayPane; // Lớp phủ chặn tương tác

    BLL_HocPhan bllhp = new BLL_HocPhan();
    BLL_Chuong bllc = new BLL_Chuong();
    BLL_BaiHoc bllbh = new BLL_BaiHoc();
    BLL_CongViec bllcv = new BLL_CongViec();

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

    private void showSuggestions() {
        vboxSuggestions.setVisible(true);
        //overlayPane.setVisible(true);
        overlayPane.setMouseTransparent(false); // Cho phép bắt sự kiện click
    }

    private void hideSuggestions() {
        vboxSuggestions.setVisible(false);
        overlayPane.setVisible(false);
        overlayPane.setMouseTransparent(true); // Ngăn chặn bắt sự kiện click
    }

    @FXML
    public void initialize() {
        bllhp.tailist();
        bllc.tailist();
        bllbh.tailist();
        bllcv.tailist();

        showTrangChu();

        // Tạo lớp phủ
        overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);"); // Trong suốt
        overlayPane.setVisible(false);
        overlayPane.setMouseTransparent(true); // Ban đầu không cho phép bắt sự kiện
        AnchorPane.setTopAnchor(overlayPane, 0.0);
        AnchorPane.setBottomAnchor(overlayPane, 0.0);
        AnchorPane.setLeftAnchor(overlayPane, 0.0);
        AnchorPane.setRightAnchor(overlayPane, 0.0);
        if (rootPane != null) {
            rootPane.getChildren().add(overlayPane);
        } else {
            System.out.println("ERROR: rootPane is null, check FXML!");
        }

        overlayPane.setOnMouseClicked(event -> {
            hideSuggestions();
        });

        // Gợi ý tìm kiếm
        TXTtimkiem.setOnKeyReleased(event -> {
            String input = TXTtimkiem.getText().trim().toLowerCase();
            vboxSuggestions.getChildren().clear();

            if (!input.isEmpty()) {
                List<String> suggestions = new ArrayList<>();

                if (hpList != null)
                    suggestions.addAll(hpList.stream().map(DTO_HocPhan::getTenHP).toList());
                if (cList != null)
                    suggestions.addAll(cList.stream().map(DTO_Chuong::getTenChuong).toList());
                if (bhList != null)
                    suggestions.addAll(bhList.stream().map(DTO_BaiHoc::getTenBH).toList());
                if (cvList != null)
                    suggestions.addAll(cvList.stream().map(DTO_CongViec::getTenCV).toList());

                List<String> filtered = suggestions.stream()
                        .filter(s -> s.toLowerCase().contains(input))
                        .distinct()
                        .limit(3)
                        .collect(Collectors.toList());

                if (!filtered.isEmpty()) {
                    for (String suggestion : filtered) {
                        Label label = new Label(suggestion);
                        label.setStyle("-fx-padding: 8; -fx-background-color: #808080; -fx-text-fill: #FFFFFF; -fx-font-size: 16px;");
                        label.setMaxWidth(Double.MAX_VALUE);
                        label.setOnMouseClicked(e -> {
                            TXTtimkiem.setText(suggestion);
                            hideSuggestions();

                            try {
                                Optional<DTO_HocPhan> selectedHP = hpList.stream()
                                        .filter(hp -> hp.getTenHP().equalsIgnoreCase(suggestion))
                                        .findFirst();

                                if (selectedHP.isPresent()) {
                                    mahienthi = selectedHP.get().getMaHP();
                                    HienThiCT("/app/doan/CTHP.fxml");
                                    return;
                                }

                                Optional<DTO_Chuong> selectedC = cList.stream()
                                        .filter(c -> c.getTenChuong().equalsIgnoreCase(suggestion))
                                        .findFirst();

                                if (selectedC.isPresent()) {
                                    mahienthi = selectedC.get().getMaChuong();
                                    HienThiCT("/app/doan/CTC.fxml");
                                    return;
                                }

                                Optional<DTO_BaiHoc> selectedBH = bhList.stream()
                                        .filter(bh -> bh.getTenBH().equalsIgnoreCase(suggestion))
                                        .findFirst();

                                if (selectedBH.isPresent()) {
                                    mahienthi = selectedBH.get().getMaBH();
                                    HienThiCT("/app/doan/CTBH.fxml");
                                    return;
                                }

                                Optional<DTO_CongViec> selectedCV = cvList.stream()
                                        .filter(cv -> cv.getTenCV().equalsIgnoreCase(suggestion))
                                        .findFirst();

                                if (selectedCV.isPresent()) {
                                    mahienthi = selectedCV.get().getMaCV();
                                    HienThiCT("/app/doan/CTCV.fxml");
                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });

                        vboxSuggestions.getChildren().add(label);
                    }

                    // Lấy vị trí của TXTtimkiem trong Scene
                    Bounds bounds = TXTtimkiem.localToScene(TXTtimkiem.getBoundsInLocal());
                    double layoutX = bounds.getMinX() - (rootPane != null ? rootPane.getLayoutX() : 0);
                    double layoutY = bounds.getMaxY() - (rootPane != null ? rootPane.getLayoutY() : 0);

                    vboxSuggestions.setVisible(true);
                    vboxSuggestions.setPrefWidth(TXTtimkiem.getWidth());
                    vboxSuggestions.setLayoutX(layoutX);
                    vboxSuggestions.setLayoutY(layoutY);

                    showSuggestions();

                } else {
                    hideSuggestions();
                }
            } else {
                hideSuggestions();
            }
        });

        TXTtimkiem.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                hideSuggestions();
            }
        });

        // Nút trang chủ
        BTtrangchu.setOnAction(actionEvent -> {
            if (isRunning) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Bạn muốn tạm dừng Pomodoro");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    showTrangChu();
                }
            } else {
                showTrangChu();
            }
        });
    }

    private void HienThiCT(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
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
    }
}