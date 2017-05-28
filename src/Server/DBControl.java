package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.Account;

/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	public static void main(String[] args) throws SQLException {
		//DriverManager.registerDriver(new org.postgresql.Driver());
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"password");
		
			connection.close();
	}
	
	public Account checkUserCredentials(String userName, String passwd)
	{
		//returns null if not exist
		return null;
	}
	public boolean registerAccount(Account account)
	{
		return false;
		//returns true if account sucessfully created, false if username already exists. Could there possibly be other errors?
	}
	
}
