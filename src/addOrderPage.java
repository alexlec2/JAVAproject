import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import project.MyJDBC;

public final class addOrderPage extends javax.swing.JFrame{
    private JPanel addOrderMainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel dessertPanel;
    private JPanel dishPanel;
    private JPanel appetizerPanel;
    private JPanel drinkPanel;
    private JLabel lblTotalOrder;
    private JPanel panelTabs;
    private JLabel lblTotalPrice;
    private JButton toOrderButton;
    private JComboBox tableComboBox;
    private JButton returnButton1;
    private JButton historicButton4;
    private JButton settingButton;
    private JButton BTNDash;
    int count = 0;
    double total_price = 0;
    Statement statement;
    ArrayList<Integer> id_dish_arrayD = new ArrayList<Integer>();

    DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        new addOrderPage(1, "Admin");
    }

    addOrderPage(int id_user, String type){
        setContentPane(addOrderMainPanel);
        setTitle("Java Project Add order page");
        setSize(500,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


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

        try{
            statement = MyJDBC.connection(statement);

            ResultSet res_table = statement.executeQuery("select id_table from `table`;");
            ArrayList<Integer> list_table = new ArrayList<Integer>(100);
            int i = 0;
            while(res_table.next()){
                list_table.add(Integer.parseInt(res_table.getString("id_table")));
            }
            for(int j=0; j<list_table.size();j++){
                tableComboBox.addItem("Table "+list_table.get(j));
            }
            statement.close();
        }catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException);
        }

        setVisible(true);

        BTNDash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new dashboardPage(id_user, type);
                dispose();
            }
        });

        toOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    statement = MyJDBC.connection(statement);

                    String id_table_string = (String) tableComboBox.getSelectedItem();
                    int id_table = Integer.parseInt(String.valueOf(id_table_string.charAt(id_table_string.length()-1)));
                    String queryAddOrder = "insert into `order` (id_table, id_user, status_order, date_order) values("+id_table+", "+id_user+", 'Ordered', now());";
                    statement.execute(queryAddOrder);

                    int i = 0;
                    Collections.sort(id_dish_arrayD);
                    while(i < id_dish_arrayD.size()){
                        int dish_ordered = id_dish_arrayD.get(i);
                        int count_dish_ordered=0;
                        for (int j = i; j < id_dish_arrayD.size(); j++){
                            if(dish_ordered == id_dish_arrayD.get(j)){
                                count_dish_ordered++;
                                i++;
                            }
                        }
                        String queryAddOrdered = "insert into ordered values(LAST_INSERT_ID(), "+dish_ordered+", "+count_dish_ordered+", "+id_table+", 'Ordered');";
                        statement.execute(queryAddOrdered);

                        String query = "Select available from dish where id_dish="+dish_ordered+";";
                        ResultSet result = statement.executeQuery(query);

                        int number_availibitity = 0;
                        if(result.next()){
                            number_availibitity = Integer.parseInt(result.getString(1))- count_dish_ordered;
                        }

                        result.close();

                        String queryUpdateAvailibility = "Update dish set available="+number_availibitity+" where id_dish="+dish_ordered+";";
                        statement.execute(queryUpdateAvailibility);
                    }
                    JOptionPane.showMessageDialog(null, "Added to the database!");

                    statement.close();



                    new mainInterfacePage(id_user, type);
                    dispose();
                }
                catch (SQLException sqlException){
                    JOptionPane.showMessageDialog(null, sqlException);
                }

            }
        });

        returnButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user, type);
                dispose();
            }
        });

        historicButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user, type);
                dispose();
            }
        });
    }

    private void displayDishTable(ResultSet result1) throws SQLException {

        while (result1.next()){
            JPanel panelTemp = new JPanel();
            JLabel id_dish;
            panelTemp.add(new JLabel(result1.getString(2)));
            panelTemp.add(id_dish = new JLabel(result1.getString(1)));
            id_dish.setVisible(false);
            panelTemp.add(new JLabel("(Restant: "+result1.getString(5)+")"));
            JTextField txtNumber;
            panelTemp.add(txtNumber = new JTextField("0", 2));
            txtNumber.setEditable(false);

            JButton plus, minus;
            panelTemp.add(plus = new JButton("+"));
            panelTemp.add(minus = new JButton("-"));

            plus.setPreferredSize(new Dimension(20, 20));
            minus.setPreferredSize(new Dimension(20, 20));

            int id = Integer.parseInt(result1.getString(1));
            String price = result1.getString(3);
            String availibilty = result1.getString(5);
            plus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Integer.parseInt(txtNumber.getText()) < Integer.parseInt(availibilty)){
                        count++;
                        id_dish_arrayD.add(id);

                        txtNumber.setText(String.valueOf(Integer.parseInt(txtNumber.getText())+1));
                        lblTotalOrder.setText("The total number of command is : " + count+".");

                        total_price = Double.parseDouble(price)+total_price;

                        lblTotalPrice.setText("The total price is : " + df.format(total_price) +"$.");
                    }
                }
            });
            minus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Integer.parseInt(txtNumber.getText()) > 0){
                        count--;
                        id_dish_arrayD.remove(id_dish_arrayD.indexOf(id));

                        txtNumber.setText(String.valueOf(Integer.parseInt(txtNumber.getText())-1));
                        lblTotalOrder.setText("The total number of command is : " + count+".");

                        total_price = total_price-Double.parseDouble(price);

                        lblTotalPrice.setText("The total price is : " + df.format(total_price) +"$.");
                    }
                }
            });

            String name = result1.getString(4);

            if(name.equals(appetizerPanel.getName())){
                appetizerPanel.add(panelTemp);
            }
            else if(name.equals(dishPanel.getName())){
                dishPanel.add(panelTemp);
            }
            else if(name.equals(dessertPanel.getName())){
                dessertPanel.add(panelTemp);
            }
            else if(name.equals(drinkPanel.getName())){
                drinkPanel.add(panelTemp);
            }
        }
    }

    private void createUIComponents() {
        //Display table dish + elements in tabs
        try{
            statement = MyJDBC.connection(statement);

            String query1 = "Select * from dish";
            ResultSet result1 = statement.executeQuery(query1);

            appetizerPanel = new JPanel();
            appetizerPanel.setName("Appetizer");
            appetizerPanel.setLayout(new BoxLayout(appetizerPanel, BoxLayout.Y_AXIS));

            dishPanel = new JPanel();
            dishPanel.setName("Dishes");
            dishPanel.setLayout(new BoxLayout(dishPanel, BoxLayout.Y_AXIS));

            dessertPanel = new JPanel();
            dessertPanel.setName("Dessert");
            dessertPanel.setLayout(new BoxLayout(dessertPanel, BoxLayout.Y_AXIS));

            drinkPanel = new JPanel();
            drinkPanel.setName("Drink");
            drinkPanel.setLayout(new BoxLayout(drinkPanel, BoxLayout.Y_AXIS));

            displayDishTable(result1);

            result1.close();
            statement.close();

            toOrderButton = new JButton("To order");
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
