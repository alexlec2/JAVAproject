import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboardPage extends javax.swing.JFrame{

    private JPanel mainDashboardPanel;
    private JButton returnButton3;
    private JButton historicButton3;

    dashboardPage(int id_user){
        setContentPane(mainDashboardPanel);
        setTitle("Java Project Dashboard page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        returnButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user);
                dispose();
            }
        });

        historicButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user);
                dispose();
            }
        });
    }
}
