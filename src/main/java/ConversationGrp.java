

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConversationGrp extends JFrame {
    private JLabel Fname;
    private JPanel conv;
    private JTextField text;
    private JButton SENDButton;
    private JTextArea cnv;
    private String mess;


    public ConversationGrp(String classNmae, String nomSender) throws Exception {


        setTitle("CONVERSATION");
        setContentPane(conv);
        setMinimumSize(new Dimension(500, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        appInterface ad = (appInterface) Naming.lookup("rmi://localhost:1021/auth");

        ClientInt clientInt = new ClientImp(cnv,ad,nomSender);






        Fname.setText(classNmae);
//        System.out.println(ad.receiveMsg());
//        cnv.append("Me :" + ms1 + "\n");
//        cnv.append(nomReciever + " : " + ms2 + " \n");
        SENDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msg = text.getText();
                try {

                    ad.sendMessage(msg);
                    System.out.println(nomSender);

                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }


                text.setText("");


            }
        });
    }
}








