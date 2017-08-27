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

/*    public DatabaseConnection(String first_name, String last_name, String ssn)
    {
        DatabaseConnection.first_name = first_name;
        DatabaseConnection.last_name = last_name;
        DatabaseConnection.ssn = ssn;

        createConnectionToDatabase();
    }*/

    public Connection createConnectionToDatabase()
    {
        try
        {
            //Class.forName("com.mysql.jdbc.Driver");
            //1. Create connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bank?autoReconnect=true&useSSL=false", "root", "password");


/*
            String sqlQuery = "SELECT * FROM clients";

            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery(sqlQuery);

            System.out.println("Database Accessed");

            System.out.println("Client info: ");

            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }*/

        }
        catch (SQLException s)
        {
            s.printStackTrace();
        }
        return connection;
    }


}
