package Server;

import java.security.cert.CertificateEncodingException;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
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
		defaultCategories();
	}
	private void databaseDriverError()
	{
		throw new RuntimeException("Postgresql jdbc driver is not included in the project! Download at jdbc.postgresql.org");
	}

	private void defaultCategories()
	{
		insertCategory("Subcat of Antiques", "", getCategory("Antiques"));
		insertCategory("Antiques", "", null);
		insertCategory("Art", "", null);
		insertCategory("Baby", "", null);
		insertCategory("Books", "", null);
		insertCategory("Business & Industrial", "", null);
		insertCategory("Cameras & Photo", "", null);
		insertCategory("Cell Phones & Accessories", "", null);
		insertCategory("Clothing, Shoes & Accessories", "", null);
		insertCategory("Coins & Paper Money", "", null);
		insertCategory("Collectibles", "", null);
		insertCategory("Computers/Tablets & Networking", "", insertCategory("Consumer Electronics", "", null));
		insertCategory("Crafts", "", null);
		insertCategory("Dolls & Bears", "", null);
		insertCategory("DVDs & Movies", "", null);
		insertCategory("Motors", "", null);
		insertCategory("Gift Cards & Coupons", "", null);
		insertCategory("Health & Beauty", "", null);
		insertCategory("Home & Garden", "", null);
		insertCategory("Jewelry & Watches", "", null);
		insertCategory("Music", "", null);
		insertCategory("Musical Instruments & Gear", "", null);
		insertCategory("Pet Supplies", "", null);
		insertCategory("Specialty Services", "", null);
		insertCategory("Sporting Goods", "", null);
		insertCategory("Tickets & Experiences", "", null);
		insertCategory("Toys & Hobbies", "", null);
		insertCategory("Video Games & Consoles", "", null);
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
									"Category_name VARCHAR(50) UNIQUE," +
									"Cat_description VARCHAR(250)," +
									"Cat_picture VARCHAR(150)," + 
									"Cat_parent int," +
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
		
		String createSalesTable =	"CREATE TABLE IF NOT EXISTS \"sales\"("	 +
									"SalesID SERIAL," +
									"BuyerID SERIAL," +
									"ItemID SERIAL," +
									"QuantityBought INT," +
									"TotalAmount DECIMAL(5, 2)," +
									"PRIMARY KEY(SalesID)," +
									"FOREIGN KEY(BuyerID) REFERENCES "+schemaName+".\"account\"(AccountID)," +
									"FOREIGN KEY(ItemID) REFERENCES "+schemaName+".\"item\"(ItemID));";
				
		
		
		try(	Connection connection = connect();
				PreparedStatement schemaStatement = connection.prepareStatement(createSchema);
				PreparedStatement enumsStatement = connection.prepareStatement(createEnums);
				PreparedStatement accountTableStatement = connection.prepareStatement(createTableAccount);
				PreparedStatement categoryTableStatement = connection.prepareStatement(createTableCategory);
				PreparedStatement itemTableStatement = connection.prepareStatement(createTableItem);
				PreparedStatement salesTableStatement = connection.prepareStatement(createSalesTable))
		{
			schemaStatement.execute();
			enumsStatement.execute();
			accountTableStatement.execute();
			categoryTableStatement.execute();
			itemTableStatement.execute();
			salesTableStatement.execute();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private Account getAccountFromRS(ResultSet resultSet) throws SQLException
	{
		return new Account(resultSet.getInt("accountid"), resultSet.getString("username"), resultSet.getString("email"), 
				new Person(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("address"), 
						resultSet.getInt("phone"), resultSet.getBoolean("ismale"), LocalDate.ofEpochDay(resultSet.getDate("birthday").getTime())));
	}
	
	public List<Item> searchItems(Category category, String searchPredicate)
	{
		List<Item> result = new LinkedList<>();
		int i = 1;
		String sql = "select * from item join account on seller = accountid join category on category = categoryid ";
		if(category != null)
		{
			sql += "where ";
			sql += (category.getCategoryID() > 0 ? "categoryid = ?" : "category_name = ?");
		}
		if(searchPredicate != null && !searchPredicate.equals(""))
		{
			if(category != null)
			{
				sql += " AND ";
				i++;
			}
			else
				sql += "where ";
			sql += "(LOWER(item_name) LIKE ? OR LOWER(description) LIKE ?)";
		}
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql))
		{
			System.out.println(sql);
			if(category != null && category.getCategoryID() > 0)
				statement.setInt(1, category.getCategoryID());
			else if(category != null)
				statement.setString(1, category.getCategoryName());
			
			if(searchPredicate != null)
			{
				searchPredicate = "%" + searchPredicate.toLowerCase() + "%";
				statement.setString(i, searchPredicate);
				statement.setString(i+1, searchPredicate);
			}
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				result.add(new Item(resultSet.getInt("itemid"), resultSet.getString("item_name"), resultSet.getDouble("item_price"),
						resultSet.getString("description"), resultSet.getInt("quantity")).setDescription(resultSet.getString("description"))
						.setItemCategory(category == null ? getCategory(resultSet.getString("category_name")) : category).setSeller(getAccountFromRS(resultSet)));
			}
			return result;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return result;
		}
	}
	
	public Category getCategory(String categoryName)
	{
		String sql = "with recursive category_tree as ("+
						"select categoryid, cat_parent, category_name, cat_description" +
						"from category"+
						"where category_name = ?"+
						"union all"+
						"select parent.categoryid, parent.cat_parent, parent.category_name, parent.cat_description"+
						"from category parent"+
						"join category_tree child on parent.categoryid = child.cat_parent)"+
						"select * from category_tree;";
		
		try(Connection connection = connect();
				PreparedStatement statement = connection.prepareStatement(sql))
			{
			statement.setString(1, categoryName);
			ResultSet resultSet = statement.executeQuery();
			if(!resultSet.next())
				return null;
			
			Category category = new Category(resultSet.getInt("categoryid"), resultSet.getString("category_name"));
			category.setCategoryDescription(resultSet.getString("cat_description"));
			
			Category child = category;
			while(resultSet.next()) //the entire parent tree will be on the next rows.
			{
				Category parent = new Category(resultSet.getInt("categoryid"), resultSet.getString("category_name"));
				parent.setCategoryDescription(resultSet.getString("cat_description"));
				child.setParent(parent);
				child = parent;
			}
			
			return category;
			}
		catch(SQLException ex)
		{
			return null;
		}
	}
	
	
	public List<Category> getCategories(Category category)
	{
		//if category is null, return all top-level categories (categories without parents)
		//else return all children of the specified category
		List<Category> list = new LinkedList<>();
		String sql = "select *, EXISTS(SELECT 1 FROM category d where d.cat_parent = c.categoryid) AS haschild  from category c where c.cat_parent %s";
		if(category != null)
			sql = String.format(sql, "= ?");
		else
			sql = String.format(sql, "IS NULL");
		
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql))
		{
			if(category != null)
				statement.setInt(1, category.getCategoryID());
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				Category cat = new Category(resultSet.getInt("categoryID"), resultSet.getString("category_name"));
				//resultSet.getInt("Cat_parent");
				//cat.setHasParent(!resultSet.wasNull());
				cat.setHasChildren(resultSet.getBoolean("haschild"));
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
			
			return new Account(resultSet.getInt("accountid"), resultSet.getString("username"), resultSet.getString("email"), 
					new Person(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("address"), 
							resultSet.getInt("phone"), resultSet.getBoolean("ismale"), LocalDate.ofEpochDay(resultSet.getDate("birthday").getTime())));
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Category insertCategory(String categoryName, String categoryDescription, Category parent)
	{
		String sql = "INSERT INTO category(Category_name, Cat_description, Cat_parent) VALUES (?, ?, ?)";
		
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			statement.setString(1, categoryName);
			statement.setString(2, categoryDescription);
			if(parent != null)
				statement.setInt(3, parent.getCategoryID());
			else
				statement.setNull(3, java.sql.Types.INTEGER);
			
			statement.executeUpdate();
			ResultSet rSet = statement.getGeneratedKeys();
			rSet.next();
			int pkey = rSet.getInt("categoryid");
			Category cat = new Category(pkey, categoryName);
			cat.setCategoryDescription(categoryDescription);
			cat.setParent(parent);
			return cat;
		}
		catch(Exception ex)
		{
			return null;
		}
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
	
	public boolean itemExists(Item item)
	{
		String sql = "SELECT EXISTS (SELECT 1 from sep2xgroup6.item where itemid = ?) as exists";
		try(Connection connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setInt(1, item.getItemID());
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next() && resultSet.getBoolean(1);
		}
		catch(SQLException ex)
		{
			return false;
		}
	}
	
	public boolean buyItem(Account buyer, Item item, int quantity) {
		String buyItemSQL = "INSERT INTO \"sales\"(BuyerID, ItemID, QuantityBought, TotalAmount)" +
							"VALUES(?, ?, ?, ?)";
		String checkForErrorsSQL = "SELECT COUNT(QuantityBought) FROM \"sales\" WHERE ItemID = ?";
		
		try(Connection conn = connect();
	              PreparedStatement pstmt = conn.prepareStatement(buyItemSQL,
	                      Statement.RETURN_GENERATED_KEYS);
	            		  PreparedStatement pstmt2 = conn.prepareStatement(checkForErrorsSQL)) 
		{
			pstmt2.setInt(1, item.getItemID());
			pstmt.setInt(1, buyer.getAccountID());
			pstmt.setInt(2, item.getItemID());
			pstmt.setInt(3, quantity);
			pstmt.setDouble(4, item.getItemPrice() * quantity);
			
			ResultSet result = pstmt2.executeQuery();
			int soldAmount = 0;
			
			if(result.next()) {
				soldAmount = result.getInt(1);
			}
			
			if (quantity <= item.getQuantity() - soldAmount) 
				{
					pstmt.executeUpdate();
					return true; // i have more stuff to sell
				} else {
				return false; // all sold out
			}
		} catch (SQLException ex) {
	          System.out.println(ex.getMessage());
	    }
		return false;
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
	
	public boolean insertItem(Item item, Account seller) {
      String SQL = "INSERT INTO item(itemid, item_name, Category, item_price, description, quantity, Seller)"
              + "VALUES(?,?,?,?,?,?,?,?)";

      try (Connection conn = connect();
              PreparedStatement pstmt = conn.prepareStatement(SQL,
              Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setInt(1, item.getItemID());
          pstmt.setString(2, item.getItemName());
          pstmt.setInt(3, item.getItemCategory().getCategoryID());
          pstmt.setDouble(4, item.getItemPrice());
          pstmt.setString(5, item.getDescription());
          pstmt.setInt(6, item.getQuantity());
          pstmt.setInt(7, seller.getAccountID());
          
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
