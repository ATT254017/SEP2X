import java.io.IOException;
import java.net.UnknownHostException;

import Net.Client.NetClient;
import Net.Client.ServerResponse;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		NetClient client = new NetClient("localhost",  9999);
		ServerResponse response = client.runServerMethod("Method1", "This", "Client", "is", "awesome!!");
		
		Object[] serverResponseVars = response.awaitResponse();
		System.out.println("Server returned: ");
		if(serverResponseVars != null)
			for(int i = 0; i < serverResponseVars.length; i++)
				System.out.println(serverResponseVars[i].toString());
	}

}
