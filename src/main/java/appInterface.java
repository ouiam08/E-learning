import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface appInterface extends Remote {
    public  int auth(String userName, String password) throws RemoteException;
    public int getTotalStudent() throws RemoteException;
    public int getTotalClasses() throws RemoteException;
    public int getTotalProfs() throws RemoteException;
    public ArrayList<String> getAllClass() throws RemoteException;
    public boolean insertStudent(String name,String password,int idClass) throws RemoteException;
    public int getIdClass(String nomClass) throws RemoteException;
    public boolean studentExist(String name) throws RemoteException;
    public boolean insertProf(String name,String password) throws RemoteException;
    public boolean profExist(String name) throws RemoteException;
    public ArrayList<String> getAllProf() throws RemoteException;
    public boolean classExist(String name) throws RemoteException;
    public int getIdProf(String nomprof) throws RemoteException;
    public boolean insertClass(String name,int prof) throws RemoteException;
    public String getProfClass(String className) throws RemoteException;
    public ArrayList<String> getAllStudent(String className) throws RemoteException;
    public boolean UpdateClass(String name,int prof,int id_class) throws RemoteException;
    public boolean deleteClass(int idClass) throws Exception;
    public String getProfPassword(String profName) throws RemoteException;
    public boolean UpdateProf(String name,String password,int id_prof) throws RemoteException;
    public boolean deleteProf(int idprof) throws Exception;

    public void logout(String userName,int type) throws RemoteException;
    public int getIdStudent(String nomStudent) throws RemoteException;
    public boolean deleteStudent(int idStudent) throws Exception;
    public boolean UpdateStudent(String name,String password,int id_class,int idStud) throws RemoteException;
    public String getStudentPassword(String studName) throws RemoteException;
    public ArrayList<String> getProfsSession() throws RemoteException;
    public ArrayList<String> getStudentsSession() throws RemoteException;
    public ArrayList<String> getAdminSession() throws RemoteException;
    public ArrayList<String> getAllStudentofProf(String name) throws  RemoteException;
    public String getStudentClass(String name) throws RemoteException;
    public void sendMessage(String message) throws RemoteException;
    public void addClient(ClientInt clientInt) throws RemoteException;
    public ArrayList<String> getAllClassofProf(String name) throws  RemoteException;
    public void sendMessageBroadcast(String classname, String message) throws RemoteException;



}
