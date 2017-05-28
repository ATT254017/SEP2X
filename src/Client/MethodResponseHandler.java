package Client;

import Model.MethodStatus;

public interface MethodResponseHandler {
	void handle(MethodStatus status, Object[] args);
}
