import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ProfClasses extends JFrame{
    private JList list1;
    private JPanel prof;
public ProfClasses(String name) throws MalformedURLException, NotBoundException, RemoteException {
    setTitle("Classes");
    setContentPane(prof);
    setMinimumSize(new Dimension(500, 600));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setVisible(true);
    appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");
    DefaultListModel<String> listModel = new DefaultListModel<>();
    listModel.clear();
    ArrayList<String> classes =ad.getAllStudentofProf(name);
    for(String i : classes){
        listModel.addElement(i);
    }
    list1.setModel(listModel);

    list1.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()){
                try {
                    if (list1.getSelectedValuesList() != null){
                        String claassNme = list1.getSelectedValue().toString();
                        ConversationGrp conversationGrp = new ConversationGrp(claassNme,name);
//                            allClassList.clearSelection();
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        }
    });
}
}
