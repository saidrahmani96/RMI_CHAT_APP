import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StartServer {
	public static void main(String[] args) {
		try {
			//System.setSecurityManager(new RMISecurityManager());
			LocateRegistry.createRegistry(1099);
			
			ChatServerInt b = new ChatServer();
			//Naming.rebind("rmi://192.168.1.5/myabc", b);
			Naming.rebind("rmi://127.0.0.1/myabc", b);
			System.out.println("[Système] Le serveur de chat est prêt.");
		} catch (Exception e) {
			System.out.println("Le serveur de chat a échoué");
		}
	}
}
