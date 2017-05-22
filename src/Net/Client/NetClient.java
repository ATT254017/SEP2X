package Net.Client;

import java.io.IOException;
import java.util.HashMap;
import Net.NetMessage;
import Net.SocketHandler;

public class NetClient {
	private int responseTimeout;
	private SocketHandler socketHandler;
	private int requestNum = 0;
	
	public ServerResponse runServerMethod(String method, Object... args) {
		int thisRequestID = requestNum++;
		HashMap<String, Object> params = new HashMap<>();
		params.put("REQUEST_ID", thisRequestID);
		
		for (int i = 0; i < args.length; i++)
		{
			params.put("PARAM" + Integer.toString(i), args[i]);
		}
		
		try {
			socketHandler.send(new NetMessage(method, params));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ServerResponse response = new ServerResponse(thisRequestID, responseTimeout);
		socketHandler.addDataReceivedActionListener(response);
		return response;
	}
	
	public void setTimeout(int timeout) {
		this.responseTimeout = timeout;
	}
	
	public int getTimeout() {
		return responseTimeout;
	}
}
