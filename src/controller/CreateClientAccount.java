package controller;

import model.DatabaseConnection;
import view.AccessAccount;

import java.sql.*;

/**
 * Connects to the bank database and adds the client if it doesn't exist
 * It creates an account number, logs into the account, and displays the interface
 */

public class CreateClientAccount
{
    boolean clientCreated = false;
    private DatabaseConnection connection;

    private int accountNumber;
     String firstName, lastName, socialSecurityNumber;

    public CreateClientAccount(String firstName, String lastName, String socialSecurityNumber)
    {

        System.out.println("CreateClientAccount");
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;

       connection = new DatabaseConnection();

       addClientToDatabase(firstName, lastName, socialSecurityNumber);
    }

    public void addClientToDatabase(String firstName,String lastName,String ssn)
    {
        System.out.println("addClientToDatabase");
        Connection bankConnection = connection.createConnectionToDatabase();
        System.out.println("Connection to database successfull");

        String createClientStatement = "    INSERT INTO clients (first_name, last_name, ssn)" +
                " values (?, ?, ?)";


        try
        {
        PreparedStatement preparedStatement = bankConnection.prepareStatement(createClientStatement);

        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, ssn);

        clientCreated = preparedStatement.execute(); // returns boolean

         String accountNumberSqlStatement = "SELECT account_number FROM clients WHERE ssn = ?";

         preparedStatement = bankConnection.prepareStatement(accountNumberSqlStatement);

        preparedStatement.setString(1, socialSecurityNumber);

         ResultSet accountNumberResultSet = preparedStatement.executeQuery();

         while(accountNumberResultSet.next())
         {
             accountNumber = accountNumberResultSet.getInt(1);
         }



            System.out.println("Account number: " + accountNumber);


        bankConnection.close();

        new AccessAccount(accountNumber);

        }
        catch(SQLException e2)
        {
            e2.printStackTrace();
        }
    }



}
