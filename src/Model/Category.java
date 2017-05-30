package Model;

public class Category
{
	private int categoryID; //probably won't be used
	private String categoryName;
	private String categoryDescription;
	private Category parent;
	private boolean hasChildren;
	private boolean hasParent;
	
	public Category(String categoryName)
	{
		this.categoryName = categoryName;
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

	public boolean isHasChildren()
	{
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	public boolean isHasParent()
	{
		return hasParent;
	}

	public void setHasParent(boolean hasParent)
	{
		this.hasParent = hasParent;
	}
}
