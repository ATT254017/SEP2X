package Net.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
		
	}
	
}
