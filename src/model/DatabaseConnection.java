package model;

import java.sql.*;

public class DatabaseConnection
{
    private Connection connection = null;

/*
    //TESTING PURPOSES
    public static void main(String[] args)
    {
        new DatabaseConnection().createConnectionToDatabase();
    }*/

    public Connection createConnectionToDatabase()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false", "root", "");
        }
        catch (SQLException s)
        {
            s.printStackTrace();
        }
        return connection;
    }
}
