import project.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class dashboardPage extends javax.swing.JFrame{

    private JPanel mainDashboardPanel;
    private JButton returnButton3;
    private JButton historicButton3;
    private JButton settingButton;
    private JPanel DashBpanel;
    private JButton endedCommandNButton;
    private JComboBox comboBox1;
    private JPanel Orderpanel;
    private JButton searchButton;
    Statement statement;

    public static void main(String[] args) {
        new dashboardPage(1, "Admin");
    }
    dashboardPage(int id_user, String type){
        setContentPane(mainDashboardPanel);
        setTitle("Java Project Dashboard page");
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

        returnButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });

        historicButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user, type);
                dispose();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new modifyOrderPage(id_user, type);
                dispose();
            }
        });
        endedCommandNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = String.valueOf(comboBox1.getSelectedItem());
                try {
                    statement =MyJDBC.connection(statement);
                    statement.execute("UPDATE `order` SET `order`.status_order = 'ended' WHERE `order`.status_order='Ordered' and `order`.id_order="+id+";");
                }catch (SQLException ex){
                    System.out.println(ex);
                }
            }
        });


    }

    private void createUIComponents() {
        DashBpanel = new JPanel();
        JLabel x;
        DashBpanel.setLayout(new GridLayout(0, 2));

        //DashBpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statement = MyJDBC.connection(statement);

        try{
            ResultSet rs = statement.executeQuery("SELECT id_order, id_table from `order` where status_order='Ordered';");
            while (rs.next()){
                 x = new JLabel("<html>Order number: "+rs.getString(1)+"<br>Table number:"+rs.getString(2)+"</html>");
                 x.setBackground(new Color(209, 237, 242));
                 x.setOpaque(true);
                 x.setBorder(BorderFactory.createLineBorder(new Color(194, 197, 204), 10));
                 DashBpanel.add(x);
                //DashBpanel.add(new JLabel("<html>Order number: "+rs.getString("id_order")+"<br>Table number:"+rs.getString("id_table")+"<br>Command:"+rs.getString("name")+"</html>"));
            }
            rs.close();
            statement.close();
        }catch (SQLException ex){
            System.out.println(ex);
        }

        comboBox1 = new JComboBox<>();
        try {
            statement = MyJDBC.connection(statement);
            ResultSet rs = statement.executeQuery("SELECT DISTINCT `order`.id_order from `order`, ordered, dish WHERE `order`.status_order='Ordered' and ordered.id_dish=dish.id_dish and `order`.id_order=ordered.id_order;");
            while(rs.next()){
                comboBox1.addItem(rs.getString("id_order"));
            }
            statement.close();
            rs.close();
        }catch (SQLException e){
            System.out.println(e);
        }






    }
}
