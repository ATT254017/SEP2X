package Client;

import Model.MethodStatus;
import Model.RegisterAccountStatus;

public interface RegisterAccountResponseHandler
{
	void handle(MethodStatus mStatus, RegisterAccountStatus rStatus);
}
