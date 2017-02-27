package postGres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {

	private static Connection c = null;
	private static Statement stmt = null;
	
	public static void connection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "1234");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static boolean executeInsertQuery(String sql)
	{
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static ResultSet executeSelectQuery(String sql)
	{
		try {
			stmt = c.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}