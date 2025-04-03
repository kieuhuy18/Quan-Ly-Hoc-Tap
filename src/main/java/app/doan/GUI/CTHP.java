package app.doan.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CTHP {
    @FXML
    private StackPane container;

    @FXML
    private TextField TXTtieude;

    @FXML
    private Label displayLabel;

    @FXML
    private ImageView IMclose;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize(){
        // Ban đầu chỉ hiển thị Label, ẩn TextField
        TXTtieude.setVisible(false);
        TXTtieude.setText("Huy ne");
        displayLabel.setText(TXTtieude.getText());

        displayLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                enableEditing();
            }
        });

        TXTtieude.setOnAction(event -> saveText());
        TXTtieude.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveText();
        });

        IMclose.setOnMouseClicked(event -> {
            Stage stage = (Stage) IMclose.getScene().getWindow();
            stage.close();
        });
    }

    private void enableEditing() {
        TXTtieude.setText(displayLabel.getText());
        TXTtieude.setVisible(true);
        displayLabel.setVisible(false);
        TXTtieude.requestFocus();
        TXTtieude.selectAll();
    }

    private void saveText() {
        displayLabel.setText(TXTtieude.getText().trim().isEmpty() ? "Nhấn đúp để chỉnh sửa" : TXTtieude.getText());
        TXTtieude.setVisible(false);
        displayLabel.setVisible(true);
    }
}
