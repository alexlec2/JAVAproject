import project.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    private JPanel appetizer2Panel;
    private JPanel dish2Panel;
    private JPanel dessert2Panel;
    private JPanel drink2Panel;
    private JPanel modifyPanel;
    private JPanel buttonUpdatePanel;
    private JPanel searchPanel;
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

                if(!idOrderTxt.getText().isEmpty()){
                    displayDataPanel.setVisible(true);
                    searchPanel.setVisible(false);

                    try{
                        appetizer2Panel = new JPanel();
                        appetizer2Panel.setLayout(new BoxLayout(appetizer2Panel, BoxLayout.Y_AXIS));
                        displayDishTableFromOrder("Appetizer", appetizer2Panel, Integer.parseInt(idOrderTxt.getText()));
                        tabbedPane2.addTab("Appetizer", appetizer2Panel);

                        dish2Panel = new JPanel();
                        dish2Panel.setLayout(new BoxLayout(dish2Panel, BoxLayout.Y_AXIS));
                        displayDishTableFromOrder("Dishes", dish2Panel, Integer.parseInt(idOrderTxt.getText()));
                        tabbedPane2.addTab("Dish", dish2Panel);

                        dessert2Panel = new JPanel();
                        dessert2Panel.setLayout(new BoxLayout(dessert2Panel, BoxLayout.Y_AXIS));
                        displayDishTableFromOrder("Dessert", dessert2Panel, Integer.parseInt(idOrderTxt.getText()));
                        tabbedPane2.addTab("Dessert", dessert2Panel);

                        drink2Panel = new JPanel();
                        drink2Panel.setLayout(new BoxLayout(drink2Panel, BoxLayout.Y_AXIS));
                        displayDishTableFromOrder("Drink", drink2Panel, Integer.parseInt(idOrderTxt.getText()));
                        tabbedPane2.addTab("Drink", drink2Panel);

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "empty field");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statement = MyJDBC.connection(statement);

                try{
                    /*
                    String query1 = "Select *  from order where id_order="+idOrderTxt.getText()+";";
                    ResultSet result1 = statement.executeQuery(query1);
                    String id_table = result1.getString(2);
                    String id_user = result1.getString(3);
                    String status_order = result1.getString(4);
                    String date_order = result1.getString(5);
                    JOptionPane.showMessageDialog(null, date_order);
                    result1.close();

                    String query2 = "Delete from order where id_order = "+idOrderTxt.getText()+";";
                    statement.execute(query2);
*/
                    String query3 = "Delete from ordered where id_order = "+idOrderTxt.getText()+";";
                    statement.execute(query3);

                    while(!id_dish_arrayD.isEmpty()){
                        int dish_ordered = id_dish_arrayD.get(0);
                        int count_dish_ordered=0;
                        while(id_dish_arrayD.contains(dish_ordered)){
                            count_dish_ordered++;
                            id_dish_arrayD.remove(id_dish_arrayD.indexOf(dish_ordered));
                        }
                        String queryAddOrdered = "insert into ordered values("+idOrderTxt.getText()+", "+dish_ordered+", "+count_dish_ordered+");";
                        statement.execute(queryAddOrdered);
                    }
                    statement.close();


                }
                catch (Exception ex){
                    ex.printStackTrace();
                }


            }
        });
    }


    private void displayDishTableFromOrder(String type, JPanel panel, int idOrder) throws SQLException {
        boolean closed = false;

        statement = MyJDBC.connection(statement);
        statement2 = MyJDBC.connection(statement2);

        String query = "Select * from dish where type='"+type+"';";
        ResultSet result1 = statement.executeQuery(query);
        ResultSet result2 = statement2.executeQuery("SELECT dish.id_dish, dish.name, ordered.number FROM dish inner join ordered " +
                "on dish.id_dish = ordered.id_dish and ordered.id_order = "+idOrder+" and dish.type='"+type+"';");

        while (result1.next()){
            JPanel panelTemp = new JPanel();
            JLabel id_dish;
            panelTemp.add(new JLabel(result1.getString(2)));
            panelTemp.add(id_dish = new JLabel(result1.getString(1)));
            id_dish.setVisible(false);
            panelTemp.add(new JLabel("(Restant: "+result1.getString(5)+")"));
            JTextField txtNumber;
            panelTemp.add(txtNumber = new JTextField("0", 2));

            if(!closed){
                if(result2.next()){
                    if(Objects.equals(result1.getString(1), result2.getString(1))){
                        txtNumber.setText(result2.getString(3));
                        count += Integer.parseInt(result2.getString(3));
                        for(int i = 0; i < Integer.parseInt(result2.getString(3)); i++){
                            id_dish_arrayD.add(Integer.parseInt(result2.getString(1)));
                        }
                    }
                }
                else {
                    result2.close();
                    statement2.close();
                    closed = true;
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
                    if(Integer.parseInt(txtNumber.getText()) < Integer.parseInt(availibilty)){
                        count++;
                        id_dish_arrayD.add(id);
                        txtNumber.setText(String.valueOf(Integer.parseInt(txtNumber.getText())+1));
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
                    }
                }
            });

            panel.add(panelTemp);
        }
        result1.close();
        statement.close();
        if(!closed){
            result2.close();
            statement2.close();
        }
    }



    private void createUIComponents(){
        searchPanel = new JPanel();
        searchPanel.setVisible(false);

        displayDataPanel = new JPanel();
        displayDataPanel.setVisible(false);
    }
}
