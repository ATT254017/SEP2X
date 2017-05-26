import java.io.IOException;
import java.net.UnknownHostException;

import Net.Client.NetClient;
import Net.Client.ServerResponse;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		NetClient client = new NetClient("localhost",  9999);
		ServerResponse response = client.runServerMethod("Method1", "This", "Client", "is", "awesome!!");
		ServerResponse response2 = client.runServerMethod("Method2", "David", "is", "a FAGG!!");
		
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
