import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import project.MyJDBC;

public final class addOrder extends javax.swing.JFrame{
    private JPanel addOrderMainPanel;
    private JButton returnButton;
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
    int count = 0;
    double total_price = 0;
    Statement statement;
    int[] id_dish_array = new int[100];
    ArrayList<Integer> id_dish_arrayD = new ArrayList<Integer>();

    DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        new addOrder(1);
    }

    addOrder(int id_user){
        setContentPane(addOrderMainPanel);
        setTitle("Java Project");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        statement = MyJDBC.connection(statement);

        try{
            ResultSet res_table = statement.executeQuery("select id_table from `table`;");
            ArrayList<Integer> list_table = new ArrayList<Integer>(100);
            int i = 0;
            while(res_table.next()){
                list_table.add(Integer.parseInt(res_table.getString("id_table")));
            }
            for(int j=0; j<list_table.size();j++){
                tableComboBox.addItem("Table "+list_table.get(j));
            }
        }catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException);
        }

        setVisible(true);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new main_interface(id_user);
                dispose();
            }
        });
        toOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String id_table_string = (String) tableComboBox.getSelectedItem();
                    int id_table = Integer.parseInt(String.valueOf(id_table_string.charAt(id_table_string.length()-1)));
                    String queryAddOrder = "insert into db_restaurant.order (id_table, id_user, status_order, date_order) values("+id_table+", "+id_user+", 'Ordered', now());";

                    statement.execute(queryAddOrder);
                    while(!id_dish_arrayD.isEmpty()){
                        int dish_ordered = id_dish_arrayD.get(0);
                        int count_dish_ordered=0;
                        while(id_dish_arrayD.contains(dish_ordered)){
                            count_dish_ordered++;
                            id_dish_arrayD.remove(id_dish_arrayD.indexOf(dish_ordered));
                        }
                        String queryAddOrdered = "insert into ordered values(LAST_INSERT_ID(), "+dish_ordered+", "+count_dish_ordered+");";
                        statement.execute(queryAddOrdered);
                    }
                    JOptionPane.showMessageDialog(null, "Added to the database!");

                }
                catch (SQLException sqlException){
                    JOptionPane.showMessageDialog(null, sqlException);
                }

            }
        });

    }

    private void displayDishTable(String query1, JPanel panel) throws SQLException {
        ResultSet result1 = statement.executeQuery(query1);

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

            panel.add(panelTemp);
        }
        result1.close();
    }

    private void createUIComponents() {
        //connection database
        statement = MyJDBC.connection(statement);

        //Display table dish + elements in tabs
        try{
            String query1 = "Select * from dish where type='Appetizer'";
            appetizerPanel = new JPanel();
            appetizerPanel.setLayout(new BoxLayout(appetizerPanel, BoxLayout.Y_AXIS));
            displayDishTable(query1, appetizerPanel);


            String query2 = "Select * from dish where type='Dishes'";
            dishPanel = new JPanel();
            dishPanel.setLayout(new BoxLayout(dishPanel, BoxLayout.Y_AXIS));
            displayDishTable(query2, dishPanel);


            String query3 = "Select * from dish where type='Dessert'";
            dessertPanel = new JPanel();
            dessertPanel.setLayout(new BoxLayout(dessertPanel, BoxLayout.Y_AXIS));
            displayDishTable(query3, dessertPanel);

            String query4 = "Select * from dish where type='Drink'";
            drinkPanel = new JPanel();
            drinkPanel.setLayout(new BoxLayout(drinkPanel, BoxLayout.Y_AXIS));
            displayDishTable(query4, drinkPanel);


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
