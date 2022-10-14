import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class modifyOrderPage extends javax.swing.JFrame{

    private JPanel modifyMainPanel;
    private JButton returnButton5;
    private JButton historicButton2;
    private JPanel inputPanel2;
    private JPanel searchedPanel;
    private JPanel modifyPanel;
    private JPanel modifyTablePanel;
    private JPanel modifyButtonPanel;
    private JButton updateButton;
    private JPanel panelTabs;
    private JTabbedPane tabbedPane1;
    private JPanel appetizerPanel;
    private JPanel dishPanel;
    private JPanel dessertPanel;
    private JPanel drinkPanel;
    private JButton searchButton;
    private JButton modifyButton;
    private JTextField textField1;
    private JPanel inputPanel1;

    public static void main(String[] args) {
        new modifyOrderPage(1);
    }

    modifyOrderPage(int id_user){
        setContentPane(modifyMainPanel);
        setTitle("Java Project Modify order page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        returnButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user);
                dispose();
            }
        });
        historicButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user);
                dispose();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchedPanel.setVisible(true);
                modifyPanel.setVisible(false);
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyPanel.setVisible(true);
                searchedPanel.setVisible(false);
            }
        });
    }

    private void createUIComponents(){
        searchedPanel = new JPanel();
        searchedPanel.setVisible(false);

        modifyPanel = new JPanel();
        modifyPanel.setVisible(false);
    }
}
