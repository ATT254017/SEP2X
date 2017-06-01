package Client;

import java.util.List;

import Model.MethodStatus;
import Model.SalesReceipt;

public interface GetBuyHistoryResponseHandler
{
	void handle(MethodStatus status, List<SalesReceipt> items);
}
