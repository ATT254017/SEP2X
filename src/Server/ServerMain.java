package Server;

import java.io.IOException;
import java.util.Map;
import Net.Server.*;
import Model.*;

public class ServerMain {
	private DBControl database;
	private NetServer networkServer;
	private Map<String, Account> sessionIDs;
	public ServerMain(int listenerPort) throws IOException {
		database = new DBControl();
		networkServer = new NetServer(listenerPort);
		sessionIDs = new Map<String, Account>();
		networkServer.addServerMethod("REGISTER_ACCOUNT", e -> handleRegisterAccount(e));
		networkServer.addServerMethod("LOGIN_ACCOUNT", e -> handleLoginAccount(e));
	}
	
	private Object[] handleLoginAccount(Object[] args)
	{
		//0: Username
		//1: PasswordHash
		
	}
	
	private Object[] handleRegisterAccount(Object[] args)
	{
		return null;
	}

	public static void main(String[] args) {
		
	}

}
