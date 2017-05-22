import java.io.IOException;
import java.net.UnknownHostException;

import Net.Client.NetClient;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		NetClient client = new NetClient("localhost", 9999);
		client.runServerMethod("Meth", "test1", "test3");
	}

}
