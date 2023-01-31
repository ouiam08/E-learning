import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AddProf extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField NameProdtf;
    private JTextField mdpTF;
    private JLabel mm;

    public AddProf(JFrame parent) throws Exception{
        super(parent);
        setContentPane(contentPane);
        setDefaultCloseOperation(AddProf.DISPOSE_ON_CLOSE);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        String profName = NameProdtf.getText();
        String profPwd = mdpTF.getText();

        boolean exist = ad.profExist(profName);
        if (profPwd.isEmpty() || profName.isEmpty() ) {
            JOptionPane.showMessageDialog(AddProf.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        } else if(!exist){
            boolean state = ad.insertProf(profName,profPwd);
            if(state){
                JOptionPane.showMessageDialog(AddProf.this, "Prof added!", "ok", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else if (!state) {
                JOptionPane.showMessageDialog(AddProf.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            } }
        else if(exist){

            JOptionPane.showMessageDialog(AddProf.this, "Prof already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
