package Net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandler implements Runnable {
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Thread runner;
	private boolean running;
	private ArrayList<DataReceivedAction> dataReceivedActionListeners;
	
	public SocketHandler(Socket connection) throws IOException {
		running = true;
		dataReceivedActionListeners = new ArrayList<>();
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
			inputStream.close();
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
	
	public void addDataReceivedActionListener(DataReceivedAction dataReceivedAction) {
		dataReceivedActionListeners.add(dataReceivedAction);
	}
	
	public void removeDataReceivedListener(DataReceivedAction dataReceivedAction) {
		dataReceivedActionListeners.remove(dataReceivedAction);
	}

	@Override
	public void run() {
		while (running) {
			NetMessage msg = null;
			try {
				msg = (NetMessage) inputStream.readObject();
			} 
			catch (IOException ex) {
				return;
			}
			catch(ClassNotFoundException ex){
				return;
			}

			//invoke event:
			for (int i = dataReceivedActionListeners.size() - 1; i > -1; i--) { //subscriber may unsubscribe itself during invocation, so; loop backwards.
				DataReceivedAction subscriber = dataReceivedActionListeners.get(i);
				if (subscriber != null)
					subscriber.dataReceived(this, msg);
			}
		}
		//Exception happened, should probably notify that the connection is closed.
	}
}
