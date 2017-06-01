package Client;

import Model.CancelSellItemStatus;
import Model.MethodStatus;

public interface CancelSellItemResponseHandler
{
	void handle(MethodStatus status, CancelSellItemStatus cStatus);
}
