import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AddStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField studentNametf;
    private JTextField studentPassw;
    private JComboBox comboBox1;
    private JPanel addFriendPannel;

    public AddStudent(JFrame parent) throws MalformedURLException, NotBoundException, RemoteException {
        super(parent);
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(AddStudent.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);


        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method
        ArrayList<String> allClass = ad.getAllClass();

        for(String  i : allClass){
            comboBox1.addItem(i);
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws MalformedURLException, NotBoundException, RemoteException {
        // add your code here
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        String StudentName = studentNametf.getText();
        String StudentPwd = studentPassw.getText();
        String className = (String)comboBox1.getSelectedItem();
        int idClass = ad.getIdClass(className);
        boolean exist = ad.studentExist(StudentName);
        if (StudentPwd.isEmpty() || StudentName.isEmpty() || className.isEmpty()) {
            JOptionPane.showMessageDialog(AddStudent.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        } else if(!exist){
            boolean state = ad.insertStudent(StudentName,StudentPwd,idClass);
        if(state){
            JOptionPane.showMessageDialog(AddStudent.this, "Student added!", "ok", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (!state) {
            JOptionPane.showMessageDialog(AddStudent.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        } }
        else if(exist){

            JOptionPane.showMessageDialog(AddStudent.this, "User already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }



}
