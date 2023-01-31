import javax.swing.*;
import java.awt.event.*;
import java.rmi.*;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField pwdtf;
    private JTextField userNametf;

    public Login() {
        setContentPane(contentPane);
        setTitle("LOGIN");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    private void onOK() throws Exception {
        String name= userNametf.getText();
        String pwd=String.valueOf(pwdtf.getPassword());
            appInterface log = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method
            int user = log.auth(name,pwd);
            if(user==1){
                System.out.println("student");
                Student student= new Student(name);
                dispose();
            }else if(user==2){
                System.out.println("admin");
                Admin admin = new Admin(name);
                dispose();
            } else if (user==3) {
                System.out.println("prof");
                Prof prof = new Prof(name);
                dispose();
            } else if (name.isEmpty() || pwd.isEmpty()) {
                System.out.println("empty");
                JOptionPane.showMessageDialog(Login.this, "Please fill the form first!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                //si les info inserer sont fausse afficher erreur
                System.out.println("invalid");
                JOptionPane.showMessageDialog(Login.this, "User name or Password invalid!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            }



    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Login dialog = new Login();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
