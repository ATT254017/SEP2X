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
			clientControl.makeOffer(new Item(999, "itemName", 345, 1), 10, (stat, state) ->
			{
				System.out.println(stat + " - " + state);
			});
			
			clientControl.getItems((status2, list) ->
			{
				for(Item item : list)
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

		Thread.sleep(2000);

		clientControl.disconnect();
		
	}

}
