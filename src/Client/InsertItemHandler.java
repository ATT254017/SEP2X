package Client;

import Model.InsertItemStatus;
import Model.MethodStatus;

public interface InsertItemHandler {
	void handle(MethodStatus status, InsertItemStatus state);
}
