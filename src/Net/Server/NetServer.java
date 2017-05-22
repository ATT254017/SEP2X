package Net.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Net.DataReceivedAction;
import Net.NetMessage;
import Net.SocketHandler;

public class NetServer implements Runnable, DataReceivedAction {
	private boolean isConnected = true;
	private Map<String, ServerMethodHandler> methodHandlers;
	private ServerSocket listenerSocket;
	private ArrayList<SocketHandler> clientConnections;
	private Thread runner;
	
	public NetServer(int port) throws IOException {
		listenerSocket = new ServerSocket(port);
		runner = new Thread(this);
		runner.start();
	}
	
	@Override
	public void run() {
		while(isConnected) {
			try {
				Socket connectionSocket = listenerSocket.accept();
				SocketHandler handlerConnection = new SocketHandler(connectionSocket);
				clientConnections.add(handlerConnection);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		isConnected = false;
	}
	
	public void addServerMethod(String method, ServerMethodHandler handler) {
		methodHandlers.put(method, handler);
	}
	
	public void removeServerMethod(String method) {
		methodHandlers.remove(method);
	}
	
	@Override
	public void dataReceived(SocketHandler socket, NetMessage net) {
		if(methodHandlers.containsKey(net.getMessage()))
		{
			if(net.getParams().containsKey("REQUEST_ID"))
			{
				int reqID = (int)net.getParams().get("REQUEST_ID");
				Object[] methodReturn = methodHandlers.get(net.getMessage()).ServerMethod(getParameters(net.getParams()));
				
				//now send back the reply:
				Map<String, Object> responseParams = new HashMap<String, Object>();
				responseParams.put("REQUEST_ID", reqID);
				for(int i = 0; i < methodReturn.length; i++)
					responseParams.put("RESP" + Integer.toString(i), methodReturn[i]);
				NetMessage response = new NetMessage("RESPONSE", responseParams);
				try {
					socket.send(response);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
			}
		}
	}
	private Object[] getParameters(Map<String, Object> params)
	{
		ArrayList<Object> temp = new ArrayList<Object>();
		for(int i = 0; true; i++)
		{
			String key = "PARAM" + Integer.toString(i);
			if(params.containsKey(key))
				temp.add(params.get(key));
			else
				break;
		}
		return temp.toArray(new Object[0]);
	}
	
}
