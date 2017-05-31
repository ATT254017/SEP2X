package Model;

import java.io.Serializable;

public class Category implements Serializable
{
	private int categoryID; 
	private String categoryName;
	private String categoryDescription;
	private Category parent;
	private boolean hasParent;
	private boolean hasChildren;
	
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
		this.hasParent = parent != null;
	}

	public boolean hasParent()
	{
		return hasParent;
	}
	
	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}
	
	public boolean hasChildren()
	{
		return this.hasChildren;
	}

}
