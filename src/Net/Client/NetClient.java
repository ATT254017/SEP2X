package Net.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import Net.NetMessage;
import Net.SocketHandler;

public class NetClient {
	private int responseTimeout;
	private SocketHandler socketHandler;
	private int requestNum = 0;
	
	public NetClient(String host, int port) throws UnknownHostException, IOException
	{
		Socket socket = new Socket(host, port);
		socketHandler = new SocketHandler(socket);
		this.responseTimeout = 5000;
	}
	
	public void disconnect()
	{
		socketHandler.closeConnection();
	}

	public ServerResponse runServerMethod(String method, Object... args) {
		int thisRequestID = requestNum++;
		ServerResponse response = new ServerResponse(thisRequestID, responseTimeout);
		socketHandler.addDataReceivedListener(response);
		
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
			return null;
		}
		
		return response;
	}
	
	public void setTimeout(int timeout) {
		this.responseTimeout = timeout;
	}
	
	public int getTimeout() {
		return responseTimeout;
	}
}
