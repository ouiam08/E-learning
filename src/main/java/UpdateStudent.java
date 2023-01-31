import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class UpdateStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField studentNametf;
    private JTextField pwdtf;
    private JComboBox comboBox1;
    private JButton DELETEButton;

    private String nameS;

    public UpdateStudent(JFrame parent,String studentName) throws Exception{
        super(parent);
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(UpdateStudent.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        nameS = studentName;

        studentNametf.setText(studentName);


        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method
        ArrayList<String> allClass = ad.getAllClass();

        for(String  i : allClass){
            comboBox1.addItem(i);
        }
        pwdtf.setText(ad.getStudentPassword(studentName));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (MalformedURLException ex) {

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
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
                    int idStudent = ad.getIdStudent(studentName);
                    Boolean state = ad.deleteStudent(idStudent);
                    if(state){
                        JOptionPane.showMessageDialog(UpdateStudent.this, "Student deleted!", "ok", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            }
        });
    }

    private void onOK() throws MalformedURLException, NotBoundException, RemoteException {
        // add your code here
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        String StudentName = studentNametf.getText();
        String StudentPwd = pwdtf.getText();
        String className = (String)comboBox1.getSelectedItem();
        int idClass = ad.getIdClass(className);
        int idStudent = ad.getIdStudent(nameS);
        boolean exist = ad.studentExist(StudentName);
        if (StudentPwd.isEmpty() || StudentName.isEmpty() || className.isEmpty()) {
            JOptionPane.showMessageDialog(UpdateStudent.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        } else if(!exist){
            boolean state = ad.UpdateStudent(StudentName,StudentPwd,idClass,idStudent);
            if(state){
                JOptionPane.showMessageDialog(UpdateStudent.this, "Student updated!", "ok", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else if (!state) {
                JOptionPane.showMessageDialog(UpdateStudent.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            } }
        else if(exist){

            JOptionPane.showMessageDialog(UpdateStudent.this, "User already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
