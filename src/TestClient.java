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
		ClientControl clientControl = ClientControl.getInstance();
		clientControl.setServerConnectionDetails("localhost", 9999);

		clientControl.signIn("awesome1337", "youllneverguess13", (status, loginSuccess, q) ->
		{
			clientControl.getCategories(null, (stat, list) ->
			{
			clientControl.insertItem("Dildo", "35cm!", 1, 12.90, list.get(0), (stat2, state) ->
			{
				System.out.println(stat + " - " + state);
			}
			);
			clientControl.getItems(list.get(0), null, null, (statu, list4) ->
			{
				
			clientControl.makeOffer(list4.get(0), 10, (stat3, state) ->
			{
				System.out.println(stat3 + " - " + state);
			
			clientControl.getItems((status2, list2) ->
			{
				for(Item item : list2)
				{
					System.out.println(item.getItemName());

					
					System.out.println(item.getItemState());
					/*clientControl.cancelSellItem(item, (status3, blah)->
					{
						System.out.println(status3);
						System.out.println(blah);
					});*/
				}
			});
			});
			});
			});
			
		});

		Thread.sleep(2000);

		clientControl.disconnect();
		
	}

}
