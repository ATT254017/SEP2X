package Model;

import java.io.Serializable;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Item implements Serializable{
    private int itemID;
    private String itemName;
    private double itemPrice;
    private String description;
    private ItemState state;

    public Item(int itemID, String itemName, double itemPrice, String description, ItemState state) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.description = description;
        this.state = state;
    }
    
    public void changeItemState(ItemState state) {
    	this.state = state;
    }
    
    public String getItemState() {
    	return this.state.getValue();
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

    public String getDescription() {
        return description;
    }
}
