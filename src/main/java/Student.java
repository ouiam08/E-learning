import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Student extends JFrame{
    private JPanel student;
    private JButton logoutButton;
    private JLabel helloLabel;
    private JButton settingsButton;
    private JList LessonsList;
    private JButton contacterLeProfButton;
    private JLabel className;


    public Student(String name) throws Exception{
        setTitle("Student");
        setContentPane(student);
        setMinimumSize(new Dimension(500,429));
        setSize(1200,700);
        setDefaultCloseOperation(Student.DISPOSE_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");


        helloLabel.setText("Welcome back, "+name);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ad.logout(name,1);
                    dispose();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        contacterLeProfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String prof = ad.getProfClass(ad.getStudentClass(name));
                    Conversation conversation = new Conversation(prof,name);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
