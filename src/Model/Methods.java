package Model;

public enum Methods {
    SignIn("SIGN_IN", false),
    RegisterAccount("REGISTER_ACCOUNT", false);
	
	
	
    private final String value;
    private final boolean loginRequired;
    private Methods(String val, boolean loginRequired) 
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
