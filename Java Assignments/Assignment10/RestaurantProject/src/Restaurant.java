public class Restaurant {
    private int    id;
    private String name;
    private String address;

    public Restaurant() {}

    public Restaurant(int id, String name, String address) {
        this.id      = id;
        this.name    = name;
        this.address = address;
    }

    public int    getId()      { return id; }
    public String getName()    { return name; }
    public String getAddress() { return address; }

    public void setId(int id)           { this.id      = id; }
    public void setName(String name)    { this.name    = name; }
    public void setAddress(String addr) { this.address = addr; }

    @Override
    public String toString() { return name; }   // shown in ComboBox
}
