package Model;

/**
 * The {@code User} class represents a user profile within the FlexBase Habit Tracker application.
 * <p>
 * It defines the basic user information that the system needs to store and manage,
 * including the user's name, password, and their purpose or goal for using the app.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 *     User user = new User("Alex", "securePass123", "Build better daily habits");
 *     System.out.println(user.getName());     // Outputs: Alex
 *     System.out.println(user.getPurpose());  // Outputs: Build better daily habits
 * </pre>
 *
 * <h3>Fields:</h3>
 * <ul>
 *     <li>{@code name} – The username or identifier of the user.</li>
 *     <li>{@code password} – The password used for authentication (currently stored in plain text; should be hashed in production).</li>
 *     <li>{@code purpose} – The reason or goal the user is using the habit tracker.</li>
 * </ul>
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
