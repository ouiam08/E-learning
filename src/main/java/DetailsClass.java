import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.ArrayList;

public class DetailsClass extends JFrame{
    private JPanel details;
    private JLabel className;
    private JLabel ProfClass;
    private JButton updateButton;
    private JButton exitButton;
    private JList list1;

    public DetailsClass(String name) throws Exception{
        setTitle("Details");
        setContentPane(details);
        setMinimumSize(new Dimension(300,500));
        setDefaultCloseOperation(DetailsClass.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        className.setText(name);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        String prof= ad.getProfClass(name);
        ProfClass.setText(prof);
        listModel.clear();
        ArrayList<String> classes = ad.getAllStudent(name);
        for(String i : classes){
            listModel.addElement(i);
        }
        list1.setModel(listModel);
        list1.clearSelection();


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                UpdateClass dialog = new UpdateClass(DetailsClass.this,name);
                dialog.pack();
                dialog.setVisible(true);}
                catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) throws Exception{
        DetailsClass detailsClass = new DetailsClass("IFA1");
    }

}
