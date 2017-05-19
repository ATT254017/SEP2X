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
		inputStream = new ObjectInputStream(connection.getInputStream());
		outputStream = new ObjectOutputStream(connection.getOutputStream());
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
		
	}
}
