package Net.Client;

import java.util.ArrayList;
import java.util.Map;
import Net.DataReceivedAction;
import Net.NetMessage;
import Net.SocketHandler;

public class ServerResponse implements DataReceivedAction, TimeOutEvent {
	private int id;
	private Object[] response;
	private ResponseStatus status;
	private ArrayList<StatusListener> statusListeners;
	private TimeOut timeOut;

	public ServerResponse(int id, int timeout) {
	    timeOut = new TimeOut(timeout);
	    timeOut.addTimeOutEventListener(this);
		this.id = id;
		this.status = ResponseStatus.InProgress;
		statusListeners = new ArrayList<>();
	}

/*
	public Object[] awaitResponse() throws InterruptedException {
		while (status == ResponseStatus.InProgress && !timedOut())
			Thread.sleep(1);
		return response;
	}
*/

	public ResponseStatus getStatus() {
		return status;
	}

/*
	public void setTimeout(int timeout) {
		this.timeout = LocalDateTime.now().plusNanos((long) (timeout * 1e6));
	}
*/

	public void addStatusListener(StatusListener statusListener) {
		statusListeners.add(statusListener);
	}

	public void removeStatusListener(StatusListener statusListener) {
		statusListeners.add(statusListener);
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
					fireStatusChangedEvent();
					return;
				}
			}
		}
		/*
		if(status == ResponseStatus.InProgress && timedOut())
		{
			socket.removeDataReceivedListener(this); 
			status = ResponseStatus.Timeout;
			fireStatusChangedEvent();
		}
		*/
	}

	public void fireStatusChangedEvent() {
		for (int i = statusListeners.size() - 1; i > -1; i--) {
			StatusListener subscriber = statusListeners.get(i);
			if (subscriber != null) {
				subscriber.statusChangeEvent(this);
			}
		}
	}

	private Object[] getParameters(Map<String, Object> params) {
		ArrayList<Object> temp = new ArrayList<>();
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
    public void timeOutElapsedAction() {
        status = ResponseStatus.Timeout;
        fireStatusChangedEvent();
    }
}
