import java.io.IOException;

import Net.Server.NetServer;
import Net.Server.ServerMethodHandler;

public class TestServer {

	public static void main(String[] args) throws IOException {
		NetServer server = new NetServer(9999);
		server.addServerMethod("Meth", new ServerMethodHandler() {
			
			@Override
			public Object[] ServerMethod(Object[] args) {
				System.out.println("SERVER: ");
				for(int i = 0; i < args.length; i++)
					System.out.println("ARG: " + args[i].toString());
				
				return null;
			}
		});
	}

}
