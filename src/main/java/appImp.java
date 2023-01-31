import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import  java.sql.*;
import java.util.ArrayList;

public class appImp extends UnicastRemoteObject implements appInterface {
    final String DB_URL = "jdbc:mysql://localhost:3306/elearning";
    final String USERNAME = "root";

    public ArrayList<String> getMeetings() throws RemoteException {
        return meetings;
    }

    final String PASSWORD = "";

//listes des admins connecter
    private  ArrayList<String> adminSession = new ArrayList<>();
//liste des etudiants connecter
    private ArrayList<String> studentsSession = new ArrayList<>();
//liste des profs connecter
    private ArrayList<String> profsSession = new ArrayList<>();
//liste des etudiants qui rejoint le chat
    private ArrayList<ClientInt> chatters = new ArrayList<>();
//listes des meetings
    private ArrayList<String> meetings = new ArrayList<>();
    //seters
    public void setAdminSession(ArrayList<String> adminSession) {
        this.adminSession = adminSession;
    }

    public void setStudentsSession(ArrayList<String> studentsSession) {
        this.studentsSession = studentsSession;
    }

    public void setProfsSession(ArrayList<String> profsSession) {
        this.profsSession = profsSession;
    }
//getters
    public ArrayList<String> getAdminSession() throws RemoteException{
        return adminSession;
    }

    public ArrayList<String> getStudentsSession() throws RemoteException{
        return studentsSession;
    }

    public ArrayList<String> getProfsSession() throws RemoteException{
        return profsSession;
    }

