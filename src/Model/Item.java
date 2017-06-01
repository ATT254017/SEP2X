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
    private int initialQuantity;
    private int currentRemainingQuantity;
    private ItemState state;
    private Category itemCategory;
    private Account seller;

    public Item(int itemID, String itemName, double itemPrice, int initialQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.initialQuantity = initialQuantity;
        if(initialQuantity > 0)
        	state = ItemState.InStock;
    }
    
    public int getCurrentRemainingQuantity()
	{
		return currentRemainingQuantity;
	}

	public Item setCurrentRemainingQuantity(int currentRemainingQuantity)
	{
		this.currentRemainingQuantity = currentRemainingQuantity;
		if(state != ItemState.Cancelled)
			state = currentRemainingQuantity > 0 ? ItemState.InStock : ItemState.SoldOut;
		return this;
	}

	public Account getSeller()
    {
    	return seller;
    }
    
    public Item setSeller(Account seller)
    {
    	this.seller = seller;
    	return this;
    }
    
    
    public Category getItemCategory()
	{
		return itemCategory;
	}


	public Item setItemCategory(Category itemCategory)
	{
		this.itemCategory = itemCategory;
		return this;
	}

    public int getInitialQuantity() {
    	return initialQuantity;
    }
  
    public Item setItemState(ItemState state) {
    	this.state = state;
    	return this;
    }
    
    public ItemState getItemState() {
    	return this.state;
    }

    public int getItemID() {
        return itemID;
    }
/*
    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
*/
    public String getItemName() {
        return itemName;
    }
/*
    public Item setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }
*/
    public double getItemPrice() {
        return itemPrice;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }
}
