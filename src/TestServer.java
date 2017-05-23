import java.io.IOException;

import Net.Server.NetServer;
import Net.Server.ServerMethodHandler;

public class TestServer {

	public static void main(String[] args) throws IOException {
		NetServer server = new NetServer(9999);
		System.out.println("Server started");
		server.addServerMethod("Method1", new ServerMethodHandler() {
			
			@Override
			public Object[] ServerMethod(Object[] args) {
				System.out.println("CLIENT INVOKING THIS METHOD!!!");
				System.out.println("They sent:");
				if(args != null)
					for(int i = 0; i < args.length;i++)
						System.out.println(args[i].toString());
				
				return new Object[] { "sup mah bra." };
			}
		});

		server.addServerMethod("Method2", new ServerMethodHandler() {
			@Override
			public Object[] ServerMethod(Object[] args) {
				System.out.println("CLIENT INVOKED THIS FUCKING METHOD!");
				System.out.println("They sent:");
				if(args != null)
					for(int i = 0; i < args.length;i++)
						System.out.println(args[i].toString());

				return new Object[] { "sup mah bra." };
			}
		});

		System.out.println("Server method registered");
	}

}
