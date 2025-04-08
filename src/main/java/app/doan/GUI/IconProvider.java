package app.doan.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IconProvider {

    private static final Map<String, Image> iconMap = new HashMap<>();

    static {
        load("checked_default", "/app/doan/image/check_box.png");
        load("checked_red", "/app/doan/image/check_boxred.png");
        load("checked_green", "/app/doan/image/check_boxgreen.png");
        load("checked_orange", "/app/doan/image/check_boxorange.png");
        load("unchecked_default", "/app/doan/image/check_box_outline_blank.png");
        load("unchecked_red", "/app/doan/image/checkboxred.png");
        load("unchecked_green", "/app/doan/image/checkboxgreen.png");
        load("unchecked_orange", "/app/doan/image/checkboxorange.png");
    }

    private static void load(String key, String path) {
        iconMap.put(key, new Image(Objects.requireNonNull(IconProvider.class.getResourceAsStream(path))));
    }

    public static Image getImage(String key) {
        return iconMap.get(key);
    }

    public static ImageView getIcon(String key, double width, double height) {
        ImageView view = new ImageView(getImage(key));
        view.setFitWidth(width);
        view.setFitHeight(height);
        return view;
    }

    public static ImageView getIcon(String key) {
        return getIcon(key, 40, 40); // default size
    }
}
