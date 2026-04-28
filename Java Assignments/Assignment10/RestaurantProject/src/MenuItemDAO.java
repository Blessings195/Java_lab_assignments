import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    private static final String SELECT_JOIN =
        "SELECT mi.Id, mi.Name, mi.Price, mi.ResId, r.Name AS RestaurantName " +
        "FROM MenuItem mi JOIN Restaurant r ON mi.ResId = r.Id";

    // INSERT
    public boolean insertMenuItem(String name, double price, int resId) throws SQLException {
        String sql = "INSERT INTO MenuItem (Name, Price, ResId) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, resId);
            return ps.executeUpdate() > 0;
        }
    }

    // SELECT ALL
    public List<FoodItem> getAllMenuItems() throws SQLException {
        List<FoodItem> list = new ArrayList<>();
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(SELECT_JOIN)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // SELECT WHERE PRICE <= threshold
    public List<FoodItem> getMenuItemsByMaxPrice(double maxPrice) throws SQLException {
        List<FoodItem> list = new ArrayList<>();
        String sql = SELECT_JOIN + " WHERE mi.Price <= ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setDouble(1, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // SELECT WHERE RESTAURANT NAME = ?
    public List<FoodItem> getMenuItemsByRestaurantName(String restName) throws SQLException {
        List<FoodItem> list = new ArrayList<>();
        String sql = SELECT_JOIN + " WHERE r.Name = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, restName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // UPDATE single record
    public boolean updateMenuItem(int id, String name, double price, int resId) throws SQLException {
        String sql = "UPDATE MenuItem SET Name = ?, Price = ?, ResId = ? WHERE Id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, resId);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE price of all records where price <= threshold
    public int updatePriceWhereBelow(double threshold, double newPrice) throws SQLException {
        String sql = "UPDATE MenuItem SET Price = ? WHERE Price <= ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setDouble(2, threshold);
            return ps.executeUpdate();
        }
    }

    // DELETE by ID
    public boolean deleteMenuItemById(int id) throws SQLException {
        String sql = "DELETE FROM MenuItem WHERE Id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE where name starts with letter
    public int deleteMenuItemsByPrefix(String prefix) throws SQLException {
        String sql = "DELETE FROM MenuItem WHERE Name LIKE ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            return ps.executeUpdate();
        }
    }

    private FoodItem mapRow(ResultSet rs) throws SQLException {
        return new FoodItem(
            rs.getInt("Id"),
            rs.getString("Name"),
            rs.getDouble("Price"),
            rs.getInt("ResId"),
            rs.getString("RestaurantName")
        );
    }
}
