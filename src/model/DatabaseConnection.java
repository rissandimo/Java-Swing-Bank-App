package model;

import java.sql.*;

public class DatabaseConnection
{
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private static String first_name;
    private static String last_name;
    private static String ssn;

    public static void main(String[] args)
    {
        new DatabaseConnection().createConnectionToDatabase();
    }

    public DatabaseConnection()
    {
    }


    public Connection createConnectionToDatabase()
    {
        try
        {
            //Class.forName("com.mysql.jdbc.Driver");
            //1. Create connection
            connection = DriverManager.getConnection("jdbc:mysql://local" +
                    "host/bank?autoReconnect=true&useSSL=false", "root", "");
            //create bank database schema

        }
        catch (SQLException s)
        {
            s.printStackTrace();
        }
        return connection;
    }


}
