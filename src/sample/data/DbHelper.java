package sample.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper{
    private Connection connection;

    public DbHelper() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/etudiant" , "wilcoln", "");
    }
    public Connection getDataBase(){
        return connection;
    }
}
