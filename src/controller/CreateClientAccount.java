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
    private DatabaseConnection bankConnection;

    private static final int ACCOUNT_NUMBER = 1000000;

    private String firstName, lastName, socialSecurityNumber;

     private int accountNumbers[] = new int[10];

    public CreateClientAccount(String firstName, String lastName, String socialSecurityNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;

       bankConnection = new DatabaseConnection();

       addClientToDatabase(firstName, lastName, socialSecurityNumber);
    }

    private void addClientToDatabase(String firstName,String lastName,String ssn)
    {
        int accountNumberIndex = 0;

        String createClientStatement = "INSERT INTO clients (first_name, last_name, ssn) values (?, ?, ?)";
        String createCheckingStatement = "INSERT INTO checking_account(balance, account_number) values (?, ?)";

        Connection sqlConnection = bankConnection.createConnectionToDatabase();

       // System.out.println("Connection to database successful");


        // add client to database
        try
        {

        PreparedStatement preparedStatementClient = sqlConnection.prepareStatement(createClientStatement);

        preparedStatementClient.setString(1, firstName);
        preparedStatementClient.setString(2, lastName);
        preparedStatementClient.setString(3, ssn);

        //insert client into database
        boolean clientCreated = preparedStatementClient.execute();

        // add client info to checking account

            PreparedStatement preparedStatementAccount = sqlConnection.prepareStatement(createCheckingStatement);

            preparedStatementAccount.setDouble(1, 0.0);
            preparedStatementAccount.setInt(2, ACCOUNT_NUMBER);

            boolean accountCreated = preparedStatementAccount.execute();


            //print out to console if connection successfull

        if(clientCreated) System.out.println("Client created successfully");
        if(accountCreated) System.out.println("Account created successfully");


        //search acct #s based on a ssn - testing purposes
         String accountNumberSqlStatement = "SELECT account_number FROM clients WHERE ssn = ?";

         preparedStatementClient = sqlConnection.prepareStatement(accountNumberSqlStatement);

         preparedStatementClient.setString(1, socialSecurityNumber);


         ResultSet accountNumberResultSet = preparedStatementClient.executeQuery();

         //cycle through the existing account numbers
         while(accountNumberResultSet.next())
         {
             accountNumbers[accountNumberIndex] = accountNumberResultSet.getInt(1);
             accountNumberIndex++;
         }

         //print out the accoumt numbers
        System.out.println("Account number: " + ACCOUNT_NUMBER);

         //access ATM
        new AccessAccount(ACCOUNT_NUMBER);

        }
        catch(SQLException e2)
        {
            e2.printStackTrace();
        }
        finally
        {
            try {
                sqlConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
