import java.io.IOException;
import java.net.UnknownHostException;

import Client.*;
import Model.Account;
import Model.Methods;
import Model.Person;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		ClientControl clientControl = ClientControl.getInstance();
		clientControl.setServerConnectionDetails("localhost", 9999);
		
		clientControl.runServerMethod(Methods.RegisterAccount, blah ->
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			System.out.println(Thread.currentThread().getId() + ": I'm out");
			
		}, new Account("MyUserName", "fdlgdfgk", "email", new Person(0, "firstName", "lastName", "address", 234, false)));
		
		
		System.out.println(Thread.currentThread().getId() + ": I can haz response?");
		
		/*
		Object[] serverResponseVars = response.awaitResponse();
		Object[] serverResponseVars2 = response2.awaitResponse();
		System.out.println("Server returned: ");
		if(serverResponseVars != null)
			for(int i = 0; i < serverResponseVars.length; i++)
				System.out.println(serverResponseVars[i].toString());

		System.out.println("Server returned: ");
		if(serverResponseVars2 != null)
			for(int i = 0; i < serverResponseVars2.length; i++)
				System.out.println(serverResponseVars2[i].toString());*/
	}

}
