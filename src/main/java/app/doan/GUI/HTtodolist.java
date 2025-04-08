package app.doan.GUI;

import app.doan.BLL.BLL_BaiHoc;
import app.doan.BLL.BLL_Chuong;
import app.doan.BLL.BLL_CongViec;
import app.doan.BLL.BLL_HocPhan;
import app.doan.DTO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static app.doan.BLL.BLL_CongViec.*;
import static app.doan.DAL.DAL_BaiHoc.bhList;
import static app.doan.DAL.DAL_CongViec.cvList;
import static app.doan.DAL.DAL_HocPhan.hpList;
import static app.doan.Main.primaryStage;

public class HTtodolist {
    public static String mahienthi = null;
    private final BLL_CongViec bllcv = new BLL_CongViec();
    private final BLL_HocPhan bllhp = new BLL_HocPhan();
    private final BLL_Chuong bllc = new BLL_Chuong();
    private final BLL_BaiHoc bllbh = new BLL_BaiHoc();
    static ObservableList<DTO_CongViec> observableList = FXCollections.observableArrayList(cvList);
    private LocalDate d;
    private int dut = 0;
    private static int list = 1;
    public static String cvht;

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
    private HBox HBtoday;

    @FXML
    private HBox HBplan;

    @FXML
    private HBox HBdone;

    @FXML
    private HBox HBoverdate;

    @FXML
    private TreeView<HienThi> treeView;

    private HTTrangChu htTrangChu;

    public void setMainController(HTTrangChu controller) {
        this.htTrangChu = controller;
    }

    @FXML
    public void initialize() {
        //setup
        List<HBox> hboxList = List.of(HBtoday, HBplan, HBdone, HBoverdate);
        bllhp.tailist();
        bllc.tailist();
        bllbh.tailist();
        setupTreeView(hpList);

        //listview
        loadListView(list);
        listViewItems.setItems(observableList);
        listViewItems.setCellFactory(param -> new ListCell<>() { //setup tung dong trong listview
            private final Label checkBoxLabel = new Label();
            private final Text taskName = new Text();
            private final Text taskDate = new Text();
            private final HBox taskLayout = new HBox(10);
            private final Button pomoButton = new Button();
            {
                //Xu ly su kien khi chon vao o checkbox
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
                    if (htTrangChu != null) {
                        htTrangChu.showPomo();
                    }
                });

                VBox pomoBox = new VBox(pomoButton);
                pomoBox.setAlignment(Pos.CENTER);
                HBox.setMargin(pomoBox, new Insets(0, 30, 0, 0));

                taskLayout.getChildren().addAll(checkBoxContainer, taskInfo, spacer, pomoBox);
                taskLayout.setMaxWidth(Double.MAX_VALUE);
                setMaxWidth(Double.MAX_VALUE);
            }

            //Cap nhat tung dong trong listview
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

        //Xu ly su kien khi nha vao mot dong trong listview
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
                        HienThiCT(selectedItem.getMaCV(), "/app/doan/CTCV.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        //nut a
        a.setOnAction(this::handleButtonA);

        //nut b
        LBdate.setText("Lich trong");

        datePicker.setOpacity(0);
        datePicker.setDisable(true);

        b.setOnAction(event -> {
            datePicker.setOpacity(1);
            datePicker.setDisable(false);
            datePicker.show();
        });

        datePicker.setOnAction(event -> {
            d = datePicker.getValue();
            String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LBdate.setText(selectedDate);
            datePicker.setOpacity(0);
            datePicker.setDisable(true);
        });

        //nut c
        contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Độ ưu tiên cao");
        MenuItem item2 = new MenuItem("Độ ưu tiên trung bình");
        MenuItem item3 = new MenuItem("Độ ưu tiên thấp");
        MenuItem item4 = new MenuItem("Không ưu tiên");

        item1.setStyle("-fx-text-fill: #333333;");
        item2.setStyle("-fx-text-fill: #333333;");
        item3.setStyle("-fx-text-fill: #333333;");
        item4.setStyle("-fx-text-fill: #333333;");

        String url1 = String.valueOf(getClass().getResource("/app/doan/image/FlagRed.png"));
        String url2 = String.valueOf(getClass().getResource("/app/doan/image/FlagOrange.png"));
        String url3 = String.valueOf(getClass().getResource("/app/doan/image/FlagGreen.png"));
        String url0 = String.valueOf(getClass().getResource("/app/doan/image/Flag.png"));

        item1.setOnAction(e -> {dut = 1; c.setStyle("-fx-background-image: url('" + url1 + "/'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");});
        item2.setOnAction(e -> {dut = 2; c.setStyle("-fx-background-image: url('" + url2 + "'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");});
        item3.setOnAction(e -> {dut = 3; c.setStyle("-fx-background-image: url('" + url3 + "'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");});
        item4.setOnAction(e -> {dut = 0; c.setStyle("-fx-background-image: url('" + url0 + "'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");});

        contextMenu.getItems().addAll(item1, item2, item3, item4);

        //Xu ly su kien
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(c, c.localToScreen(0, c.getHeight()).getX(),
                    c.localToScreen(0, c.getHeight()).getY());
        });

