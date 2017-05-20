package Net.Client;

import Net.DataReceivedAction;
import Net.NetMessage;
import Net.SocketHandler;

public class ServerResponse implements DataReceivedAction {
	private int id;
	private Object[] response;
	private ResponseStatus status;
	private int timeout;
	
	public ServerResponse(int id, int timeout) {
		this.id = id;
		this.timeout = timeout;
	}
	
	public Object[] awaitResponse() {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseStatus getStatus() {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public void dataReceived(SocketHandler socket, NetMessage net) {
		
	}
}
