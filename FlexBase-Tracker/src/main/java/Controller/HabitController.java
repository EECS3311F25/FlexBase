package Controller;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.DBInput;
import Model.DBOutput;
import Model.UserSession;

public class HabitController {

	    public static void addHabit(String habit, int priority, String start, String end) throws SQLException {
	        int userId = UserSession.getID();

	        String query = "INSERT INTO HABIT (USER_ID, HABIT_NAME, HABIT_PRIORITY, HABIT_TIME_START, HABIT_TIME_END) " +
	                       "VALUES (" + userId + ", '" + habit + "', " + priority + ", '" + start + "', '" + end + "');";

	        DBInput.input(query);
	    }

	    public static ResultSet getHabits() throws SQLException {
	        int userId = UserSession.getID();

	        String query = "SELECT * FROM HABIT WHERE USER_ID = " + userId + ";";

	        return DBOutput.getData(query);
	    }

}
