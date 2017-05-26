package Client;

import java.io.IOException;
import java.net.UnknownHostException;


import Model.Methods;
import Net.Client.NetClient;
import Net.Client.ServerResponse;
import Net.Client.StatusListener;

public class ClientControl {
	private static ClientControl instance = null;
	private static final Object initInstanceLock = new Object();
	private NetClient networkClient;

	public ClientControl() {
	}
	
	public void setServerConnectionDetails(String host, int port) throws UnknownHostException, IOException
	{
		networkClient = new NetClient(host, port);
	}

	public static ClientControl getInstance() {
		if (instance == null) {
			synchronized (initInstanceLock) {
				if (instance == null) {
					instance = new ClientControl();
				}
			}
		}
		return instance;
	}
	
	public void runServerMethod(Methods m, MethodResponseHandler responseHandler, Object... args)
	{
		ServerResponse response = networkClient.runServerMethod(m.getValue(), args);
		response.addStatusListener(new StatusListener() {
			
			@Override
			public void statusChangeEvent(ServerResponse sender) {
				sender.removeStatusListener(this);
				responseHandler.handle(sender.getResponse());
			}
		});
	}

}
