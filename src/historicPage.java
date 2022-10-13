import project.MyJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class historicPage extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JTable historicTable;
    private JPanel tabelPanel;
    private JButton showButton;
    private JComboBox comboBox1;
    private JButton returnButton4;

    Statement statement;

    historicPage(int id_user){
        setContentPane(mainPanel);
        setTitle("Java Project Historic page");
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
                comboBox1.addItem("Table "+list_table.get(j));
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }

        setVisible(true);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String x = (String) comboBox1.getSelectedItem();
                int index;
                index = Integer.parseInt(String.valueOf(x.charAt(x.length()-1)));

                final String  QUERY = "select date_order, number, name, price, location from `order`, ordered, `table`, dish WHERE `order`.id_table=`table`.id_table and ordered.id_dish=dish.id_dish and `order`.status_order='ended' and `table`.id_table="+index+";";
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
                new mainInterfacePage(id_user);
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
