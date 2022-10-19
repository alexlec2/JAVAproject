import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class mainInterfacePage extends javax.swing.JFrame{
    private JPanel mainInterfacePanel;
    private JButton addOrderButton;
    private JButton searchModifyOrderButton;
    private JButton logOutButton;
    private JPanel inputPanel;
    private JPanel menuPanel;
    private JButton historicButton1;
    private JButton settingButton;
    private JButton BTNDash;
    private JLabel testWelcome;

    public static void main(String[] args) {
        new mainInterfacePage(1, "Admin");
    }

    mainInterfacePage(int id_user, String type){
        setContentPane(mainInterfacePanel);
        setTitle("Java Project Main interface page");
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
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addOrderPage(id_user, type);
                dispose();
            }
        });

        searchModifyOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new modifyOrderPage(id_user, type);
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
                new historicPage(id_user, type);
                dispose();
            }
        });
        BTNDash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new dashboardPage(id_user, type);
                dispose();
            }
        });
    }
}
