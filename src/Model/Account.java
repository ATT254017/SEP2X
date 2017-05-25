package Model;

/**
 * Created by Afonso on 5/25/2017.
 */
public class Account {
    private String userName;
    private String passwordHash;
    private String email;
    private Person person;

    public Account(String userName, String passwordHash, String email, Person person) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.person = person;
    }

    public void setPerson(int personID, String firstName, String lastName, String address, int phoneNumber, boolean isMale) {
        person = new Person(personID, firstName, lastName, address, phoneNumber, isMale);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
