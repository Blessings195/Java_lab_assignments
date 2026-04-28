import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.SQLException;
import java.util.List;

// ═══════════════════════════════════════════════════════════════════════
//  VIEW / FILTERED  (mode: "all" | "price" | "restaurant")
// ═══════════════════════════════════════════════════════════════════════
class MenuItemViewPane extends VBox {
    public MenuItemViewPane(MainApp app, String mode, String filter, double price) {
        VBox card = MainApp.cardPane(heading(mode, filter, price));
        TableView<FoodItem> tv = TableBuilder.menuItemTable();
        VBox.setVgrow(tv, Priority.ALWAYS);
        try {
            MenuItemDAO dao = new MenuItemDAO();
            List<FoodItem> list = switch (mode) {
                case "price"      -> dao.getMenuItemsByMaxPrice(price);
                case "restaurant" -> dao.getMenuItemsByRestaurantName(filter);
                default           -> dao.getAllMenuItems();
            };
            TableBuilder.loadMenuItems(tv, list);
            app.setStatus("✔  " + list.size() + " item(s) loaded.");
        } catch (SQLException e) { app.setStatus("✘  " + e.getMessage()); }

        Button btnRefresh = MainApp.actionBtn("🔄 Refresh", "#3b82f6");
        btnRefresh.setOnAction(e -> app.showPane(new MenuItemViewPane(app, mode, filter, price)));
        card.getChildren().addAll(tv, btnRefresh);
        getChildren().add(card);
        VBox.setVgrow(card, Priority.ALWAYS);
        setFillWidth(true);
    }
    private static String heading(String mode, String filter, double price) {
        return switch (mode) {
            case "price"      -> "💰  Items — Price ≤ ₹" + (int) price;
            case "restaurant" -> "🏠  Items at — " + filter;
            default           -> "🍕  All Menu Items";
        };
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  FILTER BY MAX PRICE
// ═══════════════════════════════════════════════════════════════════════
class MenuItemFilterPricePane extends VBox {
    public MenuItemFilterPricePane(MainApp app) {
        VBox card = MainApp.cardPane("💰  Filter by Max Price");
        TextField tfPrice = MainApp.styledField("e.g. 100");
        Label alertLbl = MainApp.alertLabel();
        Button btnGo = MainApp.actionBtn("🔍 Search", "#22c55e");
        btnGo.setOnAction(e -> {
            try { app.showPane(new MenuItemViewPane(app, "price", null, Double.parseDouble(tfPrice.getText().trim()))); }
            catch (NumberFormatException ex) { MainApp.setAlert(alertLbl, false, "Enter a valid number."); }
        });
        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Max Price (₹)"), tfPrice, btnGo);
        card.getChildren().addAll(g, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  FILTER BY RESTAURANT NAME
// ═══════════════════════════════════════════════════════════════════════
class MenuItemFilterRestPane extends VBox {
    public MenuItemFilterRestPane(MainApp app) {
        VBox card = MainApp.cardPane("🏠  Filter by Restaurant");
        ComboBox<Restaurant> cb = UIUtil.<Restaurant>styledCombo();
        try { cb.setItems(FXCollections.observableArrayList(new RestaurantDAO().getAllRestaurants())); }
        catch (SQLException e) { app.setStatus("✘  " + e.getMessage()); }
        Label alertLbl = MainApp.alertLabel();
        Button btnGo = MainApp.actionBtn("🔍 Search", "#22c55e");
        btnGo.setOnAction(e -> {
            if (cb.getValue() == null) { MainApp.setAlert(alertLbl, false, "Select a restaurant."); return; }
            app.showPane(new MenuItemViewPane(app, "restaurant", cb.getValue().getName(), 0));
        });
        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Restaurant"), cb, btnGo);
        card.getChildren().addAll(g, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  INSERT MENU ITEM
// ═══════════════════════════════════════════════════════════════════════
class MenuItemInsertPane extends VBox {
    public MenuItemInsertPane(MainApp app) {
        VBox card = MainApp.cardPane("➕  Add Menu Item");
        TextField tfName = MainApp.styledField("e.g. Margherita Pizza");
        TextField tfPrice = MainApp.styledField("e.g. 150");
        ComboBox<Restaurant> cbRest = UIUtil.<Restaurant>styledCombo();
        try { cbRest.setItems(FXCollections.observableArrayList(new RestaurantDAO().getAllRestaurants())); }
        catch (SQLException e) { app.setStatus("✘  " + e.getMessage()); }
        Label alertLbl = MainApp.alertLabel();
        Button btnSave = MainApp.actionBtn("💾  Save", "#22c55e");

        btnSave.setOnAction(e -> {
            String name = tfName.getText().trim();
            Restaurant sel = cbRest.getValue();
            if (name.isEmpty() || tfPrice.getText().isBlank() || sel == null) {
                MainApp.setAlert(alertLbl, false, "All fields required."); return;
            }
            try {
                new MenuItemDAO().insertMenuItem(name, Double.parseDouble(tfPrice.getText().trim()), sel.getId());
                MainApp.setAlert(alertLbl, true, "'" + name + "' added to " + sel.getName() + ".");
                tfName.clear(); tfPrice.clear(); cbRest.setValue(null);
                app.setStatus("✔  Menu item inserted.");
            } catch (NumberFormatException nfe) { MainApp.setAlert(alertLbl, false, "Price must be a number.");
            } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Name"),       tfName);
        g.addRow(1, MainApp.fieldLabel("Price (₹)"),  tfPrice);
        g.addRow(2, MainApp.fieldLabel("Restaurant"), cbRest);
        card.getChildren().addAll(g, btnSave, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  UPDATE SINGLE MENU ITEM
// ═══════════════════════════════════════════════════════════════════════
class MenuItemUpdatePane extends VBox {
    public MenuItemUpdatePane(MainApp app) {
        VBox card = MainApp.cardPane("✏️  Update Menu Item");
        TextField tfId = MainApp.styledField("Item ID");
        TextField tfName = MainApp.styledField("New name");
        TextField tfPrice = MainApp.styledField("New price");
        ComboBox<Restaurant> cbRest = UIUtil.<Restaurant>styledCombo();
        tfName.setDisable(true); tfPrice.setDisable(true); cbRest.setDisable(true);
        try { cbRest.setItems(FXCollections.observableArrayList(new RestaurantDAO().getAllRestaurants())); }
        catch (SQLException e) { app.setStatus("✘  " + e.getMessage()); }
        Label alertLbl = MainApp.alertLabel();
        Button btnLoad = MainApp.actionBtn("🔍 Load", "#f59e0b");
        Button btnUpd  = MainApp.actionBtn("✏️ Update", "#3b82f6");
        btnUpd.setDisable(true);

        btnLoad.setOnAction(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                FoodItem found = new MenuItemDAO().getAllMenuItems().stream()
                    .filter(m -> m.getId() == id).findFirst().orElse(null);
                if (found == null) { MainApp.setAlert(alertLbl, false, "ID not found."); return; }
                tfName.setText(found.getName()); tfPrice.setText(String.valueOf(found.getPrice()));
                cbRest.getItems().stream().filter(r -> r.getId() == found.getResId())
                    .findFirst().ifPresent(cbRest::setValue);
                tfName.setDisable(false); tfPrice.setDisable(false); cbRest.setDisable(false); btnUpd.setDisable(false);
                MainApp.setAlert(alertLbl, true, "Loaded — edit and click Update.");
            } catch (NumberFormatException nfe) { MainApp.setAlert(alertLbl, false, "ID must be a number.");
            } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        btnUpd.setOnAction(e -> {
            try {
                Restaurant sel = cbRest.getValue();
                if (sel == null) { MainApp.setAlert(alertLbl, false, "Select a restaurant."); return; }
                new MenuItemDAO().updateMenuItem(Integer.parseInt(tfId.getText().trim()),
                    tfName.getText().trim(), Double.parseDouble(tfPrice.getText().trim()), sel.getId());
                MainApp.setAlert(alertLbl, true, "Updated."); app.setStatus("✔  Menu item updated.");
            } catch (Exception ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("ID"),         tfId,    btnLoad);
        g.addRow(1, MainApp.fieldLabel("Name"),       tfName);
        g.addRow(2, MainApp.fieldLabel("Price (₹)"),  tfPrice);
        g.addRow(3, MainApp.fieldLabel("Restaurant"), cbRest,  btnUpd);
        card.getChildren().addAll(g, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  BULK PRICE UPDATE  (all items where price <= threshold → new price)
// ═══════════════════════════════════════════════════════════════════════
class MenuItemBulkUpdatePane extends VBox {
    public MenuItemBulkUpdatePane(MainApp app) {
        VBox card = MainApp.cardPane("🔄  Bulk Price Update");
        Label desc = new Label("Set a new price for all items whose current price is ≤ threshold.");
        desc.setStyle("-fx-text-fill:#94a3b8; -fx-font-size:12;");
        TextField tfThreshold = MainApp.styledField("e.g. 100");
        TextField tfNewPrice  = MainApp.styledField("e.g. 200");
        Label alertLbl = MainApp.alertLabel();
        Button btnGo = MainApp.actionBtn("🔄  Apply", "#f59e0b");

        btnGo.setOnAction(e -> {
            try {
                double threshold = Double.parseDouble(tfThreshold.getText().trim());
                double newPrice  = Double.parseDouble(tfNewPrice.getText().trim());
                new Alert(Alert.AlertType.CONFIRMATION,
                    "Set price = ₹" + (int)newPrice + " for all items with price ≤ ₹" + (int)threshold + "?",
                    ButtonType.YES, ButtonType.CANCEL).showAndWait().ifPresent(b -> {
                    if (b == ButtonType.YES) {
                        try {
                            int rows = new MenuItemDAO().updatePriceWhereBelow(threshold, newPrice);
                            MainApp.setAlert(alertLbl, true, rows + " record(s) updated.");
                            app.setStatus("✔  Bulk update: " + rows + " row(s).");
                        } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
                    }
                });
            } catch (NumberFormatException nfe) { MainApp.setAlert(alertLbl, false, "Both fields must be numbers."); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Price Threshold (≤)"), tfThreshold);
        g.addRow(1, MainApp.fieldLabel("New Price"),           tfNewPrice);
        card.getChildren().addAll(desc, g, btnGo, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  DELETE BY ID
// ═══════════════════════════════════════════════════════════════════════
class MenuItemDeletePane extends VBox {
    public MenuItemDeletePane(MainApp app) {
        VBox card = MainApp.cardPane("🗑  Delete Menu Item by ID");
        TextField tfId = MainApp.styledField("Item ID");
        Label alertLbl = MainApp.alertLabel();
        Button btnDel  = MainApp.actionBtn("🗑  Delete", "#ef4444");

        btnDel.setOnAction(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                new Alert(Alert.AlertType.CONFIRMATION, "Delete item ID " + id + "?", ButtonType.YES, ButtonType.CANCEL)
                    .showAndWait().ifPresent(b -> { if (b == ButtonType.YES) {
                        try {
                            boolean ok = new MenuItemDAO().deleteMenuItemById(id);
                            if (ok) { MainApp.setAlert(alertLbl, true, "Deleted ID " + id); tfId.clear(); app.setStatus("✔  Deleted."); }
                            else MainApp.setAlert(alertLbl, false, "ID not found.");
                        } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
                    }});
            } catch (NumberFormatException nfe) { MainApp.setAlert(alertLbl, false, "ID must be a number."); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("ID"), tfId, btnDel);
        card.getChildren().addAll(g, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  DELETE WHERE NAME STARTS WITH PREFIX
// ═══════════════════════════════════════════════════════════════════════
class MenuItemDeletePrefixPane extends VBox {
    public MenuItemDeletePrefixPane(MainApp app) {
        VBox card = MainApp.cardPane("🔤  Delete by Name Prefix");
        Label desc = new Label("Deletes all items whose name starts with the given letter / prefix (e.g. \"P\" removes Pasta, Pizza, Paneer…).");
        desc.setStyle("-fx-text-fill:#94a3b8; -fx-font-size:12;"); desc.setWrapText(true);
        TextField tfPrefix = MainApp.styledField("e.g. P");
        Label alertLbl = MainApp.alertLabel();
        Button btnDel  = MainApp.actionBtn("🗑  Delete Matching", "#ef4444");

        btnDel.setOnAction(e -> {
            String pfx = tfPrefix.getText().trim();
            if (pfx.isEmpty()) { MainApp.setAlert(alertLbl, false, "Enter a prefix."); return; }
            new Alert(Alert.AlertType.CONFIRMATION, "Delete all items starting with '" + pfx + "'?",
                ButtonType.YES, ButtonType.CANCEL).showAndWait().ifPresent(b -> { if (b == ButtonType.YES) {
                    try {
                        int rows = new MenuItemDAO().deleteMenuItemsByPrefix(pfx);
                        MainApp.setAlert(alertLbl, true, rows + " item(s) deleted."); tfPrefix.clear(); app.setStatus("✔  " + rows + " item(s) deleted.");
                    } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
                }});
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Name Prefix"), tfPrefix, btnDel);
        card.getChildren().addAll(desc, g, alertLbl);
        getChildren().add(card);
    }
}


