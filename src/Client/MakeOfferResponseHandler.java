package Client;

import Model.MethodStatus;

public interface MakeOfferResponseHandler
{
	void handle(MethodStatus status, Boolean isOfferPlaced);
}
