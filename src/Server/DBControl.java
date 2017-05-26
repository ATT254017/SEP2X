package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	public static void main(String[] args) throws SQLException {
		DriverManager.registerDriver(new org.postgresql.Driver());
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"password");
		
			connection.close();
	}
}
