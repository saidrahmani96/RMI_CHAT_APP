import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements ChatClientInt{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ChatUI ui;
	protected ChatClient(String n) throws RemoteException {
		name = n;
	}

	@Override
	public void tell(String st) throws RemoteException {
		System.out.println(st);
		ui.writeMsg(st);
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}
	public void setGUI(ChatUI t) {
		ui = t;
	}

}
