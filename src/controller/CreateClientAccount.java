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

    /*    public static void main(String[] args)
        {
            new CreateClientAccount();
        }*/
    private DatabaseConnection bankConnection;

    private static final int ACCOUNT_NUMBER = 1000000;

    private String firstName, lastName, social;

    //  private int accountNumbers[] = new int[10];

    public CreateClientAccount(String firstName, String lastName, String social)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.social = social;

        bankConnection = new DatabaseConnection();

        addClientInfo(firstName, lastName, social);
    }

    private void addClientInfo(String firstName, String lastName, String social)
    {
        System.out.println("Add client info");

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
                boolean clientCreated = preparedStatementClient.execute();

                //preparedStatementClient closes here

                if (clientCreated) System.out.println("Client created successfully");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }// end add client


}