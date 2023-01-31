import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements Runnable{
    private static final int PORT = 1021;
    public void run(){

try{
// Defining Object
            appInterface loginInterface = new appImp();
// Creating RMI Registry with Port
            Registry registry = LocateRegistry.createRegistry(PORT);
// Binding the Object
            registry.rebind("auth", loginInterface);

    System.out.println("Authentication Service running at "+PORT+" port...");




    }catch (Exception e){
    e.printStackTrace();
    }
}

    public static void main(String[] args) {
        Server s = new Server();
        s.run();
    }
}
