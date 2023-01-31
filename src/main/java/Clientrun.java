public class Clientrun implements Runnable {


    public void run(){
        try {
            Login lg = new Login();
            lg.pack();
            lg.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Clientrun clt = new Clientrun();
        clt.run();
    }
}
