import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * Shared UI utility used by all pane classes.
 */
public class UIUtil {

    public static GridPane formGrid() {
        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(12);
        g.setPadding(new Insets(4, 0, 14, 0));
        return g;
    }

    public static <T> ComboBox<T> styledCombo() {
        ComboBox<T> cb = new ComboBox<>();
        cb.setStyle(
            "-fx-background-color:#1e2235; -fx-text-fill:#e2e8f0;" +
            "-fx-border-color:#2d3561; -fx-font-size:13;"
        );
        cb.setPrefWidth(280);
        return cb;
    }
}
