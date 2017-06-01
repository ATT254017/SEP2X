import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import Client.*;
import Model.Account;
import Model.Category;
import Model.Item;
import Model.ItemState;
import Model.MethodStatus;
import Model.Person;
import Model.SalesReceipt;

public class TestClient
{

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{

		/*
		 * TimeOut timeOut = new TimeOut(50000); long starttime =
		 * System.nanoTime(); timeOut.addTimeOutEventListener(() ->
		 * System.out.println("Timed out after: " + (System.nanoTime() -
		 * starttime) / 1e6));
		 * 
		 * Thread.sleep(200000);
		 */
		ClientControl clientControl = ClientControl.getInstance();
		clientControl.setServerConnectionDetails("localhost", 9999);

		clientControl.signIn("pineapplemastah", "theyreawesomel0l", (status, loginSuccess, q) ->
		{
			clientControl.getItems((status2, list) ->
			{
				for(Item item : list)
				{
					System.out.println(item.getItemName());
				}
			});
			
			if (loginSuccess)
			{
				System.out.println("logged in");
				/*clientControl.signOut(() ->
				{*/
				
				
				/*
				clientControl.getBuyHistory((status2, list) -> 
				{
					for(SalesReceipt blah : list)
					{
						System.out.println("TIME: " + blah.getBuyTime());
						System.out.println("Item: " + blah.getItemBought().getItemName());
						System.out.println("Qty: " + blah.getItemBought().getInitialQuantity());
						System.out.println("Remain Qty: " + blah.getItemBought().getCurrentRemainingQuantity());
						System.out.println("Bought Qty: " + blah.getQuantityBought());
						System.out.println("Amount due: " + blah.getAmountDue());
						System.out.println();
					}
				});*/
					/*clientControl.getItems(new Category(0, "Art"), "turd", (itemsStatus, list) ->
					{
						if (status == MethodStatus.SuccessfulInvocation)
						{

							clientControl.buyItem(list.get(0), 3, (st, b) ->
							{
								System.out.println(st);
								System.out.println(b);
							});

							System.out.println();
							for (Item item : list)
							{
								System.out.println("ITEM: " + item.getItemName());
								System.out.println("Category: " + item.getItemCategory().getCategoryName());
								System.out.println("Seller: " + item.getSeller().getUserName());
								System.out.println("remain: " + item.getCurrentRemainingQuantity());

							}
						}
					});*/

				//});
			}
		});

		/*
		 * clientControl.getCategories(null, (status, list) -> {
		 * System.out.println(status); if(status ==
		 * MethodStatus.SuccessfulInvocation) for(Category category : list) {
		 * if(category.hasChildren()) { clientControl.getCategories(category,
		 * (status2, list2) -> { if(status2 ==
		 * MethodStatus.SuccessfulInvocation) for(Category category2 : list2) {
		 * if(category2.hasChildren()) { clientControl.getCategories(category2,
		 * (status3, list3) -> { if(status3 ==
		 * MethodStatus.SuccessfulInvocation) for(Category category3 : list3) {
		 * System.out.println(category3.getParent().getParent().getCategoryName(
		 * ) + " haschild: " + category3.getParent().getParent().hasChildren());
		 * System.out.println("\t CHILD: " +
		 * category3.getParent().getCategoryName() + " haschild: " +
		 * category3.getParent().hasChildren());
		 * System.out.println("\t\t CHILD:" + category3.getCategoryName() +
		 * " haschild: " + category3.hasChildren()); } }); } else {
		 * System.out.println(category2.getParent().getCategoryName() +
		 * " haschild: " + category2.getParent().hasChildren());
		 * System.out.println("\t CHILD: " + category2.getCategoryName()); } }
		 * 
		 * }); } else System.out.println(category.getCategoryName() +
		 * " haschild: " + category.hasChildren());
		 * 
		 * } });
		 */
		/*
		 * 
		 * clientControl.registerAccount(new Account(332, "myusername",
		 * "myemail@email.com", new Person("firstname", "lastName", "address",
		 * 88888888, true, LocalDate.now())) , "mypass", (Status , derp) -> {
		 * System.out.println("MStatus: " + Status);
		 * System.out.println("RegStatus: " + derp); System.out.println();
		 * 
		 * clientControl.signIn("myusername", "mypass", (stat, boo, str) -> {
		 * System.out.println("MStatus: " + stat);
		 * System.out.println("Logged in: " + boo);
		 * System.out.println("SessionID: " + str);
		 * 
		 * clientControl.insertItem(new Item(666, "itemName", 345,
		 * "description", ItemState.Sold, 1), (status, state) -> {
		 * System.out.println("insert item: " + status + " - " + state); });
		 * 
		 * clientControl.buyItem(new Item(999, "itemName", 345, "description",
		 * ItemState.Sold, 1), 1, (status, state) -> {
		 * System.out.println("buy item: " + status + " - " + state); });
		 * 
		 * clientControl.buyItem(new Item(666, "itemName", 345, "description",
		 * ItemState.Sold, 1), 1, (status, state) -> {
		 * System.out.println("buy item: " + status + " - " + state); });
		 * 
		 * clientControl.makeOffer(new Item(999, "itemName", 345, "description",
		 * ItemState.Sold, 1), 234, (ee, rr) -> {
		 * System.out.println("make offer: " + ee); }); }); });
		 */

		Thread.sleep(2000);

		clientControl.disconnect();
		/*
		 * clientControl.signIn("myuser", "pwd", (status, blah) -> {
		 * System.out.println(status); System.out.println("args:"); if(blah !=
		 * null) for(int i = 0; i < blah.length; i++)
		 * System.out.println(blah[i]);
		 * 
		 * });
		 */
		/*
		 * clientControl.runServerMethod(Method.RegisterAccount, (status, blah)
		 * -> { System.out.println(status); System.out.println("args:"); if(blah
		 * != null) for(int i = 0; i < blah.length; i++)
		 * System.out.println(blah[i]);
		 * 
		 * }, new Account("MyUserName", "fdlgdfgk", "email", new Person(0,
		 * "firstName", "lastName", "address", 234, false)));
		 * 
		 * clientControl.runServerMethod(Method.CreateListing, (status, blah) ->
		 * { System.out.println(status); System.out.println("args:"); if(blah !=
		 * null) for(int i = 0; i < blah.length; i++)
		 * System.out.println(blah[i]); }, null);
		 */
		/*
		 * Object[] serverResponseVars = response.awaitResponse(); Object[]
		 * serverResponseVars2 = response2.awaitResponse();
		 * System.out.println("Server returned: "); if(serverResponseVars !=
		 * null) for(int i = 0; i < serverResponseVars.length; i++)
		 * System.out.println(serverResponseVars[i].toString());
		 * 
		 * System.out.println("Server returned: "); if(serverResponseVars2 !=
		 * null) for(int i = 0; i < serverResponseVars2.length; i++)
		 * System.out.println(serverResponseVars2[i].toString());
		 */
	}

}
