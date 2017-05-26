package Model;

public enum Methods {
    SignIn("SIGN_IN"),
    SearchItem("SEARCH_ITEM"),
    SearchAllItems("SEARCH_ALL_ITEMS"),
    RegisterAccount("REGISTER_ACCOUNT");
	
	
    private final String value;
    private Methods(String val) { this.value = val; }
    public String getValue() { return value; }
}
