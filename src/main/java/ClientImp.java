import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImp extends UnicastRemoteObject implements ClientInt {

    JTextArea cnv;
    appInterface ad;

    public ClientImp(JTextArea cnv, appInterface ad, String nomSender) throws RemoteException, MalformedURLException, NotBoundException {
        this.cnv=cnv;
        this.ad=ad;
        this.nomSender=nomSender;
        ad.addClient(this);

    }

    public String getNomSender() throws RemoteException {
        return nomSender;
    }

    public String getNomReciever() throws RemoteException{
        return nomReciever;
    }

    String nomSender;
    String nomReciever;
    protected ClientImp(JTextArea cnv, appInterface ad, String nomSender, String nomReciever) throws RemoteException, MalformedURLException, NotBoundException {
        this.cnv=cnv;
        this.ad=ad;
        this.nomSender=nomSender;
        this.nomReciever=nomReciever;
        ad.addClient(this);
    }

    public void ReceiveMessage(String message) throws RemoteException{
        cnv.append(nomSender+": "+message+"\n");
    }



}
