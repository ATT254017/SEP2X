package Model;

import jdk.nashorn.internal.runtime.Undefined;

public enum ItemState {
	InStock("In Stock"),
	SoldOut("Sold out"),
	Cancelled("Cancelled"),
	Undefined("Undefined");

	
	private String value;
	
	private ItemState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static ItemState getEnum(String enumString)
	{
		if(ItemState.Cancelled.getValue().equals(enumString)) return ItemState.Cancelled;
		if(ItemState.InStock.getValue().equals(enumString)) return ItemState.InStock;
		if(ItemState.SoldOut.getValue().equals(enumString)) return ItemState.SoldOut;
		
		return ItemState.Undefined;

	}
	
}
