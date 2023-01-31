import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    public void ReceiveMessage(String message) throws RemoteException;
    public String getNomSender() throws RemoteException;
    public String getNomReciever() throws RemoteException;
}
