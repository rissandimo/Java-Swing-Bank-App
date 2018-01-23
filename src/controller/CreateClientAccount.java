package controller;

import model.DatabaseConnection;
import view.AccessAccount;

import java.sql.*;

/**
 * Connects to the bank database and adds the client if it doesn't exist
 * It creates an account number, logs into the account, and displays the Access Account interface
 */

public class CreateClientAccount
{
    //TESTING PURPOSES

    /*public static void main(String[] args)
     {
         new CreateClientAccount();
     }*/
    private DatabaseConnection bankConnection = new DatabaseConnection();

    private static int ACCOUNT_NUMBER;

    private String firstName, lastName, social;

    public CreateClientAccount(String firstName, String lastName, String social, int accountNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;
        ACCOUNT_NUMBER = ++accountNumber; //increments acct for next user
        System.out.println("Acct # to be assigned: " + ACCOUNT_NUMBER);
       // bankConnection = new DatabaseConnection();

        addClientInfo(firstName, lastName, social);
    }

    private void addClientInfo(String firstName, String lastName, String social)
    {

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

                preparedStatementClient.execute();

                addCheckingInfo(sqlConnection, ACCOUNT_NUMBER, 0.0, social);
            }
        }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
    }// close addClientInfo

    private void addCheckingInfo(Connection bankConnection, int ACCOUNT_NUMBER, double balance, String social)
    {

       // Connection connection = bankConnection.createConnectionToDatabase();

        try
        {
        String checkingStatement = "INSERT INTO checking_account (account_number, account_balance, social) values(?,?,?)";

        PreparedStatement preparedStatement = bankConnection.prepareStatement(checkingStatement);

        preparedStatement.setInt(1, ACCOUNT_NUMBER);
        preparedStatement.setDouble(2,balance);
        preparedStatement.setString(3, social);

        preparedStatement.execute();

        new AccessAccount(ACCOUNT_NUMBER);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}