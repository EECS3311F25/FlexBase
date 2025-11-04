package Controller;

import java.sql.*;

public class DatabaseController {
	
	private static String DB_URL = "jdbc:mysql://127.0.0.1:3306/flexbase_schema";
	private static String DB_USER = "root";
    private static String DB_PASS = "Root1234";
	
	public static void main(String[]args)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		
		String input1 = "insert into habit values"
				 + "('1', 'gaming', '3', '19:00:00', '21:00:00');"
				 ;
		
//		String input2 = "insert into habit values"
//				 + "('2', 'studying', '8', '12:00:00', '18:00:00');"
//				 ;
		
		String select = "select * from habit";
		
		
		try {
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			PreparedStatement stmt = con.prepareStatement(input1);
//			stmt.executeUpdate();
			
			stmt = con.prepareStatement(select);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData colCount = rs.getMetaData();
			
			while(rs.next())
			{
				for (int i = 1; i <= colCount.getColumnCount(); i++)
				{
					System.out.println(rs.getString(i));
				}
			}
		}
		
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
