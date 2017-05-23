package Net.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import Net.DataReceivedAction;
import Net.NetMessage;
import Net.SocketHandler;

public class ServerResponse implements DataReceivedAction, StatusListener {
	private int id;
	private Object[] response;
	private ResponseStatus status;
	private LocalDateTime timeout;

	public ServerResponse(int id, int timeout) {
		this.id = id;
		this.status = ResponseStatus.InProgress;
		setTimeout(timeout);
	}

	public Object[] awaitResponse() throws InterruptedException {
		while (status == ResponseStatus.InProgress && !timedOut())
			Thread.sleep(1);
		return response;
	}
	
	private boolean timedOut()
	{
		return LocalDateTime.now().isAfter(timeout);
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setTimeout(int timeout) {
		this.timeout = LocalDateTime.now().plusNanos((long) (timeout * 1e6));
	}

	@Override
	public void dataReceived(SocketHandler socket, NetMessage net) {
		Map<String, Object> params = net.getParams();
		if (net.getMessage().equals("RESPONSE")) {
			if (params.containsKey("REQUEST_ID")) {
				int reqID = (int) params.get("REQUEST_ID");
				if (reqID == this.id) {
					// the response i was looking for.
					socket.removeDataReceivedListener(this); 	// i don't care
																// about any further
																// messages
					response = getParameters(params);
					status = ResponseStatus.Ready;
					return;
				}
			}
		}
		if(status == ResponseStatus.InProgress && timedOut())
		{
			socket.removeDataReceivedListener(this); 
			status = ResponseStatus.Timeout;
		}
	}

	private Object[] getParameters(Map<String, Object> params) {
		ArrayList<Object> temp = new ArrayList<Object>();
		for (int i = 0; true; i++) {
			String key = "RESP" + Integer.toString(i);
			if (params.containsKey(key))
				temp.add(params.get(key));
			else
				break;
		}
		return temp.toArray(new Object[0]);
	}

	@Override
	public void statusChangeEvent(ServerResponse sender) {
		if (this == sender) {

		}
	}
}
