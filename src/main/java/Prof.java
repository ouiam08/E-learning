import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Prof extends JFrame{
    private JPanel prof;
    private JButton logoutButton;
    private JLabel HELLOlabel;
    private JList DashList;
    private JTextField searchTf;
    private JButton searchButton;
    private JButton startButton;
    private JList onlineStlist;

    public Prof(String name) throws Exception{
        setTitle("Prof");
        setContentPane(prof);
        setMinimumSize(new Dimension(700,429));
        setDefaultCloseOperation(Prof.DISPOSE_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        HELLOlabel.setText("Hello Mr "+ name);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        DefaultListModel<String> listModel1 = new DefaultListModel<>();
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    try {
                        ad.logout(name,3);
                        dispose();
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }

        });
            //remplir la liste des etudiants online
        listModel.clear();
        ArrayList<String> studentsSession = ad.getStudentsSession();
        ArrayList<String> studentofProf = ad.getAllStudentofProf(name);
         for(String i : studentsSession){
             for(String j : studentofProf){
                 if(i.equals(j)){
                     listModel.addElement(i);
                 }
             }
        }
        onlineStlist.setModel(listModel);
        onlineStlist.clearSelection();

        //remplir la liste de dashboard
        listModel1.clear();
        listModel1.addElement("Dashboard");
        listModel1.addElement("Students");
        listModel1.addElement("Classes");
        DashList.setModel(listModel1);
        DashList.clearSelection();


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean exist = false;
                    String searchStudent = searchTf.getText();
                    for(String j : studentofProf){
                        if(searchStudent.equals(j)){
                            exist=true;
                        }
                    }
                    if (exist){
                        JOptionPane.showMessageDialog(Prof.this, "This student exist!", "Try again", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else if (!exist) {
                        JOptionPane.showMessageDialog(Prof.this, "This student doesn't exist!", "Try again", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        DashList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    try {
                        if (DashList.getSelectedValuesList() != null){
                            String element = DashList.getSelectedValue().toString();
                            if(element.equals("Students")){
                                    StudentProf studentProf = new StudentProf(name);
                            } else if (element.equals("Classes")) {
                                ProfClasses profClasses= new ProfClasses(name);
                            }

                        }
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }

                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new org.example.SwingPaint().show();
                try {
                    int i =1;
                    ad.addMeeting("meeting"+i);
                    i++;
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
