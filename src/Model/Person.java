package Model;

import java.io.Serializable;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Person implements Serializable {
    private int personID;
    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private boolean isMale;
    private int birthYear;
    private int birthMonth;
    private int birthDay;

    public Person(int personID, String firstName, String lastName, String address, int phoneNumber, boolean isMale, int birthYear, int birthMonth, int birthDay) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }
    
    public void setBirthday(int birthYear, int birthMonth, int birthDay) {
    	this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }
    
    public String getBirthday() {
    	return birthYear + "-" + birthMonth + "-" + birthDay;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public boolean getIsMale() {
        return isMale;
    }
}
