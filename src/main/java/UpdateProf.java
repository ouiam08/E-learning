import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;

public class UpdateProf extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField profNametf;
    private JTextField pwdTF;
    private JButton deleteButton;
    private String IDprof;

    public UpdateProf(JFrame parent,String profName) throws Exception{
        super(parent);
        setContentPane(contentPane);
        setDefaultCloseOperation(UpdateProf.DISPOSE_ON_CLOSE);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        IDprof =profName;


        profNametf.setText(profName);
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        String password = ad.getProfPassword(profName);
        pwdTF.setText(password);



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


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
                    int idprof = ad.getIdProf(IDprof);
                    Boolean state = ad.deleteProf(idprof);
                    if(state){
                        JOptionPane.showMessageDialog(UpdateProf.this, "Prof deleted!", "ok", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void onOK() throws Exception{
        // add your code here
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method


        String profNametfText = profNametf.getText();
       String profPassword = pwdTF.getText();
        int idprof = ad.getIdProf(IDprof);
        System.out.println(idprof);
        boolean exist = ad.profExist(profNametfText);


        if ( profPassword.isEmpty() || profNametfText.isEmpty()) {
            JOptionPane.showMessageDialog(UpdateProf.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(!exist){
            boolean state = ad.UpdateProf(profNametfText,profPassword,idprof);
            if(state){
                JOptionPane.showMessageDialog(UpdateProf.this, "prof updated!", "ok", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else if (!state) {
                JOptionPane.showMessageDialog(UpdateProf.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            } }
        else if(exist){

            JOptionPane.showMessageDialog(UpdateProf.this, "prof already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
