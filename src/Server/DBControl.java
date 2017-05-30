package Server;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import Model.*;


/**
 * Created by Afonso on 5/26/2017.
 */
public class DBControl {
	
	private String connectionURI;
	private String username;
	private String password;

	private String databaseName;
	private String schemaName;

	public DBControl(String connectionURI, String username, String password) throws SQLException
	{
		this.databaseName = "sep2xgroup6_db";
		this.schemaName = "sep2xgroup6";
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
		if(!databaseExists())
			createDatabase();
		
		this.connectionURI += databaseName + "?currentSchema=" + schemaName;;
		
		populateDatabase();
	}
	private void databaseDriverError()
	{
		throw new RuntimeException("Postgresql jdbc driver is not included in the project! Download at jdbc.postgresql.org");
	}
	
	private boolean databaseExists()
	{
		String sql = "SELECT 1 FROM pg_catalog.pg_database where datname = ?;";
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql);)
		{
			statement.setString(1, databaseName);
			ResultSet res = statement.executeQuery();
			return res.next();
		}
		catch(SQLException ex)
		{
			return false;
		}
	}
	private void createDatabase()
	{
		String sql = "CREATE DATABASE " + databaseName;
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.execute();
		}
		catch(SQLException ex)
		{
			return;
		}
	}
	private void populateDatabase() throws SQLException
	{
		String createSchema = 		"CREATE SCHEMA IF NOT EXISTS " + schemaName;
		
		String createEnums = 		"DO $$" +
									"BEGIN" + 
									"	IF NOT EXISTS (SELECT * FROM pg_type WHERE typname = 'state') THEN" +
									"		CREATE TYPE "+schemaName+".state AS ENUM ('Sold', 'In Stock');" + 
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
									"Phone INT," + 
									"IsMale BOOLEAN," +
									"Birthday DATE," +
									"PRIMARY KEY(AccountID));";
		
		String createTableCategory ="CREATE TABLE IF NOT EXISTS "+schemaName+".category("  +
									"CategoryID SERIAL," + 
									"Category_name VARCHAR(50)," +
									"Cat_description VARCHAR(250)," +
									"Cat_picture VARCHAR(150)," + 
									"Cat_parent SERIAL," +
									"FOREIGN KEY(Cat_parent) REFERENCES category(CategoryID)," + 
									"PRIMARY KEY(CategoryID));";
		
		String createTableItem = 	"CREATE TABLE IF NOT EXISTS \"item\"("  +
									"ItemID SERIAL," + 
									"Item_name VARCHAR(100)," +
									"Category SERIAL," + 
									"Item_price DECIMAL," +
									"Item_state \"state\"," + 
									"Description VARCHAR(2000)," +
									"Quantity INT,"+
									"Seller SERIAL," +
									"Sold_amount INT," +
									"Image_source VARCHAR(150)," + 
									"PRIMARY KEY(ItemID)," +
									"FOREIGN KEY(Category) REFERENCES "+schemaName+".category(CategoryID)," + 
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
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public List<Category> getCategories(Category category)
	{
		//if category is null, return all top-level categories (categories without parents)
		//else return all children of the specified category
		List<Category> list = new LinkedList<>();
		String sql = "";
		if(category != null)
			sql = "SELECT * FROM category WHERE Cat_parent = ?";
		else
			sql = "SELECT * FROM category WHERE Cat_parent IS NULL";
		
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql))
		{
			if(category != null)
				statement.setInt(1, category.getCategoryID());
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				Category cat = new Category(resultSet.getInt("categoryID"), resultSet.getString("category_name"));
				resultSet.getInt("Cat_parent");
				cat.setHasParent(!resultSet.wasNull());
				cat.setParent(category);
				cat.setCategoryDescription(resultSet.getString("cat_description"));
				list.add(cat);
			}
		}
		catch (Exception e) {
		}
		return list;
	}
	
	public Account checkUserCredentials(String userName, String passwd)
	{
		String getAccountSQL = "SELECT * FROM \"account\" WHERE username = ? AND password = ?";
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(getAccountSQL))
		{
			statement.setString(1, userName);
			statement.setString(2, passwd);
			ResultSet resultSet = statement.executeQuery();
			if(!resultSet.next())
				return null; //no rows found
			
			return new Account(resultSet.getString("username"), resultSet.getString("email"), 
					new Person(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("address"), 
							resultSet.getInt("phone"), resultSet.getBoolean("ismale"), LocalDate.ofEpochDay(resultSet.getDate("birthday").getTime())));
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public void insertCategory(Category category)
	{
		String sql = "INSERT INTO category";
	}
	
	public boolean registerAccount(Account account, String password)
	{
		String createAccountSQL = "INSERT INTO \"account\"(Username, Password, Email, Name, Surname, Address, Phone, IsMale, Birthday)" 
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
	
	public boolean deleteAccount(Account account) {
		String deleteAccountSQL = "DELETE FROM Account WHERE Username = '?'";
		
		try(Connection conn = connect();
	              PreparedStatement pstmt = conn.prepareStatement(deleteAccountSQL,
	                      Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, account.getUserName());
			int affectedRows = pstmt.executeUpdate();
	          // check the affected rows 
	          if (affectedRows == 1) {
	              return true;
	          }
		} catch (SQLException ex) {
	          System.out.println(ex.getMessage());
	    }
		
		return false;
	}
	
	public long insertItem(Item item) {
      String SQL = "INSERT INTO item(itemid, item_name, item_price, description, quantity)"
              + "VALUES(?,?,?,?)";

      long id = 0;

      try (Connection conn = connect();
              PreparedStatement pstmt = conn.prepareStatement(SQL,
              Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setInt(1, item.getItemID());
          pstmt.setString(2, item.getItemName());
          pstmt.setDouble(3, item.getItemPrice());
          pstmt.setString(4, item.getDescription());
          pstmt.setInt(5, item.getQuantity());
          

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
	
	public boolean removeItem(Item item) {
		String deleteAccountSQL = "DELETE FROM Account WHERE itemid = '?'";
		
		try(Connection conn = connect();
	              PreparedStatement pstmt = conn.prepareStatement(deleteAccountSQL,
	                      Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, item.getItemID());
			int affectedRows = pstmt.executeUpdate();
	          // check the affected rows 
	          if (affectedRows == 1) {
	              return true;
	          }
		} catch (SQLException ex) {
	          System.out.println(ex.getMessage());
	    }
		
		return false;
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
