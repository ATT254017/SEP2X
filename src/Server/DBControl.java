package Server;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.Account;

/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	
	Connection connection;
	public DBControl(String connectionURI, String username, String password) throws SQLException
	{
		try {
			DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance()); //test if the class exists
		} catch(ClassNotFoundException e) {
			databaseDriverError();
			return;
		}
		catch (InstantiationException e)
		{
			databaseDriverError();
			return;
		}
		catch (IllegalAccessException e)
		{
			databaseDriverError();
			return;
		}
		
		connection = DriverManager.getConnection(connectionURI, username, password);
	}
	private void databaseDriverError()
	{
		throw new RuntimeException("Postgresql driver is not installed!");
	}
	
	public Account checkUserCredentials(String userName, String passwd)
	{
		//returns null if not exist
		return null;
	}
	public boolean registerAccount(Account account, String password)
	{
		return false;
		//returns true if account sucessfully created, false if username already exists. Could there possibly be other errors?
	}
	
}
