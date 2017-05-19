package Net;

public interface DataReceivedAction {
	public void dataReceived(SocketHandler socket, NetMessage net);
}
