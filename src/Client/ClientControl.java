package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import Model.Account;
import Model.Method;
import Model.Person;
import Net.Client.NetClient;
import Net.Client.ResponseStatus;
import Net.Client.ServerResponse;
import Net.Client.StatusListener;

public class ClientControl {
	private static ClientControl instance = null;
	private static final Object initInstanceLock = new Object();
	private NetClient networkClient;
	private String userSessionID;

	private ClientControl() {
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
	
	public void registerAccount(Account account, MethodResponseHandler handler)
	{
		runServerMethod(Method.RegisterAccount, handler, account);
	}
	
	public void signIn(String username, String password, MethodResponseHandler handler)
	{
		runServerMethod(Method.SignIn, handler, username, password);
	}
	
	public void runServerMethod(Method m, MethodResponseHandler responseHandler, Object... args)
	{
		Object[] args2 = args;
		if(m.requiresLogin())
		{
			if(userSessionID != null)
			{
				args2 = new Object[args.length + 1];
				for(int i = 0; i < args.length; i++)
					args2[i+1] = args[i];
				
				args2[0] = userSessionID;
				
			}
			else
			{
				//not authorized
			}
		}
		
		networkClient.runServerMethod(m.getValue(), args2).addStatusListener(new StatusListener() 
		{
			@Override
			public void statusChangeEvent(ServerResponse sender) {
				sender.removeStatusListener(this);
				if(m == Method.SignIn && sender.getStatus() == ResponseStatus.Ready)
				{
					//response should contain a sessionID
					Object[] rspArgs = sender.getResponse();
					if(rspArgs.length == 2)
					{
						if(rspArgs[0] instanceof Boolean && (boolean)rspArgs[0])
						{
							userSessionID = (String)rspArgs[1];
						}
					}
				}
				responseHandler.handle(sender.getResponse());
			}
		});
	}

}
