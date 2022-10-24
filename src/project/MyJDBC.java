package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyJDBC  {
    public static Statement connection(Statement statement){
        try {
            server s = new server();
            String ip = s.ip;
            String port = "2022";
            String user = "javaRestaurant";
            String password = s.password;
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
}
