package Client;

import java.time.LocalDateTime;
import java.util.Map;

import Model.Item;
import Model.MethodStatus;

public interface GetBuyHistoryResponseHandler
{
	void handle(MethodStatus status, Map<LocalDateTime, Item> items);
}
