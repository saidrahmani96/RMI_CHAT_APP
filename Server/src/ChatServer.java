import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ChatServer extends UnicastRemoteObject implements ChatServerInt{
	private Vector v = new Vector<>();
	public ChatServer() throws RemoteException {
	}
	@Override
	public boolean login(ChatClientInt a) throws RemoteException {
		System.out.println(a.getName()+" est connecté.....");
		a.tell("Vous etes connecté avec succés.");
		publish(a.getName()+" vient de se connecter.");
		v.add(a);
		return true;
	}
	@Override
	public void publish(String s) throws RemoteException {
		System.out.println(s);
		for (int i = 0; i < v.size(); i++) {
			try {
				ChatClientInt tmp = (ChatClientInt) v.get(i);
				tmp.tell(s);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	@Override
	public Vector getConnected() throws RemoteException {
		// TODO Auto-generated method stub
		return v;
	}
}
