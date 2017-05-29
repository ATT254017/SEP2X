package Server;

import java.sql.*;

import com.sun.media.jfxmedia.locator.ConnectionHolder;

import Model.*;


/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	
	private String connectionURI;
	private String username;
	private String password;

	public DBControl(String connectionURI, String username, String password) throws SQLException
	{
		this.connectionURI = connectionURI;
		this.username = username;
		this.password = password;
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
	
	public long insertItem(Item item) {
      String SQL = "INSERT INTO item(itemid, item_name, item_price, description)"
              + "VALUES(?,?,?,?)";

      long id = 0;

      try (Connection conn = connect();
              PreparedStatement pstmt = conn.prepareStatement(SQL,
              Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setInt(1, item.getItemID());
          pstmt.setString(2, item.getItemName());
          pstmt.setDouble(3, item.getItemPrice());
          pstmt.setString(4, item.getDescription());
          

          int affectedRows = pstmt.executeUpdate();
          // check the affected rows 
          if (affectedRows > 0) {
              // get the ID back
              try (ResultSet rs = pstmt.getGeneratedKeys()) {
                  if (rs.next()) {
                      id = rs.getLong(1);
                  }
              } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
              }
          }
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      }
      return id;
  }
   
   
   private Connection connect() throws SQLException
   {
	   return DriverManager.getConnection(connectionURI, username, password);
   }
   
   public long insertPerson(Person person) {
      String SQL = "INSERT INTO item(name, surname, address, phone) "
              + "VALUES(?,?,?,?)";

      long id = 0;

      try (Connection conn = connect();
              PreparedStatement pstmt = conn.prepareStatement(SQL,
              Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setString(1, person.getFirstName());
          pstmt.setString(2, person.getLastName());
          pstmt.setString(3, person.getAddress());
          pstmt.setInt(4, person.getPhoneNumber());
          
          

          int affectedRows = pstmt.executeUpdate();
          // check the affected rows 
          if (affectedRows > 0) {
              // get the ID back
              try (ResultSet rs = pstmt.getGeneratedKeys()) {
                  if (rs.next()) {
                      id = rs.getLong(1);
                  }
              } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
              }
          }
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      }
      return id;
  }
   
   public long insertAccount(Account account) {
      String SQL = "INSERT INTO account(username,email) "
              + "VALUES(?,?)";

      long id = 0;

      try (Connection conn = connect();
              PreparedStatement pstmt = conn.prepareStatement(SQL,
              Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setString(1, account.getUserName());
          pstmt.setString(2, account.getEmail());
                  
          
          int affectedRows = pstmt.executeUpdate();
          // check the affected rows 
          if (affectedRows > 0) {
              // get the ID back
              try (ResultSet rs = pstmt.getGeneratedKeys()) {
                  if (rs.next()) {
                      id = rs.getLong(1);
                  }
              } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
              }
          }
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      }
      return id;
  }
   
   
   //close the connection

	
}
