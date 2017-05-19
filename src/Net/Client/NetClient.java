package Net.Client;

import Net.SocketHandler;

public class NetClient {
	private int responseTimeout;
	private SocketHandler socketHandler;
	
	public ServerResponse runServerMethod(String method, Object... args) {
		
	}
	
	public void setTimeout(int timeout) {
		this.responseTimeout = timeout;
	}
	
	public int getTimeout() {
		return responseTimeout;
	}
}
