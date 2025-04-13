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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static app.doan.BLL.BLL_CongViec.*;
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
    private Button BTthemHP;

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
                        HienThiCT("/app/doan/CTCV.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        //nut a
        a.setOnAction(this::handleButtonA);

        //nut b
        LBdate.setText("Lịch trống");

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
        List<String> a = Arrays.asList("Độ ưu tiên cao", "Độ ưu tiên trung bình", "Độ ưu tiên thấp", "Không ưu tiên");
        ContextMenu contextMenu = setContextMenu(a);

        String[] urls = {
                String.valueOf(getClass().getResource("/app/doan/image/FlagRed.png")),
                String.valueOf(getClass().getResource("/app/doan/image/FlagOrange.png")),
                String.valueOf(getClass().getResource("/app/doan/image/FlagGreen.png")),
                String.valueOf(getClass().getResource("/app/doan/image/Flag.png"))
        };

        List<MenuItem> items = contextMenu.getItems();

        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            MenuItem item = items.get(i);
            item.setOnAction(e -> {
                dut = index + 1;
                c.setStyle("-fx-background-image: url('" + urls[index] + "'); -fx-background-size: 40px 40px; -fx-text-fill: transparent;");
            });
        }

        //Xu ly su kien
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(c, c.localToScreen(0, c.getHeight()).getX(),
                    c.localToScreen(0, c.getHeight()).getY());
        });

        for (HBox hbox : hboxList) {
            hbox.setOnMouseClicked(this::handleHBoxClick);
        }

        BTthemHP.setOnAction(event -> {
            try {
                HienThiCT("/app/doan/ThemHP.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ContextMenu setContextMenu(List<String> menu){
        ContextMenu Menu = new ContextMenu();
        int i = 0;
        for(String s: menu){
            MenuItem a = new MenuItem(s);
            a.setStyle("-fx-text-fill: #333333;");
            Menu.getItems().add(a);
        }
        return Menu;
    }

    @FXML
    private void handleButtonA(ActionEvent event) {
        if(LBdate.getText().equals("Lịch trống")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng chọn ngày thực hiện!");
            alert.showAndWait();
        }else{
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
            TFthem.setText(null);
        }
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
        TreeItem<HienThi> root = new TreeItem<>();

        for (DTO_HocPhan hocPhan : hocPhanList) {
            TreeItem<HienThi> hocPhanNode = new TreeItem<>(hocPhan);
            hocPhanNode.setExpanded(false);
            root.getChildren().add(hocPhanNode);

            for (DTO_Chuong chuong : bllc.timtheohocphan(hocPhan.getMaHP())) {
                TreeItem<HienThi> chuongNode = new TreeItem<>(chuong);
                chuongNode.setExpanded(false);
                hocPhanNode.getChildren().add(chuongNode);

                for (DTO_BaiHoc baiHoc : bllbh.timtheochuong(chuong.getMaChuong())) {
                    TreeItem<HienThi> baiHocNode = new TreeItem<>(baiHoc);
                    chuongNode.getChildren().add(baiHocNode);
                }
            }
        }

        treeView.setRoot(root);
        treeView.setShowRoot(false);

        treeView.setCellFactory(tv -> {
            TreeCell<HienThi> cell = new TreeCell<HienThi>() {
                @Override
                protected void updateItem(HienThi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        setContextMenu(null);
                        setOnMouseClicked(null); // Loại bỏ listener click chuột trái cũ
                    } else {
                        setText(item.getDisplayName());
                        setGraphic(null);

                        TreeItem<HienThi> currentTreeItem = getTreeItem();
                        if (currentTreeItem != null) {
                            ContextMenu contextMenu = createContextMenuForTreeItem(currentTreeItem);
                            setOnMouseClicked(event -> {
                                if (event.getButton() == MouseButton.SECONDARY) {
                                    setContextMenu(contextMenu);
                                    contextMenu.show(this, event.getScreenX(), event.getScreenY());
                                } else {
                                    setContextMenu(null);
                                }
                            });
                        } else {
                            setContextMenu(null);
                            setOnMouseClicked(null);
                        }

                        if (item instanceof DTO_HocPhan) {
                            setFont(Font.font(20));
                        } else if (item instanceof DTO_Chuong) {
                            setFont(Font.font(18));
                        } else if (item instanceof DTO_BaiHoc) {
                            setFont(Font.font(16));
                        }
                    }
                }

                private ContextMenu createContextMenuForTreeItem(TreeItem<HienThi> treeItem) {
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem themItem = null;
                    MenuItem xoaItem = new MenuItem("Xóa");
                    MenuItem hoanThanhItem = new MenuItem("Hoàn thành");
                    MenuItem chiTietItem = new MenuItem("Hiển thị chi tiết");

                    HienThi itemValue = treeItem.getValue();

                    if (itemValue instanceof DTO_HocPhan) {
                        themItem = new MenuItem("Thêm chương");
                    } else if (itemValue instanceof DTO_Chuong) {
                        themItem = new MenuItem("Thêm bài học");
                    }

                    if (themItem != null) {
                        contextMenu.getItems().add(themItem);
                        themItem.setOnAction(event -> {
                            if (itemValue instanceof DTO_HocPhan) {
                                try {
                                    mahienthi = ((DTO_HocPhan) itemValue).getMaHP();
                                    HienThiCT("/app/doan/ThemC.fxml");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else{
                                mahienthi = ((DTO_Chuong) itemValue).getMaChuong();
                                try {
                                    HienThiCT("/app/doan/ThemBH.fxml");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }

                    if (itemValue instanceof DTO_BaiHoc) {
                        MenuItem dsCongViecItem = new MenuItem("Danh sách công việc");
                        contextMenu.getItems().add(dsCongViecItem);
                        dsCongViecItem.setOnAction(event -> {
                            mahienthi = ((DTO_BaiHoc) itemValue).getMaBH();
                            bllcv.dscv();
                            observableList.setAll(dscvbh);
                        });
                    }

                    xoaItem.setOnAction(event -> {
                        String txt;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Xác nhận");
                        alert.setHeaderText("Xác nhận xóa?");
                        if(itemValue instanceof DTO_HocPhan) {
                            txt = "Tất cả các chương, bài học và công việc liên quan cũng sẽ bị xóa";
                        }else if(itemValue instanceof DTO_Chuong){
                            txt = "Tất cả các bài học và công việc liên quan cũng sẽ bị xóa";
                        }else{
                            txt = "Tất cả các công việc liên quan cũng sẽ bị xóa";
                        }
                        alert.setContentText(txt);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK){
                            if (itemValue instanceof DTO_HocPhan) {
                                bllhp.xoa((DTO_HocPhan) itemValue);
                                bllhp.tailist();
                                bllc.tailist();
                                bllbh.tailist();
                                setupTreeView(hpList);
                            }else if (itemValue instanceof DTO_Chuong) {
                                bllc.xoa((DTO_Chuong) itemValue);
                                bllhp.tailist();
                                bllc.tailist();
                                bllbh.tailist();
                                setupTreeView(hpList);
                            }else {
                                assert itemValue instanceof DTO_BaiHoc;
                                bllbh.xoa((DTO_BaiHoc) itemValue);
                                bllhp.tailist();
                                bllc.tailist();
                                bllbh.tailist();
                                loadListView(1);
                            }
                        }
                    });

                    hoanThanhItem.setOnAction(event -> {
                        if (itemValue instanceof DTO_HocPhan) {
                            ((DTO_HocPhan) itemValue).setTrangThai(true);
                        }else if (itemValue instanceof DTO_Chuong) {
                            ((DTO_Chuong) itemValue).setTrangThai(true);
                        }else {
                            assert itemValue instanceof DTO_BaiHoc;
                            ((DTO_BaiHoc) itemValue).setTrangThai(true);
                        }
                    });

                    chiTietItem.setOnAction(event -> {
                        // Xử lý logic hiển thị chi tiết
                        if (itemValue instanceof DTO_HocPhan) {
                            DTO_HocPhan hp = (DTO_HocPhan) itemValue;
                            try {
                                mahienthi = hp.getMaHP();
                                HienThiCT("/app/doan/CTHP.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (itemValue instanceof DTO_Chuong) {
                            DTO_Chuong c = (DTO_Chuong) itemValue;
                            try {
                                mahienthi = c.getMaChuong();
                                HienThiCT("/app/doan/CTC.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (itemValue instanceof DTO_BaiHoc) {
                            DTO_BaiHoc bh = (DTO_BaiHoc) itemValue;
                            try {
                                mahienthi = bh.getMaBH();
                                HienThiCT("/app/doan/CTBH.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    contextMenu.getItems().addAll(xoaItem, hoanThanhItem, chiTietItem);
                    return contextMenu;
                }
            };
            return cell;
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
        loadListView(list);
        bllhp.tailist();
        bllc.tailist();
        bllbh.tailist();
        setupTreeView(hpList);
    }
}
