package Net.Client;

import java.io.IOException;
import Net.NetMessage;
import Net.SocketHandler;

public class NetClient {
	private int responseTimeout;
	private SocketHandler socketHandler;
	
	public ServerResponse runServerMethod(String method, Object... args) {
		try {
			socketHandler.send(new NetMessage(method, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTimeout(int timeout) {
		this.responseTimeout = timeout;
	}
	
	public int getTimeout() {
		return responseTimeout;
	}
}
