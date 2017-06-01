package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SalesReceipt implements Serializable
{
	private Item itemBought;
	private LocalDateTime buyTime;
	private int quantityBought;
	private double amountDue;
	public SalesReceipt(Item itemBought, LocalDateTime buyTime, int quantityBought, double amountDue)
	{
		this.itemBought = itemBought;
		this.buyTime = buyTime;
		this.quantityBought = quantityBought;
		this.amountDue = amountDue;
	}
	public Item getItemBought()
	{
		return itemBought;
	}
	public LocalDateTime getBuyTime()
	{
		return buyTime;
	}
	public int getQuantityBought()
	{
		return quantityBought;
	}
	public double getAmountDue()
	{
		return amountDue;
	}
	
}
