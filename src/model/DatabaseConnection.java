package model;

import java.sql.*;

public class DatabaseConnection
{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private static String first_name;
    private static String last_name;
    private static String ssn;

    public static void main(String[] args)
    {
        new DatabaseConnection().createConnectionToDatabase();
    }

    private DatabaseConnection()
    {
    }

    public DatabaseConnection(String first_name, String last_name, String ssn)
    {
        DatabaseConnection.first_name = first_name;
        DatabaseConnection.last_name = last_name;
        DatabaseConnection.ssn = ssn;

        createConnectionToDatabase();
    }

    private void createConnectionToDatabase()
    {
        try
        {
            //Class.forName("com.mysql.jdbc.Driver");
            //1. Create connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bank?autoReconnect=true&useSSL=false", "root", "password");

            String sqlQuery = "SELECT * FROM clients";

            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
            }

        } catch (SQLException s)
        {
            s.printStackTrace();
        }

    }

}
