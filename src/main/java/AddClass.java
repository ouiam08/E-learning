import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.ArrayList;

public class AddClass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nomClass;
    private JComboBox prof;

    public AddClass(JFrame parent) throws Exception {
        super(parent);
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(AddClass.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method
        ArrayList<String> allProf = ad.getAllProf();

        for(String  i : allProf){
            prof.addItem(i);
        }
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
        // add your code here
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        String className = nomClass.getText();
        String profName = (String)prof.getSelectedItem();
        int idprof = ad.getIdProf(profName);
        boolean exist = ad.classExist(className);
        if ( profName.isEmpty() || className.isEmpty()) {
            JOptionPane.showMessageDialog(AddClass.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        } else if(!exist){
            boolean state = ad.insertClass(className,idprof);
            if(state){
                JOptionPane.showMessageDialog(AddClass.this, "Class added!", "ok", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else if (!state) {
                JOptionPane.showMessageDialog(AddClass.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            } }
        else if(exist){

            JOptionPane.showMessageDialog(AddClass.this, "User already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
