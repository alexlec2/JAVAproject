import project.MyJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class loginPage extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTextField txtlogin;
    private JButton LOGINButton;
    private JPasswordField passwordField;
    private JPanel panelError;
    int id_login;

    Statement statement;

    public loginPage() {
        setContentPane(mainPanel);
        setTitle("Java Project Login Page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panelError.setVisible(false);

        statement = MyJDBC.connection(statement);

        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtlogin.getText();
                String password = "";
                char[] pass = passwordField.getPassword();

                for (int i = 0; i < pass.length; i++){
                    password += pass[i];
                }

                String queryString = "select id_user from `user` where username='"+username+"' and password='"+password+"';";

                try {
                    ResultSet result = statement.executeQuery(queryString);
                    if(result.next()){
                        //JOptionPane.showMessageDialog(null, "Hello "+result.getString(1));
                        id_login = Integer.parseInt(result.getString(1));
                        new mainInterfacePage(id_login);
                        dispose();
                    }
                    else {
                        panelError.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
