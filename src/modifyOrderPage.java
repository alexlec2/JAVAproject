import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class modifyOrderPage extends javax.swing.JFrame{

    private JPanel mainModifyPanel;
    private JButton returnButton5;
    private JButton historicButton2;
    private JPanel inputPanel2;
    private JButton searchButton;
    private JButton modifyButton;
    private JTextField textField1;
    private JPanel inputPanel1;
    private JButton updateButton;
    private JPanel displayDataPanel;
    private JTabbedPane tabbedPane2;
    private JPanel appetizer2Panel;
    private JPanel dish2Panel;
    private JPanel dessert2Panel;
    private JPanel drink2Panel;
    private JPanel modifyPanel;
    private JPanel buttonUpdatePanel;
    private JPanel searchPanel;

    public static void main(String[] args) {
        new modifyOrderPage(1);
    }

    modifyOrderPage(int id_user){
        setContentPane(mainModifyPanel);
        setTitle("Java Project Modify order page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

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
                searchPanel.setVisible(true);
                displayDataPanel.setVisible(false);
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDataPanel.setVisible(true);
                searchPanel.setVisible(false);
            }
        });
    }

    private void createUIComponents(){
        searchPanel = new JPanel();
        searchPanel.setVisible(false);

        displayDataPanel = new JPanel();
        displayDataPanel.setVisible(false);
    }
}
