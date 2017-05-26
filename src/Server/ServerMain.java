package Server;

import java.io.IOException;
import java.util.*;
import Net.Server.*;
import Model.*;

public class ServerMain {
	private DBControl database;
	private NetServer networkServer;
	private Map<String, Account> sessionIDs;

	public ServerMain(int listenerPort) throws IOException {
		database = new DBControl();
		networkServer = new NetServer(listenerPort);
		sessionIDs = new HashMap<String, Account>();
		networkServer.addServerMethod(Method.RegisterAccount.getValue(), e -> handleRegisterAccount(e));
		networkServer.addServerMethod(Method.SignIn.getValue(), e -> handleLoginAccount(e));
	}

	private Object[] handleLoginAccount(Object[] args) {
		// input:
		// 0: String - Username
		// 1: String - PasswordHash

		// output:
		// 0: boolean - successful login?
		// 1: String - sessionID

		Object[] response = new Object[2];
		response[0] = false;
		if (args.length == 2) {
			if (args[0] instanceof String && args[1] instanceof String) {
				Account theAccount = database.checkSignin((String) args[0], (String) args[1]);
				if (theAccount != null) {
					response[0] = true;
					String session = UUID.randomUUID().toString();
					response[1] = session;
					sessionIDs.put(session, theAccount);
				}
			}
		}
		return response;
	}

	private Object[] handleRegisterAccount(Object[] args)
	{
		//input:
		//0: Account - accountDetails
		
		//output:
		//0: RegisterAccountStatus - status
		Object[] response = new Object[1];
		response[0] = RegisterAccountStatus.UnknownError;
		if(args.length == 1)
		{
			if(args[0] instanceof Account)
			{
				if(database.registerAccount((Account)args[0]))
					response[0] = RegisterAccountStatus.AccountCreated;
				else
					response[0] = RegisterAccountStatus.UsernameAlreadyExists;
			}
		}
		return response;
	}

	public static void main(String[] args) {

	}

}
