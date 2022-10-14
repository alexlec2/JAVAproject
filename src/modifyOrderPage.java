import project.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class modifyOrderPage extends javax.swing.JFrame{

    private JPanel mainModifyPanel;
    private JButton returnButton5;
    private JButton historicButton2;
    private JPanel inputPanel2;
    private JButton searchButton;
    private JButton modifyButton;
    private JTextField idOrderTxt;
    private JPanel inputPanel1;
    private JButton updateButton;
    private JPanel displayDataPanel;
    private JTabbedPane tabbedPane2;
    private JPanel Appetizer2Panel;
    private JPanel Dishes2Panel;
    private JPanel Dessert2Panel;
    private JPanel Drink2Panel;
    private JPanel modifyPanel;
    private JPanel buttonUpdatePanel;
    private JPanel searchPanel;
    private JButton deleteButton;
    private JComboBox idComboBox;
    Statement statement;
    Statement statement2;
    int count = 0;
    ArrayList<Integer> id_dish_arrayD = new ArrayList<Integer>();

    public static void main(String[] args) {
        new modifyOrderPage(1);
    }

    modifyOrderPage(int id_user){
        setContentPane(mainModifyPanel);
        setTitle("Java Project Modify order page");
        setSize(400,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        returnButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainInterfacePage(id_user);
                dispose();
            }
        });
        historicButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historicPage(id_user);
                dispose();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPanel.setVisible(true);
                displayDataPanel.setVisible(false);

            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                count = 0;
                id_dish_arrayD.clear();

                while(tabbedPane2.getTabCount() > 0){
                    tabbedPane2.removeTabAt(0);
                }

                if(!idOrderTxt.getText().isEmpty()){
                    displayDataPanel.setVisible(true);
                    searchPanel.setVisible(false);

                    try{
                        int id_number = Integer.parseInt(idOrderTxt.getText());
                        String tableSelected = (String) idComboBox.getSelectedItem();

                        statement = MyJDBC.connection(statement);
                        statement2 = MyJDBC.connection(statement2);

                        String query1 = "Select * from dish;";
                        String query2 = "";
                        if(Objects.equals(tableSelected, "Order")){
                            query2 = "SELECT dish.id_dish, dish.name, ordered.number, dish.type FROM dish inner join ordered " +
                                    "on dish.id_dish = ordered.id_dish and ordered.id_order = "+id_number+";";
                        }
                        else {
                            query2 = "SELECT dish.id_dish, dish.name, ordered.number, dish.type FROM dish inner join ordered " +
                                    "on dish.id_dish = ordered.id_dish "+
                                    "inner join `order` on  ordered.id_table = "+id_number+" and `order`.id_order=ordered.id_order and `order`.status_order != 'Ended';";
                        }

                        ResultSet result1 = statement.executeQuery(query1);
                        ResultSet result2 = statement2.executeQuery(query2);

                        Appetizer2Panel = new JPanel();
                        Appetizer2Panel.setName("Appetizer");
                        Appetizer2Panel.setLayout(new BoxLayout(Appetizer2Panel, BoxLayout.Y_AXIS));

                        Dishes2Panel = new JPanel();
                        Dishes2Panel.setName("Dishes");
                        Dishes2Panel.setLayout(new BoxLayout(Dishes2Panel, BoxLayout.Y_AXIS));

                        Dessert2Panel = new JPanel();
                        Dessert2Panel.setName("Dessert");
                        Dessert2Panel.setLayout(new BoxLayout(Dessert2Panel, BoxLayout.Y_AXIS));

                        Drink2Panel = new JPanel();
                        Drink2Panel.setName("Drink");
                        Drink2Panel.setLayout(new BoxLayout(Drink2Panel, BoxLayout.Y_AXIS));

                        displayDishTableFromOrder(result1, result2, Appetizer2Panel);
                        displayDishTableFromOrder(result1, result2, Dishes2Panel);
                        displayDishTableFromOrder(result1, result2, Dessert2Panel);
                        displayDishTableFromOrder(result1, result2, Drink2Panel);

                        tabbedPane2.addTab("Appetizer", Appetizer2Panel);
                        tabbedPane2.addTab("Dish", Dishes2Panel);
                        tabbedPane2.addTab("Dessert", Dessert2Panel);
                        tabbedPane2.addTab("Drink", Drink2Panel);

                        result1.close();
                        result2.close();
                        statement.close();
                        statement2.close();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, id_dish_arrayD+" "+count);
                }
                else{
                    JOptionPane.showMessageDialog(null, "empty field");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try{
                    statement = MyJDBC.connection(statement);
                    String query = "";

                    if(idComboBox.getSelectedItem() == "Table"){
                        query = "Select id_order, id_table, status_order from `order` where id_table= "+idOrderTxt.getText()+" and status_order != 'Ended';";
                    }
                    else{
                        query = "Select id_order, id_table, status_order from `order` where id_order= "+idOrderTxt.getText()+";";
                    }

                    ResultSet result = statement.executeQuery(query);

                    String id_order = "";
                    String id_table = "";

                    while(result.next()){
                        id_order = result.getString(1);
                        id_table = result.getString(2);
                    }

                    result.close();

                    String query3 = "Delete from ordered where id_order = "+id_order+";";
                    statement.execute(query3);

                    int i = 0;
                    Collections.sort(id_dish_arrayD);
                    while(i < id_dish_arrayD.size()){

                        int dish_ordered = id_dish_arrayD.get(i);
                        int count_dish_ordered = 0;

                        for (int j = i; j < id_dish_arrayD.size(); j++){
                            if(dish_ordered == id_dish_arrayD.get(j)){
                                count_dish_ordered++;
                                i++;
                            }
                        }

                        String queryAddOrdered = "insert into ordered values("+id_order+", "+dish_ordered+", "+count_dish_ordered+", "+id_table+", 'Ordered');";
                        statement.execute(queryAddOrdered);
                    }

                    statement.close();
                    JOptionPane.showMessageDialog(null, "Ordered table updated");
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                statement = MyJDBC.connection(statement);

                String query1 = "Delete from ordered where id_order="+Integer.parseInt(idOrderTxt.getText())+";";
                String query2 = "Delete from `order` where id_order="+Integer.parseInt(idOrderTxt.getText())+";";

                statement.execute(query1);
                statement.execute(query2);

                statement.close();
                JOptionPane.showMessageDialog(null, "Delete from order and ordered tables");
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void displayDishTableFromOrder(ResultSet result1, ResultSet result2, JPanel panel) throws SQLException {
        boolean equal = true;

        String id_dish2 = "";
        String number2 = "";

        while (result1.next()){
            JPanel panelTemp = new JPanel();
            JLabel id_dish;
            panelTemp.add(new JLabel(result1.getString(2)));
            panelTemp.add(id_dish = new JLabel(result1.getString(1)));
            id_dish.setVisible(false);
            panelTemp.add(new JLabel("(Restant: " + result1.getString(5) + ")"));
            JTextField txtNumber;
            panelTemp.add(txtNumber = new JTextField("0", 2));

            if (equal) {
                if (result2.next()) {
                    id_dish2 = result2.getString(1);
                    number2 = result2.getString(3);

                    if (Objects.equals(result1.getString(1), id_dish2)) {
                        txtNumber.setText(number2);
                        count += Integer.parseInt(number2);
                        for (int i = 0; i < Integer.parseInt(number2); i++) {
                            id_dish_arrayD.add(Integer.parseInt(id_dish2));
                        }
                        equal = true;
                    } else {
                        equal = false;
                    }
                }
            } else {
                if (Objects.equals(result1.getString(1), id_dish2)) {
                    txtNumber.setText(number2);
                    count += Integer.parseInt(number2);
                    for (int i = 0; i < Integer.parseInt(number2); i++) {
                        id_dish_arrayD.add(Integer.parseInt(id_dish2));
                    }
                    equal = true;
                } else {
                    equal = false;
                }
            }


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
                    if (Integer.parseInt(txtNumber.getText()) < Integer.parseInt(availibilty)) {
                        count++;
                        id_dish_arrayD.add(id);
                        txtNumber.setText(String.valueOf(Integer.parseInt(txtNumber.getText()) + 1));
                    }
                }
            });
            minus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Integer.parseInt(txtNumber.getText()) > 0) {
                        count--;
                        id_dish_arrayD.remove(id_dish_arrayD.indexOf(id));
                        txtNumber.setText(String.valueOf(Integer.parseInt(txtNumber.getText()) - 1));
                    }
                }
            });

            String name = result1.getString(4);

            if(name.equals(Appetizer2Panel.getName())){
                Appetizer2Panel.add(panelTemp);
            }
            else if(name.equals(Dishes2Panel.getName())){
                Dishes2Panel.add(panelTemp);
            }
            else if(name.equals(Dessert2Panel.getName())){
                Dessert2Panel.add(panelTemp);
            }
            else if(name.equals(Drink2Panel.getName())){
                Drink2Panel.add(panelTemp);
            }
        }
    }

    private void createUIComponents(){
        searchPanel = new JPanel();
        searchPanel.setVisible(false);

        displayDataPanel = new JPanel();
        displayDataPanel.setVisible(false);

        String[] data = { "Table", "Order"};
        idComboBox = new JComboBox(data);
    }
}
