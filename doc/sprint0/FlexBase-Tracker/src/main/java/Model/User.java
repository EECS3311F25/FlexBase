package Model;

/**
 * What information we want to store for the user of this app
 */
public class User {
    private String name;
    private String password;
    private String purpose;

    public User(String name, String password, String purpose) {
        this.name = name;
        this.password = password;
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPurpose() {
        return purpose;
    }

}
