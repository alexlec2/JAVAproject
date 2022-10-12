import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class addOrder extends javax.swing.JFrame{
    private JPanel addOrderMainPanel;
    private JButton returnButton;
    private JSpinner spinner1;
    private JTabbedPane tabbedPane1;
    private JLabel welcomelbl;

    public static void main(String[] args) {
        new addOrder(1);
    }

    addOrder(int id_user){
        setContentPane(addOrderMainPanel);
        setTitle("Java Project");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new main_interface(id_user);
                dispose();
            }
        });
    }
}
