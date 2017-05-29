package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private boolean isMale;
    private LocalDate birthDay;

    public Person(String firstName, String lastName, String address, int phoneNumber, boolean isMale, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
        this.birthDay = birthday;
    }
   /* 
    public void setBirthday(int birthYear, int birthMonth, int birthDay) {
    	this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }*/
    
    public LocalDate getBirthday() {
    	return birthDay;//return birthYear + "-" + birthMonth + "-" + birthDay;
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
