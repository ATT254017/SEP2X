package Client;

import Model.MethodStatus;
import Model.RegisterAccountStatus;

public interface RegisterAccountHandler
{
	void handle(MethodStatus mStatus, RegisterAccountStatus rStatus);
}
