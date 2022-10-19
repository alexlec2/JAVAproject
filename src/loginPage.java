import project.MyJDBC;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class loginPage extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTextField txtlogin;
    private JButton LOGINButton;
    private JPasswordField passwordField;
    private JPanel panelError;
    private JPanel panel1;
    int id_login;
    String type;

    Statement statement;

    public loginPage() {
        setContentPane(mainPanel);
        setTitle("Java Project Login Page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panelError.setVisible(false);

        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = txtlogin.getText();
                String password = "";
                char[] pass = passwordField.getPassword();

                for (int i = 0; i < pass.length; i++){
                    password += pass[i];
                }

                String queryString = "select id_user, type from `user` where username='"+username+"' and password='"+password+"';";

                try {
                    statement = MyJDBC.connection(statement);

                    ResultSet result = statement.executeQuery(queryString);

                    if(result.next()){
                        //JOptionPane.showMessageDialog(null, "Hello "+result.getString(1));
                        id_login = Integer.parseInt(result.getString(1));
                        type = result.getString(2);
                        new mainInterfacePage(id_login, type);
                        result.close();
                        statement.close();
                        dispose();
                    }
                    else {
                        panelError.setVisible(true);
                    }
                    result.close();
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x,y,width-1,height-1,radius,radius);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        panel1 = new JPanel();
        //panel1.setBorder(new RoundedBorder(50));
    }
}
