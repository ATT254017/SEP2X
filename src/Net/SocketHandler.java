package Net;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandler implements Runnable {
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Thread runner;
	private boolean running;
	private ArrayList<DataReceivedAction> dataReceivedActionListeners;
	private ArrayList<DisconnectedAction> disconnectedActionListeners;
	
	public SocketHandler(Socket connection) throws IOException {
		running = true;
		dataReceivedActionListeners = new ArrayList<>();
		disconnectedActionListeners = new ArrayList<>();
		outputStream = new ObjectOutputStream(connection.getOutputStream());
		inputStream = new ObjectInputStream(connection.getInputStream());
		runner = new Thread(this);
		runner.start();
	}
	
	public void closeConnection()
	{
		running = false;
		try
		{
			inputStream.close(); //closing the stream should close the underlying socket
		}
		catch (IOException ex)	{}
		try
		{
			outputStream.close();
		}
		catch(IOException ex) {}
		
	}
	
	public void send(NetMessage message) throws IOException {
		outputStream.writeObject(message);
	}
	
	public void addDataReceivedListener(DataReceivedAction dataReceivedAction) {
		dataReceivedActionListeners.add(dataReceivedAction);
	}
	
	public void removeDataReceivedListener(DataReceivedAction dataReceivedAction) {
		dataReceivedActionListeners.remove(dataReceivedAction);
	}
	
	public void addDisconnectedListener(DisconnectedAction disconnectedAction) {
		disconnectedActionListeners.add(disconnectedAction);
	}
	
	public void removeDisconnectedListener(DisconnectedAction disconnectedAction) {
		disconnectedActionListeners.remove(disconnectedAction);
	}

	@Override
	public void run() {
		while (running) {
			NetMessage msg = null;
			try {
				msg = (NetMessage) inputStream.readObject();
			} 
			catch(WriteAbortedException ex) {
				ex.printStackTrace(); //forgetting to implement Serializable is annoying
				break;
			}
			catch (IOException ex) {
				break;
			}
			catch(ClassNotFoundException ex){
				break;
			}

			//invoke event:
			invokeDataReceivedEvent(msg);
		}
		//Exception happened, notify that the connection is closed:
		invokeDisconnectedEvent();
	}
	private void invokeDataReceivedEvent(NetMessage msg)
	{
		for (int i = dataReceivedActionListeners.size() - 1; i > -1; i--) { //subscriber may unsubscribe itself during invocation, so; loop backwards.
			DataReceivedAction subscriber = dataReceivedActionListeners.get(i);
			if (subscriber != null)
				subscriber.dataReceived(this, msg);
		}
	}
	private void invokeDisconnectedEvent()
	{
		for(int i = disconnectedActionListeners.size() - 1; i > - 1; i--)
		{
			DisconnectedAction subscriber = disconnectedActionListeners.get(i);
			if(subscriber != null)
				subscriber.disconnected(this);
		}
	}
}