    public boolean studentExist(String username, String pwd) {

        //methode qui permet de tester si le student existe dans la BD

        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from student where nom_stud = ? and mdp_stud=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean adminExist(String username, String pwd) {

        //methode qui permet de tester si le admin existe dans la BD

        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from admin where nom_admin = ? and mdp_admin=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean profExist(String username, String pwd) {

        //methode qui permet de tester si le prof existe dans la BD

        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from prof where nom_prof = ? and mdp_prof=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public appImp() throws RemoteException, MalformedURLException, NotBoundException {

    }

    public int auth(String userName, String password) throws RemoteException {
        //permet l'identification
        int i = 0;
        boolean student = studentExist(userName, password);
        boolean admin = adminExist(userName, password);
        boolean prof = profExist(userName, password);
    try {
        if (student) {
            i = 1;
            studentsSession.add(userName);


        } else if (admin) {
            i = 2;
            adminSession.add(userName);


        } else if (prof) {
            i = 3;
            profsSession.add(userName);

        }
    }catch (Exception e){
        e.printStackTrace();
    }

        return i;
    }

    public int getTotalStudent() {
//donner le total des etudiants
        int total = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM `student`;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalProfs() {
//donner le total des profs
        int total = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM `prof`;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalClasses() {
//donner total des classes
        int total = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM `classe`;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            total = rs.getInt(1);
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public ArrayList<String> getAllClass() throws RemoteException {
        //retourner tout les classes
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom_class FROM `classe`; ";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result.add(es.getString("nom_class"));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public boolean insertStudent(String name, String password, int idClass) throws RemoteException {
        //methode qui permet de d'ajouter un user a la base de donnée

        boolean result = false;

//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO student ( nom_stud, mdp_stud ,id_class) " + "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, idClass);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getIdClass(String nomClass) throws RemoteException {
        //retourner if de classes
        int result = 0;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_class FROM classe WHERE nom_class=\"" + nomClass + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getInt("id_class");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public boolean studentExist(String name) {
        //retourner si etudiant existe
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from student where nom_stud = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean profExist(String name) {
        //retourner si le prof existe
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from prof where nom_prof = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertProf(String name, String password) throws RemoteException {
        //methode qui permet de d'ajouter un user a la base de donnée

        boolean result = false;

//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO prof ( nom_prof, mdp_prof ) " + "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public ArrayList<String> getAllProf() throws RemoteException {
        //retourner tout les prof
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom_prof FROM `prof`; ";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result.add(es.getString("nom_prof"));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public boolean classExist(String name) {
        //retourner si la classe existe
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * from classe where nom_class = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public int getIdProf(String nomprof) {
        //retourner id de prof
        int result = 0;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_prof FROM prof WHERE nom_prof=\"" + nomprof + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getInt("id_prof");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public boolean insertClass(String name,int prof) {
        //inserer la classe
        boolean result = false;

//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO classe ( nom_class, id_prof ) " + "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, prof);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public String getProfClass(String className) {
        //retourner cla class du prof
        String result = "";
        int id_prof =0;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_prof FROM classe WHERE nom_class=\"" + className + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                id_prof = es.getInt("id_prof");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom_prof FROM prof WHERE id_prof=\"" + id_prof + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getString("nom_prof");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return result;





    }
    public ArrayList<String> getAllStudent(String className) throws RemoteException {
        //retourner tout les etudiants
        ArrayList<String> result = new ArrayList<>();
        int id_class = getIdClass(className);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom_stud FROM `student` where id_class= "+id_class+";";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result.add(es.getString("nom_stud"));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public boolean UpdateClass(String name,int prof,int id_class) {
        //modifier une classe
        boolean result = false;

//        String confirmPassword = mess.split("//")[5].trim();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE classe SET nom_class = ?, id_prof = ? WHERE classe.id_class = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, prof);
            ps.setInt(3, id_class);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteClass(int idClass) throws Exception {
        //supprimerune classe

        //methode qui permet de supprimer une classe  de la base de donnée

        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement stmt = conn.createStatement();


        String sql = "DELETE FROM classe WHERE id_class="+idClass+";";
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();


        return true;
    }

    public String getProfPassword(String profName){
        //retourner le mot de passe
        String result = "";


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT mdp_prof FROM prof WHERE nom_prof=\"" + profName + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getString("mdp_prof");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public boolean UpdateProf(String name,String password,int id_prof) {
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE prof SET nom_prof = ?, mdp_prof = ? WHERE prof.id_prof = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, id_prof);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public boolean deleteProf(int idprof) throws Exception {

        //methode qui permet de supprimer une classe  de la base de donnée

        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement stmt = conn.createStatement();


        String sql = "DELETE FROM prof WHERE id_prof="+idprof+";";
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();


        return true;
    }

    public void logout(String userName,int type) throws RemoteException{


        if (type == 1) {

            studentsSession.remove(userName);
        } else if (type == 2) {

            adminSession.remove(userName);
        } else if (type == 3) {

            profsSession.remove(userName);
        }

    }

    public boolean UpdateStudent(String name,String password,int id_class,int idStud) {
        boolean result = false;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE student SET nom_stud = ?, mdp_stud = ?,id_class=? WHERE student.id_stud = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, id_class);
            ps.setInt(4, idStud);

            int addedRows = ps.executeUpdate();
            if (addedRows > 0) {
                result = true;
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public boolean deleteStudent(int idStudent) throws Exception {

        //methode qui permet de supprimer un eleve  de la base de donnée

        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement stmt = conn.createStatement();


        String sql = "DELETE FROM student WHERE id_stud="+idStudent+";";
        stmt.executeUpdate(sql);

        stmt.close();
        conn.close();


        return true;
    }
    public int getIdStudent(String nomStudent) {
        int result = 0;


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_stud FROM student WHERE nom_stud=\"" + nomStudent + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getInt("id_stud");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public String getStudentPassword(String studName){
        String result = "";


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT mdp_stud FROM student WHERE nom_stud=\"" + studName + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getString("mdp_stud");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public ArrayList<String> getAllStudentofProf(String nameprof) {

        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> id_classes = new ArrayList<>();
        int id_prof = getIdProf(nameprof);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT id_class FROM `classe` where id_prof= "+id_prof+";";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                id_classes.add(es.getInt("id_class"));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

            for(Integer i: id_classes) {
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                    Statement stmt = conn.createStatement();
                    String sql = "SELECT nom_stud FROM `student` where id_class= " + i + ";";
                    PreparedStatement ps = conn.prepareStatement(sql);


                    ResultSet es = stmt.executeQuery(sql);

                    while (es.next()) {
                        result.add(es.getString("nom_stud"));
                    }
                    stmt.close();
                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        return result;
    }
    public String getStudentClass(String name){
        String result = "";


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT classe.nom_class FROM classe , student WHERE classe.id_class=student.id_class and nom_stud=\"" + name + "\";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result = es.getString("nom_class");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public void sendMessage(String message) throws RemoteException{
        for(ClientInt i : chatters){
            for(ClientInt j : chatters) {
                if (i.getNomSender().equals(j.getNomReciever()) || j.getNomSender().equals(i.getNomReciever())) {
                    i.ReceiveMessage(message);
                }
            }
        }
    }

    public void addClient(ClientInt clientInt) throws RemoteException{
        chatters.add(clientInt);
    }
    public ArrayList<String> getAllClassofProf(String name){
        ArrayList<String> result = new ArrayList<>();
        int id_prof = getIdProf(name);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT nom_class FROM `classe` where id_prof= "+id_prof+";";
            PreparedStatement ps = conn.prepareStatement(sql);


            ResultSet es = stmt.executeQuery(sql);

            while (es.next()) {
                result.add(es.getString("nom_class"));
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    public void sendMessageBroadcast(String classname, String message) throws RemoteException {
        ArrayList<String> nonStudent = getAllStudent(classname);
        for (ClientInt i : chatters) {
            for (String j : nonStudent) {
                if (i.getNomSender().equals(j) || j.equals(i.getNomReciever())) {
                    i.ReceiveMessage(message);

                }
            }
        }
    }

    public void addMeeting(String classname) throws RemoteException{
        meetings.add(classname);
    }


}
