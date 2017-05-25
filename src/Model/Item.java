package Model;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Item {
    private int itemID;
    private String itemName;
    private double itemPrice;
    private String description;

    public Item(int itemID, String itemName, double itemPrice, String description) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.description = description;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
