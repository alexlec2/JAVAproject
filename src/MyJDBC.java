import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC  {
    public static void main(String[] args) {

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_restaurant", "root", "rootroot");

            Statement statement = connection.createStatement();

            /*
            ResultSet resultSet = statement.executeQuery("select * from designation");

            while (resultSet.next()){
                System.out.println(resultSet.getString("code")+ " " +resultSet.getString("title"));
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
