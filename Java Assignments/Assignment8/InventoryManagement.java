import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// DESIGN PATTERN 2: ADAPTER
// Product interface — the "Target" interface
interface Product {
    void displayDetails();
}


// Legacy class with incompatible interface
// (the "Adaptee" in the Adapter pattern)

class LegacyItem {
    private int itemId;
    private String description;

    // Parameterized constructor
    public LegacyItem(int itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    // Legacy print method (incompatible with Product interface)
    public void print() {
        System.out.println("Legacy Item [ID: " + itemId + "] - " + description);
    }
}


// DESIGN PATTERN 2: ADAPTER
// ProductAdapter — wraps LegacyItem and makes
// it compatible with the Product interface

class ProductAdapter implements Product {
    private LegacyItem legacyItem;  // Adaptee reference

    public ProductAdapter(LegacyItem legacyItem) {
        this.legacyItem = legacyItem;
    }

    @Override
    public void displayDetails() {
        // Delegates to LegacyItem's print() method
        legacyItem.print();
    }
}


// Modern product — natively implements Product

class NewProduct implements Product {
    private String name;

    public NewProduct(String name) {
        this.name = name;
    }

    @Override
    public void displayDetails() {
        System.out.println("New Product: " + name);
    }
}


// DESIGN PATTERN 1: SINGLETON
// InventoryManager — only one instance ever exists

class InventoryManager {
    // Static reference to the single instance
    private static InventoryManager instance;

    // Internal product list
    private List<Product> inventory;

    // Private constructor prevents external instantiation
    private InventoryManager() {
        inventory = new ArrayList<>();
    }

    // Global access point — creates instance only once
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Add a product to the inventory
    public void addProduct(Product product) {
        inventory.add(product);
    }

    // DESIGN PATTERN 3: ITERATOR
    // Returns an Iterator over the Product list
    public Iterator<Product> returnInventory() {
        return inventory.iterator();
    }
}

// Main class

public class InventoryManagement {


    
    public static void main(String[] args) {

        // SINGLETON — get the single InventoryManager instance
        InventoryManager manager = InventoryManager.getInstance();

        // Add a modern product
        manager.addProduct(new NewProduct("Wireless Headphones"));
        manager.addProduct(new NewProduct("Mechanical Keyboard"));

        // Add legacy items via the Adapter
        LegacyItem legacyItem1 = new LegacyItem(101, "Vintage Rotary Phone");
        LegacyItem legacyItem2 = new LegacyItem(202, "CRT Monitor");

        manager.addProduct(new ProductAdapter(legacyItem1));
        manager.addProduct(new ProductAdapter(legacyItem2));

        // Add one more modern product
        manager.addProduct(new NewProduct("USB-C Hub"));

        System.out.println("===== Inventory Details =====");

        // ITERATOR — traverse the inventory
        Iterator<Product> iterator = manager.returnInventory();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            product.displayDetails();
        }

        System.out.println("=============================");

        // Verify Singleton — both references point to the same instance
        InventoryManager anotherManager = InventoryManager.getInstance();
        System.out.println("\nSingleton verified: " + (manager == anotherManager));
    }
}
