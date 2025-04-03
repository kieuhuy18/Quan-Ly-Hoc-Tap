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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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
    ObservableList<DTO_CongViec> observableList = FXCollections.observableArrayList(cvList);
    private LocalDate d;
    private int dut = 0;

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

    @FXML
    public void initialize() {
        List<HBox> hboxList = List.of(HBtoday, HBplan, HBdone, HBoverdate);

        bllhp.tailist();
        bllc.tailist();
        bllbh.tailist();
        setupTreeView(hpList);

        loadListView(1);
        listViewItems.setItems(observableList);
        listViewItems.setCellFactory(param -> new ListCell<>() {
            private final Image checkedIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/app/doan/image/check_box.png")));
            private final Image uncheckedIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/app/doan/image/check_box_outline_blank.png")));
            private final ImageView checkedView = new ImageView(checkedIcon);
            private final ImageView uncheckedView = new ImageView(uncheckedIcon);
            private final Label checkBoxLabel = new Label();
            private final Text taskName = new Text();
            private final Text taskDate = new Text();
            private final HBox taskLayout = new HBox(10);

            {
                checkedView.setFitWidth(40);
                checkedView.setFitHeight(40);
                uncheckedView.setFitWidth(40);
                uncheckedView.setFitHeight(40);

                checkBoxLabel.setOnMouseClicked(event -> {
                    DTO_CongViec task = getItem();
                    if (task != null) {
                        task.setTrangThai(!task.getTrangThai());
                        checkBoxLabel.setGraphic(task.getTrangThai() ? checkedView : uncheckedView);
                    }
                });

                taskName.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
                taskDate.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

                VBox taskInfo = new VBox(taskName, taskDate);
                VBox checkBoxContainer = new VBox(checkBoxLabel);
                checkBoxContainer.setAlignment(Pos.CENTER);
                HBox.setMargin(checkBoxContainer, new Insets(5, 0, 5, 0));

                taskLayout.getChildren().addAll(checkBoxContainer, taskInfo);
            }

            @Override
            protected void updateItem(DTO_CongViec task, boolean empty) {
                setPrefHeight(70);
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setGraphic(null);
                } else {
                    checkBoxLabel.setGraphic(task.getTrangThai() ? checkedView : uncheckedView);
                    taskName.setText(task.getTenCV());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    taskDate.setText(task.getThoiGian() != null ? task.getThoiGian().format(formatter) : "");
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
                    // Kiểm tra xem click có nằm trên CheckBox không
                    if (event.getTarget() instanceof CheckBox) {
                        event.consume();
                        return;
                    }
                    try {
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
        //setup contextMenu
        contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("do u tien cao");
        MenuItem item2 = new MenuItem("do u tien vua");
        MenuItem item3 = new MenuItem("do u tien thap");
        MenuItem item4 = new MenuItem("khong");

        item1.setStyle("-fx-text-fill: #333333;");
        item2.setStyle("-fx-text-fill: #333333;");
        item3.setStyle("-fx-text-fill: #333333;");
        item4.setStyle("-fx-text-fill: #333333;");

        item1.setOnAction(e -> dut = 1);
        item2.setOnAction(e -> dut = 2);
        item3.setOnAction(e -> dut = 3);
        item4.setOnAction(e -> dut = 0);

        contextMenu.getItems().addAll(item1, item2, item3, item4);

        //Xu ly su kien
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(c, c.localToScreen(0, c.getHeight()).getX(),
                    c.localToScreen(0, c.getHeight()).getY());
        });

        //Xu ly HBox
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
        bllcv.them1(cv);
        loadListView(1);
    }

    private void handleHBoxClick(MouseEvent event) {
        HBox clickedHBox = (HBox) event.getSource();
        if (clickedHBox == HBtoday) {
            loadListView(1);
        } else if (clickedHBox == HBplan) {
            loadListView(2);
        } else if (clickedHBox == HBdone) {
            loadListView(3);
        } else if (clickedHBox == HBoverdate) {
            loadListView(4);
        }
    }

    private void loadListView(int i) {
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
                    setStyle(""); // Reset lại style khi item trống
                } else {
                    setText(item.getDisplayName());

                    // Kiểm tra kiểu đối tượng để đặt font size
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
                    HienThiCT(hp.getMaHP(), "/app/doan/CTHP.fxml");
                    mahienthi = hp.getMaHP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedItem instanceof DTO_Chuong) {
                DTO_Chuong c = (DTO_Chuong) selectedItem;
                try {
                    HienThiCT(c.getMaChuong(), "/app/doan/CTC.fxml");
                    mahienthi = c.getMaChuong();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedItem instanceof DTO_BaiHoc) {
                DTO_BaiHoc c = (DTO_BaiHoc) selectedItem;
                try {
                    HienThiCT(c.getMaBH(), "/app/doan/CTBH.fxml");
                    mahienthi = c.getMaBH();
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
        popupStage.show();
    }
}
