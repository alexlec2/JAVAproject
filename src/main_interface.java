import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_interface extends javax.swing.JFrame{
    private JPanel mainInterfacePanel;
    private JButton addOrderButton;
    private JButton sawOrderButton;
    private JButton logOutButton;
    private JPanel inputPanel;
    private JPanel menuPanel;
    private JLabel testWelcome;

    main_interface(int id_user){
        setContentPane(mainInterfacePanel);
        setTitle("Java Project");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addOrder(id_user);
                dispose();
            }
        });

    }
}
