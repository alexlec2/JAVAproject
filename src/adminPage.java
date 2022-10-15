import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminPage extends javax.swing.JFrame{
    private JPanel mainAdminPage;
    private JButton returnButton2;

    adminPage(int id_user, String type){
        setContentPane(mainAdminPage);
        setTitle("Java Project Admin Page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        returnButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });
    }
}
