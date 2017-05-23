import java.io.IOException;
import java.net.UnknownHostException;

import Net.Client.NetClient;
import Net.Client.ServerResponse;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		NetClient client = new NetClient("localhost", 9999);
		ServerResponse response = client.runServerMethod("Meth", "test1", "test3");
		System.out.println(response.awaitResponse()[0]);
	}

}
