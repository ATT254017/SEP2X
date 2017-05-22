package Client;

public class ClientControl {
	private static ClientControl instance = null;
	
	public ClientControl() {
	}
	
	public static ClientControl getInstance() {
		if (instance == null) {
			instance = new ClientControl();
		}
		return instance;
	}
}
