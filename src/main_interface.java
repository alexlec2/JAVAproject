import javax.swing.*;

public class main_interface extends javax.swing.JFrame{
    private JPanel mainInterfacePanel;
    private JLabel testWelcome;

    main_interface(int id_user){
        setContentPane(mainInterfacePanel);
        setTitle("Class exercise 3 part 2");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        testWelcome.setText(testWelcome.getText()+" "+id_user);

    }
}
