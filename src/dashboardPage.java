import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class dashboardPage extends javax.swing.JFrame{

    private JPanel mainDashboardPanel;
    private JButton returnButton3;
    private JButton historicButton3;
    private JButton settingButton;

    dashboardPage(int id_user, String type){
        setContentPane(mainDashboardPanel);
        setTitle("Java Project Dashboard page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        if(!Objects.equals(type, "Admin")){
            settingButton.setVisible(false);
        }

        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminPage(id_user, type);
                dispose();
            }
        });

        returnButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });

        historicButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user, type);
                dispose();
            }
        });
    }
}
