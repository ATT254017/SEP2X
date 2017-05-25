package Model;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Person {
    private int personID;
    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private boolean isMale;

    public Person(int personID, String firstName, String lastName, String address, int phoneNumber, boolean isMale) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
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
