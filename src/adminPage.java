import project.MyJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class adminPage extends javax.swing.JFrame{
    private JPanel mainAdminPage;
    private JButton returnButton2;
    private JTextField numeroTabletxt;
    private JButton applyAddTableButton;
    private JComboBox tableComboBox;
    private JButton applyDeleteTableButton;
    private JTextField usernameTxt;
    private JPasswordField password1Txt;
    private JPasswordField password2Txt;
    private JButton addUserButton;
    private JComboBox usernameComboBox;
    private JButton deleteUserButton;
    private JComboBox comboBoxLocation;
    private JSpinner spinnerCapacity;
    private JComboBox comboBoxType;

    Statement statement;

    public static void main(String[] args) {
        new adminPage(1, "Admin");
    }

    adminPage(int id_user, String type){
        setContentPane(mainAdminPage);
        setTitle("Java Project Admin Page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        returnButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });
        applyAddTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    statement = MyJDBC.connection(statement);

                    String testTable = "Select id_table from `table` where id_table="+numeroTabletxt.getText();
                    ResultSet resultSet = statement.executeQuery(testTable);

                    if(!resultSet.next()){
                        resultSet.close();
                        String query = "Insert into `table` values("+numeroTabletxt.getText()+", '"+comboBoxLocation.getSelectedItem()+"'," +
                                "0, "+spinnerCapacity.getValue()+");";

                        statement.execute(query);

                        tableComboBox.addItem(numeroTabletxt.getText());

                        JOptionPane.showMessageDialog(null, "Table added !");

                        statement.close();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Table already exist. Retry with another number.");
                    }

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        applyDeleteTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    statement = MyJDBC.connection(statement);

                    String beforeQuery = "SET foreign_key_checks = 0;";
                    String query = "Delete from `table` where id_table="+tableComboBox.getSelectedItem()+";";
                    String afterQuery = "SET foreign_key_checks = 1;";

                    statement.execute(beforeQuery);
                    statement.execute(query);
                    statement.execute(afterQuery);

                    tableComboBox.removeItem(tableComboBox.getSelectedItem());

                    JOptionPane.showMessageDialog(null, "Table deleted !");

                    statement.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    statement = MyJDBC.connection(statement);

                    String testTable = "Select username from `user` where username='"+usernameTxt.getText()+"';";
                    ResultSet resultSet = statement.executeQuery(testTable);

                    if(!resultSet.next()){
                        resultSet.close();

                        String password = "";
                        char[] pass = password1Txt.getPassword();

                        for (int i = 0; i < pass.length; i++){
                            password += pass[i];
                        }
                        String password2 = "";
                        char[] pass2 = password2Txt.getPassword();

                        for (int i = 0; i < pass2.length; i++){
                            password2 += pass2[i];
                        }

                        if(password.equals(password2)){
                            String query = "Insert into `user` (username, password, type) values('"+usernameTxt.getText()+"', '"+password+"', '"+comboBoxType.getSelectedItem()+"');";

                            statement.execute(query);

                            usernameComboBox.addItem(usernameTxt.getText());

                            JOptionPane.showMessageDialog(null, "User added !");

                            statement.close();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Passwords don't correspond, retry.");
                        }



                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Username already exist. Retry with another number.");
                    }

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    statement = MyJDBC.connection(statement);

                    String beforeQuery = "SET foreign_key_checks = 0;";
                    String query = "Delete from `user` where username='"+usernameComboBox.getSelectedItem()+"';";
                    String afterQuery = "SET foreign_key_checks = 1;";

                    statement.execute(beforeQuery);
                    statement.execute(query);
                    statement.execute(afterQuery);

                    usernameComboBox.removeItem(usernameComboBox.getSelectedItem());

                    JOptionPane.showMessageDialog(null, "User deleted !");

                    statement.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        comboBoxLocation = new JComboBox();
        comboBoxLocation.addItem("Inside");
        comboBoxLocation.addItem("Outside");

        comboBoxType = new JComboBox();
        comboBoxType.addItem("Serveur");
        comboBoxType.addItem("Cuisinier");
        comboBoxType.addItem("Admin");

        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 100, 1);
        spinnerCapacity = new JSpinner(model1);

        try{
            tableComboBox = new JComboBox();
            usernameComboBox = new JComboBox();
            statement = MyJDBC.connection(statement);

            String query1 = "Select id_table from `table`";
            ResultSet result1 = statement.executeQuery(query1);
            while(result1.next()){
                tableComboBox.addItem(result1.getString(1));
            }
            result1.close();

            String query2 = "Select username from `user`";
            ResultSet result2 = statement.executeQuery(query2);

            while(result2.next()){
                usernameComboBox.addItem(result2.getString(1));
            }

            result2.close();

            statement.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
