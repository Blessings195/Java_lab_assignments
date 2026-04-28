import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    // INSERT
    public boolean insertRestaurant(String name, String address) throws SQLException {
        String sql = "INSERT INTO Restaurant (Name, Address) VALUES (?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            return ps.executeUpdate() > 0;
        }
    }

    // SELECT ALL
    public List<Restaurant> getAllRestaurants() throws SQLException {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM Restaurant";
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Restaurant(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Address")
                ));
            }
        }
        return list;
    }

    // SELECT BY ID
    public Restaurant getRestaurantById(int id) throws SQLException {
        String sql = "SELECT * FROM Restaurant WHERE Id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address"));
            }
        }
        return null;
    }

    // UPDATE
    public boolean updateRestaurant(int id, String name, String address) throws SQLException {
        String sql = "UPDATE Restaurant SET Name = ?, Address = ? WHERE Id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public int deleteRestaurant(int id) throws SQLException {
        String sql = "DELETE FROM Restaurant WHERE Id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}
