package Client;

import java.util.List;

import Model.Item;
import Model.MethodStatus;

public interface GetItemsResponseHandler
{
	void handle(MethodStatus status, List<Item> items);
}
