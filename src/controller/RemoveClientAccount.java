package controller;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RemoveClientAccount
{
    private Connection bankConnection;
    private PreparedStatement preparedStatement;

    public static void main(String[] args)
    {
        new RemoveClientAccount();
    }

    public RemoveClientAccount()
    {
        bankConnection = new DatabaseConnection().createConnectionToDatabase();
        new Frame();
    }

    public void removeCheckingAccount(int account_number) throws SQLException
    {
        String removeClientCK_ACCT = "DELETE FROM checking_account where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_ACCT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
        System.out.println("remove checking account");

    }

    public void removeCheckingDeposit(int account_number) throws SQLException
    {
        String removeClientCK_DEPOSIT = "DELETE FROM checking_deposit where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_DEPOSIT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
        System.out.println("remove checking deposit");
    }

    public void removeCheckingWithdrawal(int account_number) throws SQLException
    {
        String removeClientCK_WITHDRAWAL = "DELETE FROM checking_withdrawl where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_WITHDRAWAL);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
        System.out.println("remove checking withdrawal");
    }

    private void removeClientFromDatabase(int account_number) throws SQLException
    {
        String removeClient = "DELETE FROM clients where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClient);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
        System.out.println("remove client");
    }

    private void removeClient(int account_number) throws SQLException
    {

        removeCheckingDeposit(account_number);

        removeCheckingWithdrawal(account_number);

        removeCheckingAccount(account_number);

        removeClientFromDatabase(account_number);

    }

    class Frame extends JFrame
    {
        public JTextField input;
        private JTextArea results;

        public Frame()
        {
            createView();
            displayClients();
        }

        public void createView()
        {
            setTitle("Remove Client");


            //CENTER PANEL - Text Area
            results = new JTextArea(50,50);
            JPanel panelCenter = new JPanel();
            panelCenter.add(results);

            add(panelCenter, BorderLayout.CENTER);

            //BOTTOM PANEL - Submit Buttons & Text Input
            input = new JTextField(15);
            JButton submit = new JButton("Submit");
            submit.addActionListener(new RemoveClientListener());


            JPanel panelBottom = new JPanel();
            panelBottom.add(input);
            panelBottom.add(submit);
            add(panelBottom, BorderLayout.SOUTH);


            setSize(650, 600);
            setVisible(true);

            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        public void displayClients()
        {
            try
            {
                Statement statement = bankConnection.createStatement();

                String clientStatement = "SELECT first_name, last_name, account_number FROM clients";

                ResultSet resultSet = statement.executeQuery(clientStatement);

                results.append("List of clients in bank, please select one and click submit \n \n");

                while(resultSet.next())
                {
                    results.append(resultSet.getString(1) + ", " +
                            resultSet.getString(2) + "-" +
                            "Account #: " +
                            resultSet.getString(3) + "\n");
                }

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        class RemoveClientListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {                               //Account number
                    removeClient(Integer.parseInt(input.getText()));
                } catch(SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }// close Frame

} // close RemoveClientAccount
