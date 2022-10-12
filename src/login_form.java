import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JOptionPane;


public class login_form extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTextField txtlogin;
    private JButton LOGINButton;
    private JPasswordField passwordField;
    private JPanel panelError;

    Statement statement;

    public login_form() {
        setContentPane(mainPanel);
        setTitle("Java Project");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panelError.setVisible(false);

        try{
            String ip = "212.227.188.100";
            String port = "2022";
            String user = "javaRestaurant";
            String password = "$ja3va!R3st5auran5t.926";
            String database_name = "db_restaurant";
            Connection connection = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+database_name+"", user, password);

            statement = connection.createStatement();
            //JOptionPane.showMessageDialog(null, "Database connected");
        }
        catch (Exception e){
            e.printStackTrace();
        }




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
                        new main_interface(Integer.parseInt(result.getString(1)));
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
