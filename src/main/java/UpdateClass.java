import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.ArrayList;

public class UpdateClass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField classNametf;
    private JComboBox comboBox1;
    private JButton deleteButton;
    private String NomClass ;

    public UpdateClass(JFrame parent,String className) throws Exception{

        super(parent);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        NomClass = className;
        //sitting the old class name by default on the classname textfield
        classNametf.setText(className);

//filling the combobox
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
// Invoking the Method
        ArrayList<String> allProf = ad.getAllProf();

        for(String  i : allProf){
            comboBox1.addItem(i);
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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

        //delete button traitement
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
                    int idClass = ad.getIdClass(className);
                    Boolean state = ad.deleteClass(idClass);
                    if(state){
                        JOptionPane.showMessageDialog(UpdateClass.this, "Class deleted!", "ok", JOptionPane.INFORMATION_MESSAGE);
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


        String classNametfText = classNametf.getText();
        String profName = (String)comboBox1.getSelectedItem();
        int idClass = ad.getIdClass(NomClass);
        int idprof = ad.getIdProf(profName);
        System.out.println(idClass);
        System.out.println(idprof);
        System.out.println(classNametfText);
        boolean exist = ad.classExist(classNametfText);


        if ( profName.isEmpty() || classNametfText.isEmpty()) {
            JOptionPane.showMessageDialog(UpdateClass.this, "make sure to fill all the fields!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(!exist){
            boolean state = ad.UpdateClass(classNametfText,idprof,idClass);
            if(state){
                JOptionPane.showMessageDialog(UpdateClass.this, "Class updated!", "ok", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else if (!state) {
                JOptionPane.showMessageDialog(UpdateClass.this, "Error!", "Try again", JOptionPane.ERROR_MESSAGE);
                return;
            } }
        else if(exist){

            JOptionPane.showMessageDialog(UpdateClass.this, "Class name already exist!", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }




    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
