import javax.lang.model.element.Element;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;

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
    int count = 0;
    double total_price = 0;
    Statement statement;
    DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        new addOrder(1);
    }

    addOrder(int id_user){
        setContentPane(addOrderMainPanel);
        setTitle("Java Project");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new main_interface(id_user);
                dispose();
            }
        });
    }

    public Statement connection(){
        try {
            String ip = "212.227.188.100";
            String port = "2022";
            String user = "javaRestaurant";
            String password = "$ja3va!R3st5auran5t.926";
            String database_name = "db_restaurant";
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database_name + "", user, password);

            statement = connection.createStatement();
            return statement;
            //JOptionPane.showMessageDialog(null, "Database connected");
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private void displayDishTable(String query1, JPanel panel) throws SQLException {
        ResultSet result1 = statement.executeQuery(query1);

        while (result1.next()){
            JPanel panelTemp = new JPanel();
            panelTemp.add(new JLabel(result1.getString(2)));
            panelTemp.add(new JLabel("(Restant: "+result1.getString(5)+")"));
            JTextField txtNumber;
            panelTemp.add(txtNumber = new JTextField("0", 2));
            txtNumber.setEditable(false);

            JButton plus, minus;
            panelTemp.add(plus = new JButton("+"));
            panelTemp.add(minus = new JButton("-"));

            plus.setPreferredSize(new Dimension(20, 20));
            minus.setPreferredSize(new Dimension(20, 20));
            String price = result1.getString(3);
            String availibilty = result1.getString(5);
            plus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Integer.parseInt(txtNumber.getText()) < Integer.parseInt(availibilty)){
                        count++;
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
        statement = connection();

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
