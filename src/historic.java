import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class historic extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTable historicTable;
    private JPanel tabelPanel;
    private JButton showButton;
    private JButton button2;

    Statement statement;

    public static void main(String[] args) {
        new historic();
    }
    historic(){
        setContentPane(mainPanel);
        setTitle("Java Project - Historic");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try{
            String ip = "212.227.188.100";
            String port = "2022";
            String user = "javaRestaurant";
            String password = "$ja3va!R3st5auran5t.926";
            String database_name = "db_restaurant";
            Connection connection = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+database_name+"", user, password);

            statement = connection.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String  QUERY = "select date_order, number, name, price, location from `order`, ordered, `table`, dish WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended';";
                try{
                    ResultSet rs = statement.executeQuery(QUERY);

                    resultSetToTableModel(rs, historicTable);
                    //Description.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException ex){
                    System.out.println(ex);
                }

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