        for (HBox hbox : hboxList) {
            hbox.setOnMouseClicked(this::handleHBoxClick);
        }
    }

    @FXML
    private void handleButtonA(ActionEvent event) {
        DTO_CongViec cv = new DTO_CongViec();
        cv.setMaCV(bllcv.tangma());
        cv.setTenCV(TFthem.getText());
        cv.setThoiGian(d);
        cv.setDoUuTien(dut);
        cv.setPomoTT(0);
        cv.setPomoUT(0);
        bllcv.them1(cv);
        list = 1;
        loadListView(list);
    }

    private void handleHBoxClick(MouseEvent event) {
        HBox clickedHBox = (HBox) event.getSource();
        if (clickedHBox == HBtoday) {
            list = 1;
        } else if (clickedHBox == HBplan) {
            list = 2;
        } else if (clickedHBox == HBdone) {
            list = 3;
        } else if (clickedHBox == HBoverdate) {
            list = 4;
        }
        loadListView(list);
    }

    public void loadListView(int i) {
        bllcv.tailist();
        bllcv.chialist();
        if (i == 0) {
            observableList.setAll(cvList);
        } else if (i == 1) {
            observableList.setAll(today);
        } else if (i == 2) {
            observableList.setAll(plan);
        } else if (i == 3) {
            observableList.setAll(done);
        } else if (i == 4) {
            observableList.setAll(overdate);
        }
    }

    private void setupTreeView(List<DTO_HocPhan> hocPhanList) {
        TreeItem<HienThi> rootItem = new TreeItem<>(new HienThi() {
            @Override
            public String getDisplayName() {
                return "Danh sach hoc phan";
            }
        });
        rootItem.setExpanded(true);

        for (DTO_HocPhan hocPhan : hocPhanList) {
            TreeItem<HienThi> hocPhanNode = new TreeItem<>(hocPhan);
            hocPhanNode.setExpanded(false);

            for (DTO_Chuong chuong : bllc.timtheohocphan(hocPhan.getMaHP())) {
                TreeItem<HienThi> chuongNode = new TreeItem<>(chuong);
                chuongNode.setExpanded(false);

                for (DTO_BaiHoc baiHoc : bllbh.timtheochuong(chuong.getMaChuong())) {
                    TreeItem<HienThi> baiHocNode = new TreeItem<>(baiHoc);
                    chuongNode.getChildren().add(baiHocNode);
                }

                hocPhanNode.getChildren().add(chuongNode);
            }

            rootItem.getChildren().add(hocPhanNode);
        }

        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);

        // Tùy chỉnh hiển thị
        treeView.setCellFactory(tv -> new javafx.scene.control.TreeCell<>() {
            @Override
            protected void updateItem(HienThi item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                    setStyle("");
                } else {
                    setText(item.getDisplayName());
                    if (item instanceof DTO_HocPhan) {
                        setStyle("-fx-font-size: 20px;");
                    } else if (item instanceof DTO_Chuong) {
                        setStyle("-fx-font-size: 18px;");
                    } else if (item instanceof DTO_BaiHoc) {
                        setStyle("-fx-font-size: 16px;");
                    }
                }
            }
        });

        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            HienThi selectedItem = newVal.getValue();
            if (selectedItem instanceof DTO_HocPhan) {
                DTO_HocPhan hp = (DTO_HocPhan) selectedItem;
                try {
                    mahienthi = hp.getMaHP();
                    HienThiCT(hp.getMaHP(), "/app/doan/CTHP.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedItem instanceof DTO_Chuong) {
                DTO_Chuong c = (DTO_Chuong) selectedItem;
                try {
                    mahienthi = c.getMaChuong();
                    HienThiCT(c.getMaChuong(), "/app/doan/CTC.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedItem instanceof DTO_BaiHoc) {
                DTO_BaiHoc c = (DTO_BaiHoc) selectedItem;
                try {
                    mahienthi = c.getMaBH();
                    HienThiCT(c.getMaBH(), "/app/doan/CTBH.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void HienThiCT(String ma, String fxml) throws IOException {
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
        loadListView(list);
    }
}
