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
    //TESTING PURPOSES

    /*public static void main(String[] args)
     {
         new CreateClientAccount();
     }*/
    private DatabaseConnection bankConnection;

    private static int ACCOUNT_NUMBER;

    private String firstName, lastName, social;

    public CreateClientAccount(String firstName, String lastName, String social, int accountNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;
        ACCOUNT_NUMBER = ++accountNumber; //increments acct for next user
        bankConnection = new DatabaseConnection();

        addClientInfo(firstName, lastName, social, ACCOUNT_NUMBER);
    }

    private void addClientInfo(String firstName, String lastName, String social, int ACCOUNT_NUMBER)
    {
        System.out.println("addClientInfo()");

        try
        {
            Connection sqlConnection = bankConnection.createConnectionToDatabase();

            String createClientStatement = "INSERT INTO clients (first_name, last_name, social, account_number) values (?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = sqlConnection.prepareStatement(createClientStatement))
            {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, social);
                preparedStatementClient.setInt(4, ACCOUNT_NUMBER);

                System.out.println("client sql statement executing");

                preparedStatementClient.execute();

                System.out.println("Client created successfully");

                addCheckingInfo(ACCOUNT_NUMBER, 0.0, social);
            }
        }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
    }// close addClientInfo

    private void addCheckingInfo(int accountNumber, double balance, String social)
    {
        System.out.println("addCheckingInfo()");
        Connection connection = bankConnection.createConnectionToDatabase();

        try
        {
        String checkingStatement = "INSERT INTO checking_account (account_number, account_balance, social) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(checkingStatement);

        preparedStatement.setInt(1, ACCOUNT_NUMBER);
        preparedStatement.setDouble(2,0.0);
        preparedStatement.setString(3, social);

        preparedStatement.execute();

        System.out.println("Checking account created");

        new AccessAccount(ACCOUNT_NUMBER);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}