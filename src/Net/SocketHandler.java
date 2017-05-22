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
	private ArrayList<DataReceivedAction> dataReceivedActionListeners;
	
	public SocketHandler(Socket connection) throws IOException {
		dataReceivedActionListeners = new ArrayList<>();
		outputStream = new ObjectOutputStream(connection.getOutputStream());
		System.out.println("getting input");
		inputStream = new ObjectInputStream(connection.getInputStream());
		System.out.println("getting input");
		runner = new Thread(this);
		runner.start();
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
		while (true) {
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

			for (DataReceivedAction data : dataReceivedActionListeners) {
				if (data != null)
					data.dataReceived(this, msg);
			}
		}
		//Exception happened, should probably notify that the connection is closed.
	}
}
