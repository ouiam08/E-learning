import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.Naming;
import java.util.ArrayList;

public class StudentProf extends JFrame {
    private JTable students;
    private JPanel studens;

    public StudentProf(String name) throws Exception{
        setTitle("Students");
        setContentPane(studens);
        setMinimumSize(new Dimension(500,429));
        setDefaultCloseOperation(StudentProf.DISPOSE_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);

        DefaultTableModel model = (DefaultTableModel) students.getModel();
        model.addColumn("student name");
        model.addColumn("class");

        model.setRowCount(0);
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
        ArrayList<String> studs = ad.getAllStudentofProf(name);
        model.addRow(new Object[]{"Student", "class"});
        for(String i :studs) {
            model.addRow(new Object[]{i, ad.getStudentClass(i)});

        }





        students.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i =students.getSelectedRow();
                String stname = (String) students.getValueAt(i,0);
                try {
                    Conversation conversation = new Conversation(stname,name);
                }catch (Exception e1){
                    e1.printStackTrace();
                }

            }
        });
    }
}
