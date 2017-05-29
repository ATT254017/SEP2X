package Client;

import Model.MethodStatus;

public interface SignInResponseHandler
{
	void handle(MethodStatus status, Boolean isSignedIn, String sessionID);
}
