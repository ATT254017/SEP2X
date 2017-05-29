package Model;

import java.io.Serializable;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Account implements Serializable{
	private int accountID;
    private String userName;
    private String email;
    private Person person;

    public Account(Integer accountID, String userName, String email, Person person) {
        this.userName = userName;
        this.email = email;
        this.person = person;
        this.accountID = accountID;
    }
    
    public void setAccountID(int accountID) {
    	this.accountID = accountID;
    }
    
    public int getAccountID() {
    	return accountID;
    }

    public Person getPerson()
    {
    	return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
