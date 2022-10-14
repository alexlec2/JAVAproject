import project.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;

public class historicPage extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTable historicTable;
    private JPanel tabelPanel;
    private JButton showButton;
    private JComboBox comboBox1;
    private JButton returnButton4;
    private JComboBox comboBox2;
    private JButton showBestDishButton;
    private JButton showCommandOfButton;
    private JComboBox comboBox3;
    private JButton settingButton;

    Statement statement;

    public static void main(String[] args) {
        new historicPage(1, "Admin");
    }

    historicPage(int id_user, String type){
        setContentPane(mainPanel);
        setTitle("Java Project Historic page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        statement = MyJDBC.connection(statement);
        comboBox1.addItem("all table");
        comboBox2.addItem("all user");
        comboBox3.addItem("Today");
        comboBox3.addItem("Last week");
        comboBox3.addItem("Last month");
        comboBox3.addItem("Last year");

        try{
            ResultSet res_table = statement.executeQuery("select id_table from `table`;");
            ArrayList<Integer> list_table = new ArrayList<Integer>(100);
            int i = 0;
            while(res_table.next()){
                list_table.add(Integer.parseInt(res_table.getString("id_table")));
            }
            for(int j=0; j<list_table.size();j++){
                comboBox1.addItem("Table "+list_table.get(j));
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
        try{
            ResultSet res_user = statement.executeQuery("select id_user, username from user;");
            ArrayList<String> list_username = new ArrayList<String>(100);
            ArrayList<Integer> list_userID = new ArrayList<Integer>(100);
            int i = 0;
            while(res_user.next()){
                list_userID.add(Integer.parseInt(res_user.getString("id_user")));
                list_username.add(res_user.getString("username"));
            }
            for(int j=0; j<list_userID.size();j++){
                comboBox2.addItem(list_userID.get(j)+": "+list_username.get(j));
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
        ////////DESIGN//////
        mainPanel.setBackground(new Color(209, 237, 242));
        tabelPanel.setBackground(new Color(209, 237, 242));
        comboBox1.setForeground(Color.BLUE);
        comboBox2.setForeground(Color.BLUE);
        ///////////////////

        setVisible(true);
        showCommandOfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String QUERY="";
                String x_time = (String) comboBox3.getSelectedItem();
                String time_last_day = String.valueOf(java.time.LocalDate.now().minusDays(1));
                String time_last_week = String.valueOf(java.time.LocalDate.now().minusWeeks(1));
                String time_last_month = String.valueOf(java.time.LocalDate.now().minusMonths(1));
                String time_last_year = String.valueOf(java.time.LocalDate.now().minusYears(1));
                if(x_time.equals("Today")){
                    QUERY = "select date_order, name, number from `order`, dish, ordered WHERE ordered.id_dish=dish.id_dish and `order`.status_order='ended' and date_order>'"+time_last_day+"';";
                }
                if(x_time.equals("Last week")){
                    QUERY = "select date_order, name, number from `order`, dish, ordered WHERE ordered.id_dish=dish.id_dish and `order`.status_order='ended' and date_order>'"+time_last_week+"';";
                }
                if(x_time.equals("Last month")){
                    QUERY = "select date_order, name, number from `order`, dish, ordered WHERE ordered.id_dish=dish.id_dish and `order`.status_order='ended' and date_order>'"+time_last_month+"';";
                }
                if(x_time.equals("Last year")){
                    QUERY = "select date_order, name, number from `order`, dish, ordered WHERE ordered.id_dish=dish.id_dish and `order`.status_order='ended' and date_order>'"+time_last_year+"';";
                }
                try{
                    ResultSet rs = statement.executeQuery(QUERY);

                    resultSetToTableModel(rs, historicTable);
                    //Description.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException ex){
                    System.out.println(ex);
                }

            }
        });
        showBestDishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String QUERY = "SELECT dish.id_dish, dish.name, SUM(ordered.number) AS total_number FROM ordered, dish, `order` WHERE ordered.id_dish=dish.id_dish and `order`.status_order='ended' GROUP BY id_dish ORDER BY total_number DESC;";
                try{
                    ResultSet rs = statement.executeQuery(QUERY);

                    resultSetToTableModel(rs, historicTable);
                    //Description.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException ex){
                    System.out.println(ex);
                }
            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String QUERY;
                String x_table = (String) comboBox1.getSelectedItem();
                String x_user = (String) comboBox2.getSelectedItem();
                int index_table;
                int index_user;


                if(x_user.charAt(x_user.length()-1)=='r'){
                    if(x_table.charAt(x_table.length()-1)=='e'){
                        QUERY = "select date_order, number, name, price, location from `order`, ordered, `table`, dish WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended';";
                    }else {
                        index_table= Integer.parseInt(String.valueOf(x_table.charAt(x_table.length()-1)));
                        QUERY = "select date_order, number, name, price, location from `order`, ordered, `table`, dish WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended' and `table`.id_table="+index_table+";";
                    }
                }else {
                    if(x_table.charAt(x_table.length()-1)=='e'){
                        index_user = Integer.parseInt(String.valueOf(x_user.charAt(0)));
                        QUERY="select date_order, number, name, price, location from `order`, ordered, `table`, dish, user WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended' and user.id_user="+index_user+";";
                    }else {
                        index_user = Integer.parseInt(String.valueOf(x_user.charAt(0)));
                        index_table= Integer.parseInt(String.valueOf(x_table.charAt(x_table.length()-1)));
                        QUERY="select date_order, number, name, price, location from `order`, ordered, `table`, dish, user WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended' and user.id_user="+index_user+" and `table`.id_table="+index_table+";";
                    }
                }



                try{
                    ResultSet rs = statement.executeQuery(QUERY);

                    resultSetToTableModel(rs, historicTable);
                    //Description.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException ex){
                    System.out.println(ex);
                }

            }
        });
        returnButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });

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
    }
    public void resultSetToTableModel(ResultSet rs, JTable table) throws SQLException {
        //Create new table model
        DefaultTableModel tableModel = new DefaultTableModel();

        //Retrieve meta data from ResultSet
        ResultSetMetaData metaData = rs.getMetaData();

        //Get number of columns from meta data
        int columnCount = metaData.getColumnCount();

        //Get all column names from meta data and add columns to table model
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
            tableModel.addColumn(metaData.getColumnLabel(columnIndex));
        }

        //Create array of Objects with size of column count from meta data
        Object[] row = new Object[columnCount];

        //Scroll through result set
        while (rs.next()){
            //Get object from column with specific index of result set to array of objects
            for (int i = 0; i < columnCount; i++){
                row[i] = rs.getObject(i+1);
            }
            //Now add row to table model with that array of objects as an argument
            tableModel.addRow(row);
        }

        //Now add that table model to your table and you are done :D
        table.setModel(tableModel);
    }
}
