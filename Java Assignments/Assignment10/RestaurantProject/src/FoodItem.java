public class FoodItem {
    private int    id;
    private String name;
    private double price;
    private int    resId;
    private String restaurantName;   // for JOIN display

    public FoodItem() {}

    public FoodItem(int id, String name, double price, int resId, String restaurantName) {
        this.id             = id;
        this.name           = name;
        this.price          = price;
        this.resId          = resId;
        this.restaurantName = restaurantName;
    }

    public int    getId()             { return id; }
    public String getName()           { return name; }
    public double getPrice()          { return price; }
    public int    getResId()          { return resId; }
    public String getRestaurantName() { return restaurantName; }

    public void setId(int id)                     { this.id             = id; }
    public void setName(String name)              { this.name           = name; }
    public void setPrice(double price)            { this.price          = price; }
    public void setResId(int resId)               { this.resId          = resId; }
    public void setRestaurantName(String rName)   { this.restaurantName = rName; }
}
