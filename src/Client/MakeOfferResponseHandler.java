package Client;

import Model.MakeOfferStatus;
import Model.MethodStatus;

public interface MakeOfferResponseHandler
{
	void handle(MethodStatus status, MakeOfferStatus state);
}
