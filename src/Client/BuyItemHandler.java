package Client;

import Model.BuyItemStatus;
import Model.MethodStatus;

public interface BuyItemHandler {
	void handle(MethodStatus status, BuyItemStatus state);
}
