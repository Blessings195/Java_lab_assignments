import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BorderPane root;
    private Label      statusBar;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #0f1117;");

        // ── TOP: header + menu bar ──────────────────────────────
        root.setTop(buildTopBar());

        // ── CENTER: welcome splash ──────────────────────────────
        root.setCenter(buildSplash());

        // ── BOTTOM: status bar ─────────────────────────────────
        statusBar = new Label("Ready  |  Connected");
        statusBar.setStyle(
            "-fx-background-color:#1a1d2e; -fx-text-fill:#6ee7b7;" +
            "-fx-font-family:'Courier New'; -fx-font-size:12;" +
            "-fx-padding:6 16 6 16;"
        );
        statusBar.setMaxWidth(Double.MAX_VALUE);
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 1100, 720);

        scene.getStylesheets().add("data:text/css," +
        ".menu-bar { -fx-background-color: #1a1d2e; }" +
        ".menu-bar .menu .label { -fx-text-fill: #ffffff; -fx-font-size: 13px; }" +
        ".menu-item .label { -fx-text-fill: #ffffff; -fx-font-size: 12px; }" +
        ".menu-item:focused { -fx-background-color: #2d3561; }" +
        ".context-menu { -fx-background-color: #1a1d2e; }" +
        ".table-view .table-cell { -fx-text-fill: #ffffff; -fx-font-size: 13px; }" +
        ".table-view .column-header .label { -fx-text-fill: #6ee7b7; -fx-font-size: 13px; -fx-font-weight: bold; }" +
        ".table-view .column-header-background { -fx-background-color: #1a1d2e; }"
        );
        stage.setTitle("🍽  Restaurant Manager — JDBC + JavaFX");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> { DBConnection.closeConnection(); Platform.exit(); });
        stage.show();
    }

    // ────────────────────────────────────────────
    //  TOP BAR  (app title  +  MenuBar)
    // ────────────────────────────────────────────
    private VBox buildTopBar() {
        // ── App title strip ──
        Label title = new Label("🍽  RESTAURANT MANAGER");
        title.setStyle(
            "-fx-text-fill:#f0fdf4; -fx-font-size:22; -fx-font-weight:bold;" +
            "-fx-font-family:'Georgia';"
        );
        Label sub = new Label("JavaFX  ·  JDBC  ·  MySQL");
        sub.setStyle("-fx-text-fill:#6ee7b7; -fx-font-size:11; -fx-font-family:'Courier New';");

        VBox titleBox = new VBox(2, title, sub);
        titleBox.setPadding(new Insets(12, 20, 8, 20));
        titleBox.setStyle("-fx-background-color:#161b2e;");

        // ── MenuBar ──
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle(
        "-fx-background-color:#1a1d2e;" +
        "-fx-border-color:#2d3561 transparent transparent transparent;"
        );
        menuBar.getStylesheets().clear();

        // ─── Restaurant menu ───
        Menu mRestaurant = styledMenu("🏠  Restaurant");

        javafx.scene.control.MenuItem miViewAll    = styledItem("📋  View All Restaurants");
        javafx.scene.control.MenuItem miInsert     = styledItem("➕  Add Restaurant");
        javafx.scene.control.MenuItem miUpdate     = styledItem("✏️   Update Restaurant");
        javafx.scene.control.MenuItem miDelete     = styledItem("🗑   Delete Restaurant");

        miViewAll.setOnAction(e -> showPane(new RestaurantViewPane(this)));
        miInsert .setOnAction(e -> showPane(new RestaurantInsertPane(this)));
        miUpdate .setOnAction(e -> showPane(new RestaurantUpdatePane(this)));
        miDelete .setOnAction(e -> showPane(new RestaurantDeletePane(this)));

        mRestaurant.getItems().addAll(miViewAll, new SeparatorMenuItem(),
                                      miInsert, miUpdate, miDelete);

        // ─── MenuItem menu ───
        Menu mMenu = styledMenu("🍕  Menu Items");

        javafx.scene.control.MenuItem miViewMenu    = styledItem("📋  View All Items");
        javafx.scene.control.MenuItem miFilterPrice = styledItem("💰  Filter by Max Price");
        javafx.scene.control.MenuItem miFilterRest  = styledItem("🏠  Filter by Restaurant");
        javafx.scene.control.MenuItem miInsertItem  = styledItem("➕  Add Menu Item");
        javafx.scene.control.MenuItem miUpdateItem  = styledItem("✏️   Update Menu Item");
        javafx.scene.control.MenuItem miUpdateBulk  = styledItem("🔄  Bulk Price Update");
        javafx.scene.control.MenuItem miDeleteItem  = styledItem("🗑   Delete by ID");
        javafx.scene.control.MenuItem miDeletePfx   = styledItem("🔤  Delete by Name Prefix");

        miViewMenu   .setOnAction(e -> showPane(new MenuItemViewPane(this, "all", null, 0)));
        miFilterPrice.setOnAction(e -> showPane(new MenuItemFilterPricePane(this)));
        miFilterRest .setOnAction(e -> showPane(new MenuItemFilterRestPane(this)));
        miInsertItem .setOnAction(e -> showPane(new MenuItemInsertPane(this)));
        miUpdateItem .setOnAction(e -> showPane(new MenuItemUpdatePane(this)));
        miUpdateBulk .setOnAction(e -> showPane(new MenuItemBulkUpdatePane(this)));
        miDeleteItem .setOnAction(e -> showPane(new MenuItemDeletePane(this)));
        miDeletePfx  .setOnAction(e -> showPane(new MenuItemDeletePrefixPane(this)));

        mMenu.getItems().addAll(
            miViewMenu, miFilterPrice, miFilterRest,
            new SeparatorMenuItem(),
            miInsertItem, miUpdateItem, miUpdateBulk,
            new SeparatorMenuItem(),
            miDeleteItem, miDeletePfx
        );

        // ─── Help menu ───
        Menu mHelp = styledMenu("❓  Help");
        javafx.scene.control.MenuItem miAbout = styledItem("ℹ️  About");
        miAbout.setOnAction(e -> showAbout());
        mHelp.getItems().add(miAbout);

        menuBar.getMenus().addAll(mRestaurant, mMenu, mHelp);

        return new VBox(titleBox, menuBar);
    }

    // ────────────────────────────────────────────
    //  SPLASH / WELCOME SCREEN
    // ────────────────────────────────────────────
    private VBox buildSplash() {
        Label icon = new Label("🍽");
        icon.setStyle("-fx-font-size:72;");

        Label h = new Label("Welcome to Restaurant Manager");
        h.setStyle("-fx-text-fill:#f0fdf4; -fx-font-size:28; -fx-font-family:'Georgia'; -fx-font-weight:bold;");

        Label p = new Label("Use the menu bar above to perform CRUD operations on\nRestaurant and MenuItem tables.");
        p.setStyle("-fx-text-fill:#94a3b8; -fx-font-size:14; -fx-text-alignment:center;");
        p.setTextAlignment(TextAlignment.CENTER);

        // Quick-access buttons
        Button btnViewRest = quickBtn("📋 View Restaurants",  "#22c55e");
        Button btnViewMenu = quickBtn("🍕 View Menu Items",   "#3b82f6");
        Button btnAdd      = quickBtn("➕ Add Menu Item",     "#f59e0b");

        btnViewRest.setOnAction(e -> showPane(new RestaurantViewPane(this)));
        btnViewMenu.setOnAction(e -> showPane(new MenuItemViewPane(this, "all", null, 0)));
        btnAdd     .setOnAction(e -> showPane(new MenuItemInsertPane(this)));

        HBox btns = new HBox(14, btnViewRest, btnViewMenu, btnAdd);
        btns.setAlignment(Pos.CENTER);

        VBox box = new VBox(18, icon, h, p, btns);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60));
        return box;
    }

    // ────────────────────────────────────────────
    //  PUBLIC HELPERS used by child panes
    // ────────────────────────────────────────────
    public void showPane(Region pane) {
        root.setCenter(pane);
    }

    public void setStatus(String msg) {
        statusBar.setText(msg);
    }

    // ── Styling helpers ──────────────────────────
    static Menu styledMenu(String text) {
        Menu m = new Menu(text);
        m.setStyle("-fx-text-fill:#e2e8f0; -fx-font-size:13;");
        return m;
    }

    static javafx.scene.control.MenuItem styledItem(String text) {
        javafx.scene.control.MenuItem mi = new javafx.scene.control.MenuItem(text);
        mi.setStyle("-fx-text-fill:#e2e8f0; -fx-font-size:12; -fx-background-color:#1a1d2e;");
        return mi;
    }

    static Button quickBtn(String text, String color) {
        Button b = new Button(text);
        b.setStyle(
            "-fx-background-color:" + color + "22; -fx-text-fill:" + color + ";" +
            "-fx-border-color:" + color + "; -fx-border-radius:6; -fx-background-radius:6;" +
            "-fx-font-size:13; -fx-padding:10 22 10 22; -fx-cursor:hand;"
        );
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color:" + color + "; -fx-text-fill:#0f1119;" +
            "-fx-border-color:" + color + "; -fx-border-radius:6; -fx-background-radius:6;" +
            "-fx-font-size:13; -fx-padding:10 22 10 22; -fx-cursor:hand;"
        ));
        b.setOnMouseExited(e -> b.setStyle(
            "-fx-background-color:" + color + "22; -fx-text-fill:" + color + ";" +
            "-fx-border-color:" + color + "; -fx-border-radius:6; -fx-background-radius:6;" +
            "-fx-font-size:13; -fx-padding:10 22 10 22; -fx-cursor:hand;"
        ));
        return b;
    }

    static Label sectionTitle(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill:#f0fdf4; -fx-font-size:20; -fx-font-family:'Georgia'; -fx-font-weight:bold;");
        return l;
    }

    static Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill:#94a3b8; -fx-font-size:12;");
        return l;
    }

    static TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(
            "-fx-background-color:#1e2235; -fx-text-fill:#e2e8f0;" +
            "-fx-border-color:#2d3561; -fx-border-radius:4; -fx-background-radius:4;" +
            "-fx-font-size:13; -fx-padding:8;"
        );
        tf.setPrefWidth(280);
        return tf;
    }

    static Button actionBtn(String text, String color) {
        return quickBtn(text, color);
    }

    static Label alertLabel() {
        Label l = new Label();
        l.setStyle("-fx-font-size:12; -fx-font-family:'Courier New';");
        l.setWrapText(true);
        return l;
    }

    static void setAlert(Label l, boolean success, String msg) {
        l.setStyle("-fx-font-size:12; -fx-font-family:'Courier New'; -fx-text-fill:" + (success ? "#6ee7b7" : "#f87171") + ";");
        l.setText((success ? "✔  " : "✘  ") + msg);
    }

    static VBox cardPane(String title) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(30, 36, 30, 36));
        card.setStyle("-fx-background-color:#0f1117;");
        card.getChildren().add(sectionTitle(title));
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color:#2d3561;");
        card.getChildren().add(sep);
        return card;
    }

    // ────────────────────────────────────────────
    private void showAbout() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("About");
        a.setHeaderText("Restaurant Manager v1.0");
        a.setContentText("JavaFX + JDBC + MySQL\nCRUD Operations on Restaurant & MenuItem tables.\n\nBuilt for Java Lab Assignment.");
        a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
