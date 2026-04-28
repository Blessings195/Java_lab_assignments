import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.SQLException;
import java.util.List;

// ═══════════════════════════════════════════════════════════════════════
//  VIEW ALL RESTAURANTS
// ═══════════════════════════════════════════════════════════════════════
class RestaurantViewPane extends VBox {
    public RestaurantViewPane(MainApp app) {
        VBox card = MainApp.cardPane("📋  All Restaurants");
        TableView<Restaurant> tv = TableBuilder.restaurantTable();
        VBox.setVgrow(tv, Priority.ALWAYS);
        try {
            List<Restaurant> list = new RestaurantDAO().getAllRestaurants();
            TableBuilder.loadRestaurants(tv, list);
            app.setStatus("✔  Loaded " + list.size() + " restaurant(s).");
        } catch (SQLException e) { app.setStatus("✘  " + e.getMessage()); }

        Button btnRefresh = MainApp.actionBtn("🔄 Refresh", "#3b82f6");
        btnRefresh.setOnAction(e -> app.showPane(new RestaurantViewPane(app)));
        card.getChildren().addAll(tv, btnRefresh);
        getChildren().add(card);
        VBox.setVgrow(card, Priority.ALWAYS);
        setFillWidth(true);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  INSERT RESTAURANT
// ═══════════════════════════════════════════════════════════════════════
class RestaurantInsertPane extends VBox {
    public RestaurantInsertPane(MainApp app) {
        VBox card   = MainApp.cardPane("➕  Add Restaurant");
        TextField tfName    = MainApp.styledField("e.g. Cafe Sunrise");
        TextField tfAddress = MainApp.styledField("e.g. 12 MG Road, Pune");
        Label alertLbl = MainApp.alertLabel();
        Button btnSave = MainApp.actionBtn("💾  Save", "#22c55e");

        btnSave.setOnAction(e -> {
            String name = tfName.getText().trim(), addr = tfAddress.getText().trim();
            if (name.isEmpty() || addr.isEmpty()) { MainApp.setAlert(alertLbl, false, "All fields required."); return; }
            try {
                new RestaurantDAO().insertRestaurant(name, addr);
                MainApp.setAlert(alertLbl, true, "'" + name + "' added.");
                tfName.clear(); tfAddress.clear();
                app.setStatus("✔  Restaurant inserted.");
            } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("Name"),    tfName);
        g.addRow(1, MainApp.fieldLabel("Address"), tfAddress);
        card.getChildren().addAll(g, btnSave, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  UPDATE RESTAURANT
// ═══════════════════════════════════════════════════════════════════════
class RestaurantUpdatePane extends VBox {
    public RestaurantUpdatePane(MainApp app) {
        VBox card = MainApp.cardPane("✏️  Update Restaurant");
        TextField tfId = MainApp.styledField("ID"), tfName = MainApp.styledField("New name"), tfAddr = MainApp.styledField("New address");
        tfName.setDisable(true); tfAddr.setDisable(true);
        Label alertLbl = MainApp.alertLabel();
        Button btnLoad = MainApp.actionBtn("🔍 Load", "#f59e0b"), btnUpd = MainApp.actionBtn("✏️ Update", "#3b82f6");
        btnUpd.setDisable(true);

        btnLoad.setOnAction(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                Restaurant r = new RestaurantDAO().getRestaurantById(id);
                if (r == null) { MainApp.setAlert(alertLbl, false, "Not found."); return; }
                tfName.setText(r.getName()); tfAddr.setText(r.getAddress());
                tfName.setDisable(false); tfAddr.setDisable(false); btnUpd.setDisable(false);
                MainApp.setAlert(alertLbl, true, "Loaded — edit fields then click Update.");
            } catch (NumberFormatException nfe) { MainApp.setAlert(alertLbl, false, "ID must be a number.");
            } catch (SQLException ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        btnUpd.setOnAction(e -> {
            try {
                new RestaurantDAO().updateRestaurant(Integer.parseInt(tfId.getText().trim()), tfName.getText().trim(), tfAddr.getText().trim());
                MainApp.setAlert(alertLbl, true, "Updated."); app.setStatus("✔  Restaurant updated.");
            } catch (Exception ex) { MainApp.setAlert(alertLbl, false, ex.getMessage()); }
        });

        GridPane g = UIUtil.formGrid();
        g.addRow(0, MainApp.fieldLabel("ID"),      tfId,   btnLoad);
        g.addRow(1, MainApp.fieldLabel("Name"),    tfName);
        g.addRow(2, MainApp.fieldLabel("Address"), tfAddr, btnUpd);
        card.getChildren().addAll(g, alertLbl);
        getChildren().add(card);
    }
}

// ═══════════════════════════════════════════════════════════════════════
//  DELETE RESTAURANT
// ═══════════════════════════════════════════════════════════════════════
class RestaurantDeletePane extends VBox {
    public RestaurantDeletePane(MainApp app) {
        VBox card = MainApp.cardPane("🗑  Delete Restaurant");
        TextField tfId = MainApp.styledField("Restaurant ID");
        Label alertLbl = MainApp.alertLabel();
        Button btnDel  = MainApp.actionBtn("🗑  Delete", "#ef4444");

        btnDel.setOnAction(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                new Alert(Alert.AlertType.CONFIRMATION, "Delete ID " + id + "?", ButtonType.YES, ButtonType.CANCEL)
                    .showAndWait().ifPresent(b -> { if (b == ButtonType.YES) {
                        try {
                            int rows = new RestaurantDAO().deleteRestaurant(id);
                            if (rows > 0) { MainApp.setAlert(alertLbl, true, "Deleted ID " + id); tfId.clear(); app.setStatus("✔  Deleted."); }
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
