package Client;

import java.io.IOException;
import java.net.UnknownHostException;

import Model.Account;
import Model.Method;
import Model.MethodStatus;
import Net.Client.NetClient;
import Net.Client.ResponseStatus;
import Net.Client.ServerResponse;
import Net.Client.StatusListener;

public class ClientControl
{
	private static ClientControl instance = null;
	private static final Object initInstanceLock = new Object();
	private NetClient networkClient;
	private String userSessionID;

	private ClientControl()
	{
		
	}

	public void setServerConnectionDetails(String host, int port) throws UnknownHostException, IOException
	{
		networkClient = new NetClient(host, port);
	}

	public static ClientControl getInstance()
	{
		if (instance == null)
		{
			synchronized (initInstanceLock)
			{
				if (instance == null)
				{
					instance = new ClientControl();
				}
			}
		}
		return instance;
	}

	public void registerAccount(Account account, String password, MethodResponseHandler handler)
	{
		runServerMethod(Method.RegisterAccount, handler, account, password);
	}

	public void signIn(String username, String password, MethodResponseHandler handler)
	{
		runServerMethod(Method.SignIn, handler, username, password);
	}
	
	public void getAccount(String username, MethodResponseHandler handler)
	{
		runServerMethod(Method.GetAccount, handler, username);
	}

	public void runServerMethod(Method m, MethodResponseHandler responseHandler, Object... args)
	{
		Object[] args2 = args;
		if (m.requiresLogin())
		{
			if (userSessionID != null)
			{
				args2 = new Object[args.length + 1];
				for (int i = 0; i < args.length; i++)
					args2[i + 1] = args[i];

				args2[0] = userSessionID;
			}
			else
			{
				// not authorized
				// no point in even trying; just run with unauthorized status..
				responseHandler.handle(MethodStatus.Unauthorized, null);
				return;
			}
		}
		networkClient.runServerMethod(m.getValue(), args2).addStatusListener(new StatusListener()
		{
			@Override
			public void statusChangeEvent(ServerResponse sender)
			{
				sender.removeStatusListener(this);
				MethodStatus status = MethodStatus.UnknownError;
				if (sender.getStatus() == ResponseStatus.Ready)
				{
					if (m == Method.SignIn)
					{
						// response should contain a sessionID
						Object[] rspArgs = sender.getResponse();
						if (rspArgs.length == 2)
						{
							if (rspArgs[0] instanceof Boolean && (boolean) rspArgs[0])
							{
								userSessionID = (String) rspArgs[1];
								status = MethodStatus.Success;
							}
							else
								status = MethodStatus.Unauthorized;
						}
					}
					else
						status = MethodStatus.Success;
					
					if(m.requiresLogin())
					{
						//we might have gotten a deauthorize message from server?
					}
				}
				else if(sender.getStatus() == ResponseStatus.Timeout)
					status = MethodStatus.TimedOut;
				
				if(status == MethodStatus.Unauthorized)
					userSessionID = null;
					
				responseHandler.handle(status, sender.getResponse());
			}
		});
	}
}
