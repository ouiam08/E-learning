import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Admin extends JFrame{
    private JPanel admin;
    private JLabel labelhello;
    private JButton logoutButton;
    private JButton addStudentButton;
    private JButton addClassButton;
    private JButton addProfessorButton;
    private JLabel nbrStudent;
    private JLabel nbrClass;
    private JLabel nbrProf;
    private JList allClassList;
    private JList AllProfessorsList;
    private JTextField studentNameTF;
    private JButton updateButton;
    private String name;


    public Admin(String userName) throws Exception {
        setTitle("Admin");
        setContentPane(admin);
        setMinimumSize(new Dimension(500,429));
        setDefaultCloseOperation(Admin.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        DefaultListModel<String> listModel1 = new DefaultListModel<>();
        name=userName;
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");

        //afficher tout les class dans la liste
        listModel.clear();
        ArrayList<String> classes = ad.getAllClass();
        for(String i : classes){
            listModel.addElement(i);
        }
        allClassList.setModel(listModel);


        labelhello.setText("Hello " + userName);


        //afficher tout les prof dans la liste
        listModel1.clear();
        ArrayList<String> profs = ad.getAllProf();
        for(String i : profs){
            listModel1.addElement(i);
        }
        AllProfessorsList.setModel(listModel1);




// Invoking the Method
        int totalStudent = ad.getTotalStudent();
        nbrStudent.setText(String.valueOf(totalStudent));
        int totalClasses = ad.getTotalClasses();
        nbrClass.setText(String.valueOf(totalClasses));
        int totalProf = ad.getTotalProfs();
        nbrProf.setText(String.valueOf(totalProf));


        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent dialog = null;
                try {
                    dialog = new AddStudent(Admin.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        addProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProf dialog = null;
                try {
                    dialog = new AddProf(Admin.this);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        addClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddClass dialog = null;
                try {
                    dialog = new AddClass(Admin.this);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        allClassList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    try {
                        if (allClassList.getSelectedValuesList() != null){
                            String claassNme = allClassList.getSelectedValue().toString();
                            DetailsClass detailsClass = new DetailsClass(claassNme);
//                            allClassList.clearSelection();
                        }
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }

                }
            }
        });
        AllProfessorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    try {
                        if (AllProfessorsList.getSelectedValuesList() != null){
                            String profName = AllProfessorsList.getSelectedValue().toString();
                            UpdateProf updateProf = new UpdateProf(Admin.this,profName);
                            updateProf.pack();
                            updateProf.setVisible(true);
                        }
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }

                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ad.logout(name,2);
                    dispose();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameTF.getText();
                try {
                    Boolean exist = ad.studentExist(name);
                    if(!exist){
                        JOptionPane.showMessageDialog(Admin.this, "This student doesn't exist!", "Try again", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (exist) {
                                UpdateStudent dialog = new UpdateStudent(Admin.this,name);
                                dialog.pack();
                                dialog.setVisible(true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) throws Exception {
        Admin admin = new Admin("admin1");
    }
}
