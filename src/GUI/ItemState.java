package GUI;

public enum ItemState {
	InStock("IN_STOCK"),
	Sold("SOLD");
	
	private String value;
	
	private ItemState(String value) {
		this.value = value;
	}
	
	public void setItemState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
