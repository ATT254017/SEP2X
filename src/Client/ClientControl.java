package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import Model.Account;
import Model.BuyItemStatus;
import Model.Category;
import Model.Item;
import Model.Method;
import Model.MethodStatus;
import Model.RegisterAccountStatus;
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
	{ }

	public void setServerConnectionDetails(String host, int port) throws UnknownHostException, IOException
	{
		networkClient = new NetClient(host, port);
	}
	
	public void disconnect()
	{
		networkClient.disconnect();
	}

	public static ClientControl getInstance()
	{
		if (instance == null)
			synchronized (initInstanceLock)
			{
				if (instance == null)
					instance = new ClientControl();
			}
		return instance;
	}
	
	public void makeOffer(Item item, double offerPrice, MakeOfferResponseHandler handler)
	{
		runServerMethod(Method.MakeOffer, (status, args) -> 
		{
			boolean arg1 = status != MethodStatus.TimedOut ? (Boolean)args[0] : false;
			handler.handle(status, arg1);
		}, item, offerPrice);
	}

	public void registerAccount(Account account, String password, RegisterAccountResponseHandler handler)
	{
		runServerMethod(Method.RegisterAccount, (status, args) -> 
		{
			RegisterAccountStatus arg1 = status != MethodStatus.TimedOut ? (RegisterAccountStatus)args[0] : null;
			handler.handle(status, arg1);
		}, account, password);
	}

	public void buyItem(Item item, int quantity, BuyItemHandler handler) {
		runServerMethod(Method.BuyItem, (status, args) ->
		{
			BuyItemStatus arg1 = (status != MethodStatus.TimedOut ? (BuyItemStatus)args[0] : null);
			handler.handle(status, arg1);
		}, item, quantity);
	}
	
	public void signIn(String username, String password, SignInResponseHandler handler)
	{
		runServerMethod(Method.SignIn, (status, args) -> 
		{
			boolean arg1 = status != MethodStatus.TimedOut ? (Boolean)args[0] : false;
			String arg2 = status != MethodStatus.TimedOut ? (String)args[1] : null;
			handler.handle(status, arg1, arg2);
		}, username, password);
	}
	
	public void getCategories(Category parent, GetCategoriesResponseHandler handler)
	{
		runServerMethod(Method.GetCategories, (status, args) ->
		{
			List<Category> arg1 = status != MethodStatus.TimedOut ? (List<Category>)args[0] : null;
			handler.handle(status, arg1);
		}, parent);
	}
	
	/*public void getAccount(String username, MethodResponseHandler handler)
	{
		runServerMethod(Method.GetAccount, handler, username);
	}*/

	private void runServerMethod(Method m, MethodResponseHandler responseHandler, Object... args)
	{
		Object[] args2 = args;
		if (m.requiresLogin())
		{
			if (userSessionID != null)
			{
				args2 = new Object[args.length + 1];
				System.arraycopy(args, 0, args2, 1, args.length); 	// shift all elements up by 1
				args2[0] = userSessionID; 							// to make room for the session ID
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
								status = MethodStatus.SuccessfulInvocation;
							}
							else
								status = MethodStatus.Unauthorized;
						}
					}
					else
						status = MethodStatus.SuccessfulInvocation;
					
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
