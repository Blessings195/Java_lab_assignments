import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class TableBuilder {

    // ── Restaurant TableView ──────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public static TableView<Restaurant> restaurantTable() {
        TableView<Restaurant> tv = new TableView<>();
        tv.setStyle(
        "-fx-background-color:#12152a; -fx-table-cell-border-color:#1e2235;"
        );
        tv.setRowFactory(r -> {
        TableRow<Restaurant> row = new TableRow<>();
        row.setStyle("-fx-text-fill: #ffffff; -fx-font-size:13;");
        return row;
        });
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Restaurant, Integer> colId   = col("ID",      "id",      80);
        TableColumn<Restaurant, String>  colName = col("Name",    "name",    200);
        TableColumn<Restaurant, String>  colAddr = col("Address", "address", 400);

        styleCol(colId); styleCol(colName); styleCol(colAddr);
        tv.getColumns().addAll(colId, colName, colAddr);
        return tv;
    }

    public static void loadRestaurants(TableView<Restaurant> tv, List<Restaurant> list) {
        ObservableList<Restaurant> data = FXCollections.observableArrayList(list);
        tv.setItems(data);
    }

    // ── MenuItem TableView ───────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public static TableView<FoodItem> menuItemTable() {
        TableView<FoodItem> tv = new TableView<>();
        tv.setRowFactory(r -> {
        TableRow<FoodItem> row = new TableRow<>();
        row.setStyle("-fx-text-fill: #000000; -fx-font-size:13;");
        return row;
        });
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<FoodItem, Integer> colId    = col("ID",          "id",             70);
        TableColumn<FoodItem, String>  colName  = col("Name",        "name",           180);
        TableColumn<FoodItem, Double>  colPrice = col("Price (₹)",   "price",          120);
        TableColumn<FoodItem, Integer> colResId = col("ResID",       "resId",          70);
        TableColumn<FoodItem, String>  colRest  = col("Restaurant",  "restaurantName", 200);

        styleCol(colId); styleCol(colName); styleCol(colPrice); styleCol(colResId); styleCol(colRest);
        tv.getColumns().addAll(colId, colName, colPrice, colResId, colRest);
        return tv;
    }

    public static void loadMenuItems(TableView<FoodItem> tv, List<FoodItem> list) {
        ObservableList<FoodItem> data = FXCollections.observableArrayList(list);
        tv.setItems(data);
    }

    // ── helpers ──────────────────────────────────────────────────────────
    private static <S, T> TableColumn<S, T> col(String header, String property, double width) {
        TableColumn<S, T> c = new TableColumn<>(header);
        c.setCellValueFactory(new PropertyValueFactory<>(property));
        c.setPrefWidth(width);
        return c;
    }

    private static <S, T> void styleCol(TableColumn<S, T> c) {
        c.setStyle("-fx-text-fill:#e2e8f0; -fx-alignment:CENTER_LEFT; -fx-font-size:13;");
    }
}
