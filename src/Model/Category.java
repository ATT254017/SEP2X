package Model;

public class Category
{
	private int categoryID; 
	private String categoryName;
	private String categoryDescription;
	private Category parent;
	private boolean hasParent;
	
	public Category(int categoryID, String categoryName)
	{
		this.categoryID = categoryID;
		this.categoryName = categoryName;
	}
	
	public int getCategoryID()
	{
		return this.categoryID;
	}
	
	public String getCategoryName()
	{
		return categoryName;
	}
	
	public String getCategoryDescription()
	{
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription)
	{
		this.categoryDescription = categoryDescription;
	}

	public Category getParent()
	{
		return parent;
	}

	public void setParent(Category parent)
	{
		this.parent = parent;
	}

	public boolean hasParent()
	{
		return hasParent;
	}

	public void setHasParent(boolean hasParent)
	{
		this.hasParent = hasParent;
	}
}
