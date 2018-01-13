package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveClientAccount
{
    private DatabaseConnection bankConnection;
    private static int ACCOUNT_NUMBER;

    private RemoveClientAccount(int account_number)
    {
        bankConnection = new DatabaseConnection();
        ACCOUNT_NUMBER = account_number;
    }

    public static void main(String[] args)
    {
        new RemoveClientAccount(4).removeClient();
    }

    private void removeClient()
    {
        Connection connection = bankConnection.createConnectionToDatabase();

        String removeClientCK_DEPOSIT = "DELETE FROM checking_deposit where account_number = ?";
        String removeClientCK_ACCT = "DELETE FROM checking_account where account_number = ?";
        String removeClient = "DELETE FROM clients where account_number = ?";

        try
        {
        PreparedStatement preparedStatement = connection.prepareStatement(removeClientCK_DEPOSIT);

        preparedStatement.setInt(1, ACCOUNT_NUMBER);

        preparedStatement.execute();

        System.out.println("client removed from checking_deposit");


        preparedStatement = connection.prepareStatement(removeClientCK_ACCT);

        preparedStatement.setInt(1, ACCOUNT_NUMBER);

        preparedStatement.execute();

        System.out.println("client removed from checking_account");


        preparedStatement = connection.prepareStatement(removeClient);

        preparedStatement.setInt(1, ACCOUNT_NUMBER);

        preparedStatement.execute();

        System.out.println("client removed from clients");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
