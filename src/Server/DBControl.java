package Server;

import java.sql.*;
import java.time.LocalDate;

import Model.*;


/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	
	private String connectionURI;
	private String username;
	private String password;

	private String schemaName;

	public DBControl(String connectionURI, String username, String password) throws SQLException
	{
		this.schemaName = "SEP2XGROUP6";
		this.connectionURI = connectionURI + "?currentSchema=" + schemaName;
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
		createDatabase();
	}
	private void databaseDriverError()
	{
		throw new RuntimeException("Postgresql driver is not installed!");
	}
	
	private void createDatabase() throws SQLException
	{
		String createSchema = 		"CREATE SCHEMA IF NOT EXISTS " + schemaName;
		String createEnums = 		"DO $$" +
									"BEGIN" + 
									"	IF NOT EXISTS (SELECT * FROM pg_type WHERE typname = 'state') THEN" +
									"		CREATE TYPE state AS ENUM ('Sold', 'In Stock');" + 
									"	END IF;" +
									"END$$;";
		
		String createTableAccount = "CREATE TABLE IF NOT EXISTS "+schemaName+".\"account\"("  +
									"AccountID SERIAL," +
									"Username VARCHAR(50) NOT NULL UNIQUE," +
									"Password VARCHAR(100) NOT NULL," +
									"Email VARCHAR(100)," +
									"Name VARCHAR(100)," +
									"Surname VARCHAR(100)," +
									"Address VARCHAR(100)," +
									"Phone VARCHAR(100)," + 
									"IsFemale BOOLEAN," +
									"Birthday DATE," +
									"PRIMARY KEY(AccountID));";
		
		String createTableCategory ="CREATE TABLE IF NOT EXISTS "+schemaName+".\"category\"("  +
									"CategoryID SERIAL," + 
									"Category_name VARCHAR(50)," +
									"Cat_description VARCHAR(250)," +
									"Cat_picture VARCHAR(150)," + 
									"PRIMARY KEY(CategoryID));";
		
		String createTableItem = 	"CREATE TABLE IF NOT EXISTS "+schemaName+".\"item\"("  +
									"ItemID SERIAL," + 
									"Item_name VARCHAR(100)," +
									"Category SERIAL," + 
									"Item_price DECIMAL," +
									"Item_state state," + 
									"Description VARCHAR(2000)," +
									"Quantity INT,"+
									"Seller SERIAL," +
									"Sold_amount INT," +
									"Image_source VARCHAR(150)," + 
									"PRIMARY KEY(ItemID)," +
									"FOREIGN KEY(Category) REFERENCES "+schemaName+".\"category\"(CategoryID)," + 
									"FOREIGN KEY(Seller) REFERENCES "+schemaName+".\"account\"(AccountID));";
				
		
		
		try(	Connection connection = connect();
				PreparedStatement schemaStatement = connection.prepareStatement(createSchema);
				PreparedStatement enumsStatement = connection.prepareStatement(createEnums);
				PreparedStatement accountTableStatement = connection.prepareStatement(createTableAccount);
				PreparedStatement categoryTableStatement = connection.prepareStatement(createTableCategory);
				PreparedStatement itemTableStatement = connection.prepareStatement(createTableItem))
		{
			schemaStatement.execute();
			enumsStatement.execute();
			accountTableStatement.execute();
			categoryTableStatement.execute();
			itemTableStatement.execute();
		}
	}
	
	public Account checkUserCredentials(String userName, String passwd)
	{
		//returns null if not exist
		return null;
	}
	
	public boolean registerAccount(Account account, String password)
	{
		String createAccountSQL = "INSERT INTO \"account\"(Username, Password, Email, Name, Surname, Address, Phone, IsFemale, Birthday)" 
	+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = connect();
	              PreparedStatement pstmt = conn.prepareStatement(createAccountSQL,
	                      Statement.RETURN_GENERATED_KEYS)) 
		{
			//pstmt.setInt(1, account.getAccountID());
			pstmt.setString(1, account.getUserName());
			pstmt.setString(2, password);
			pstmt.setString(3, account.getEmail());
			pstmt.setString(4, account.getPerson().getFirstName());
			pstmt.setString(5,  account.getPerson().getLastName());
			pstmt.setString(6,  account.getPerson().getAddress());
			pstmt.setInt(7,  account.getPerson().getPhoneNumber());
			pstmt.setBoolean(8,  account.getPerson().getIsMale());
			pstmt.setDate(9, new Date(account.getPerson().getBirthday().toEpochDay()));// birthday.getYear(), birthday.getMonthValue(), birthday.getDayOfMonth()));
			
			int affectedRows = pstmt.executeUpdate();
			
			if (affectedRows == 1) {
				return true;
			}
		} catch (SQLException ex) {
	          System.out.println(ex.getMessage());
	    }
		return false;
		//returns true if account sucessfully created, false if username already exists. Could there possibly be other errors?
	}
	
	public boolean deleteAccount(Account account, String password) {
		String deleteAccountSQL = "DELETE FROM Account WHERE AccountID=" + "'" + account.getAccountID() + "';";
		
		try(Connection conn = connect();
	              PreparedStatement pstmt = conn.prepareStatement(deleteAccountSQL,
	                      Statement.RETURN_GENERATED_KEYS)) {
			int affectedRows = pstmt.executeUpdate();
	          // check the affected rows 
	          if (affectedRows == 0) {
	              return true;
	          }
		} catch (SQLException ex) {
	          System.out.println(ex.getMessage());
	    }
		
		return false;
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
  
   /*
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
  */

	
}
