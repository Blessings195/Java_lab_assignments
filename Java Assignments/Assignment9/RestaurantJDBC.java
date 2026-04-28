import java.sql.*;

public class RestaurantJDBC{
    public RestaurantJDBC(){}

    public static final String URL = "jdbc:mysql://localhost:3306/jdbcDatabase";
    static final String USERNAME = "student_developer";
    static final String PASSWORD = "#student123";
    Connection con = null;


    //Create the tables
    public static void createTables(Connection con) throws SQLException{
        Statement st = con.createStatement();
        st.execute("DROP TABLE IF EXISTS MenuItem");
        st.execute("DROP TABLE IF EXISTS Restaurant");

        st.execute("""
            CREATE TABLE Restaurant (id INT PRIMARY KEY AUTO_INCREMENT, 
            name VARCHAR(100) NOT NULL,
            address VARCHAR(200) NOT NULL)
        """);
        st.execute("""
                CREATE TABLE MenuItem(id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL, price DECIMAL(10,2) NOT NULL,
                resId INT NOT NULL,
                FOREIGN KEY (resId) REFERENCES Restaurant(id))
                """);

        System.out.println("Tables created successfully!");
        st.close();
            
    }

    //INSERT
    public static void insert(Connection con) throws SQLException{
        String restSQL = "INSERT IGNORE INTO Restaurant (name, address) VALUES(?,?)";

        //Restaurant Table
        String[][] restaurants = {
            {"Cafe Java", "12 MG Road, Pune"},
            {"Spice Garden", "45 FC Road, Pune"},
            {"The Burger Joint", "78 JM Road, Pune"},
            {"Pizza Palace", "33 Baner Road, Pune"},
            {"Noodle House", "21 Wakad, Pune"},
            {"Green Leaf", "9 Kothrud"},
            {"Royal Dine", "67 Viman Nagar, Pune"},
            {"Taste of India", "5 Hadapsar, Pune"},
            {"Ocean Breeze", "14 Koregaon Park, Pune"},
            {"Masala Twist", "88 Aundh, Pune"}
        };
        try(PreparedStatement ps = con.prepareStatement(restSQL)){
            for (String[] r: restaurants){
                ps.setString(1, r[0]);
                ps.setString(2, r[1]);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("10 records entered into the Restaurant table.");
        printRestaurantTable(con);

        //MenuItem table
        String menuSQL = "INSERT IGNORE INTO MenuItem(name, price, resId) VALUES(?, ?, ?)";
        Object[][] menuItems = {
            {"Espresso", 80.00, 1},
            {"Cappucino", 100.00, 1},
            {"Pasta Arrabiata", 149.99, 1},
            {"Paneer Tikka", 95.00, 2},
            {"Dal Makhani", 100.49, 2},
            {"Veg Burger", 110.29, 3},
            {"Pizza Margherita", 180.0, 4},
            {"Pepperoni Pizza", 200.0, 4},
            {"Spring Rolls", 60.0, 5},
            {"Hakka Noodles", 110.0, 5}
        };
        try(PreparedStatement ps = con.prepareStatement(menuSQL)){
            for(Object[] i: menuItems){
                ps.setString(1, (String) i[0]);
                ps.setDouble(2, (Double) i[1]);
                ps.setInt(3, (Integer) i[2]);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("10 records inserted into the MenuItem table.");
        printMenuItemTable(con);
    }

    //Print ResultSet in tabular format
        static void printResultSet(ResultSet rs, String[] headers) throws SQLException {
        int colWidth = 22;
        String line  = "─".repeat(colWidth * headers.length + headers.length + 1);
 
        System.out.println(line);
        StringBuilder header = new StringBuilder("|");
        for (String h : headers)
            header.append(String.format(" %-" + (colWidth - 1) + "s|", h));
        System.out.println(header);
        System.out.println(line);
 
        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
            StringBuilder row = new StringBuilder("|");
            for (String h : headers)
                row.append(String.format(" %-" + (colWidth - 1) + "s|", rs.getString(h)));
            System.out.println(row);
        }
        if (!hasRows) System.out.println("  (no records found)");
        System.out.println(line + "\n");
    }
    //SELECT
    static void selectPrice(Connection con) throws SQLException{
        String sql = "SELECT * FROM MenuItem WHERE price <= 100";
        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Menu items less than 100");
            printResultSet(rs, new String[]{"id", "name", "price", "resId"});
        }
    }
    static void selectRecord(Connection con) throws SQLException{
        String sql = """
                SELECT m.id, m.name, m.price, r.name as Restaurant
                FROM MenuItem m
                join restaurant r on r.id = m.resId
                WHERE r.name = "Cafe Java"
                """;
        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){
                System.out.println("Menu items as Cafe Java.");
                printResultSet(rs, new String[]{"Id", "Name", "Price", "Restaurant"});
            }
    }

    //UPDATE
    static void updateRecords(Connection con)throws SQLException{
        String sql = "UPDATE MenuItem SET price = 200 WHERE price <= 100";

        try(Statement st = con.createStatement();){
            int rows = st.executeUpdate(sql);
            System.out.println("Successfulyy updated " + rows + " row(s).");
        }
        System.out.println("MenuItem table after the update:");
        printMenuItemTable(con);
    }

    //DELETE
    static void deletePItems(Connection con) throws SQLException{
        String sql = "DELETE FROM MenuItem WHERE name LIKE 'P%'";
        try(Statement st = con.createStatement()){
            int rows = st.executeUpdate(sql);
            System.out.println("Deleted " + rows + " row(s).");
        }
        System.out.println("MenuItem table after DELETE:");
        printMenuItemTable(con);
    }
    
    //print Restaurant table
     static void printRestaurantTable(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant")) {
            System.out.println("\n  Restaurant table:");
            printResultSet(rs, new String[]{"Id", "Name", "Address"});
        }
    }

    //Print MenuItem table

    static void printMenuItemTable(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM MenuItem")) {
            System.out.println("\n  MenuItem table:");
            printResultSet(rs, new String[]{"Id", "Name", "Price", "ResId"});
        }
    }


    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected successfully!");

            createTables(con);
            insert(con);
            selectPrice(con);
            selectRecord(con);
            updateRecords(con);
            deletePItems(con);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}