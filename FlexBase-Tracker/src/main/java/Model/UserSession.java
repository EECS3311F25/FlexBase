package Model;

public class UserSession {
	// need to have user id so the RIGHT user can grab THEIR tasks
	// right now habit just inserts directly into the db, this is bad. 
	
	private static int userID;
	private static String username;
	
	public static void setUser(int id, String user) {
		userID = id;
		username = user;
	}
	
	public static int getID() {
		return userID;
	}
	
	public static String getUser() {
		return username;
	}

}
