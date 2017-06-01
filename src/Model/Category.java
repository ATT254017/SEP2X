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

	public Category setCategoryDescription(String categoryDescription)
	{
		this.categoryDescription = categoryDescription;
		return this;
	}

	public Category getParent()
	{
		return parent;
	}

	public Category setParent(Category parent)
	{
		this.parent = parent;
		this.hasParent = parent != null;
		return this;
	}

	public boolean hasParent()
	{
		return hasParent;
	}
	
	public Category setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
		return this;
	}
	
	public boolean hasChildren()
	{
		return this.hasChildren;
	}

}
