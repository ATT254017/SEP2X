package Model;

public enum Method {
    SignIn("SIGN_IN", false),
    RegisterAccount("REGISTER_ACCOUNT", false),
	CreateListing("CREATE_LISTING", true),
    GetAccount("GET_ACCOUNT", false),
    BuyItem("BUY_ITEM", true),
    MakeOffer("MAKE_OFFER", true),
	GetListings("GET_LISTINGS", false);
	
    private final String value;
    private final boolean loginRequired;
    private Method(String val, boolean loginRequired) 
    { 
    	this.value = val;
    	this.loginRequired = loginRequired;
    }
    public String getValue() 
    {
    	return value; 
    }
    public boolean requiresLogin()
    {
    	return loginRequired;
    }
}
