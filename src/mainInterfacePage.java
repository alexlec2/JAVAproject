import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainInterfacePage extends javax.swing.JFrame{
    private JPanel mainInterfacePanel;
    private JButton addOrderButton;
    private JButton searchModifyOrderButton;
    private JButton logOutButton;
    private JPanel inputPanel;
    private JPanel menuPanel;
    private JButton adminOptionsButton;
    private JButton historicButton1;
    private JLabel testWelcome;

    mainInterfacePage(int id_user){
        setContentPane(mainInterfacePanel);
        setTitle("Java Project Main interface page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addOrderPage(id_user);
                dispose();
            }
        });

        searchModifyOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new modifyOrderPage(id_user);
                dispose();
            }
        });
        adminOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminPage(id_user);
                dispose();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginPage();
                dispose();
            }
        });
        historicButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user);
                dispose();
            }
        });
    }
}
