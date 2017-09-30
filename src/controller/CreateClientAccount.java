package controller;

import model.DatabaseConnection;
import view.CreateAccount;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Omid on 9/3/2017.
 */
public class CreateClientAccount
{
    boolean clientCreated = false;
    private DatabaseConnection connection;

    String firstName, lastName, ssn;

    public CreateClientAccount(String firstName, String lastName, String ssn)
    {

        System.out.println("CreateClientAccount");
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;

       connection = new DatabaseConnection();

       addClientToDatabase(firstName, lastName, ssn);
    }

    public void addClientToDatabase(String firstName,String lastName,String ssn)
    {
        System.out.println("addClientToDatabase");
        Connection bankConnection = connection.createConnectionToDatabase();
        System.out.println("Connection to database successfull");

        String createClientStatement = "INSERT INTO clients (first_name, last_name, ssn)" +
                " values (?, ?, ?)";


        try
        {
        PreparedStatement preparedStatement = bankConnection.prepareStatement(createClientStatement);

        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, ssn);

        clientCreated = preparedStatement.execute();
           // System.out.println("Client added to bank: " + clientCreated); always prints out false - doesn't work

        bankConnection.close();

        }
        catch(SQLException e2)
        {
            e2.printStackTrace();
        }
    }



}
