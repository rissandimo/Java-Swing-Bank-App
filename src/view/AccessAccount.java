package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

 public class AccessAccount extends JFrame
{

    public static void main(String[] args)
    {
        new AccessAccount(1);
    }

    private DatabaseConnection databaseConnection;
    private Connection         sqlConnection;

    protected static JTextArea   results;

    private        JTextField  input;

    private int     accountNumber;
    private double  accountBalance;

    public AccessAccount(int accountNumber)
    {
        System.out.println("AccessAccount");

        createView();

        this.accountNumber = accountNumber;

        connectToDatabase(accountNumber);
    }

    private void checkAccountInfo()
    {
        System.out.println("checkAccountInfo()");

        sqlConnection = databaseConnection.createConnectionToDatabase();

        String accountInfoStatement = "SELECT account_balance as balance from checking_account where account_number = ?";

        try
        {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) // call next() because initially the cursor is positioned before the first row
            {
                AccessAccount.results.append(String.format("Balance: %.2f \n", resultSet.getDouble(1)));
            }
            resultSet.first();

            if(resultSet.getDouble(1) == 0.0) System.out.println("No funds available");
            }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void connectToDatabase(int accountNumber)
    {
        databaseConnection = new DatabaseConnection(); // initialize Connection reference - returns conn to Bank

        Connection bankConnection = databaseConnection.createConnectionToDatabase(); // return SQL connection - API
        System.out.println("databaseConnection successful");

        try
        {

            String selectCustomers = "SELECT first_name, last_name, account_number FROM clients where account_number = ?";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(selectCustomers);

            preparedStatement.setInt(1, accountNumber);


            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                results.append("Client info: ");
                results.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n" +
                        "Account number: " + resultSet.getString(3) + "\n");
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }// close connectToDatabase

    private void createView()
    {

        //TOP PANEL - BUTTONS
        JButton deposit = new JButton("Deposit");

        JButton withdrawal = new JButton("Withdrawal");

        JButton checkAcctInfo = new JButton("Account Info");
        checkAcctInfo.addActionListener(e-> checkAccountInfo());

        JButton logOut = new JButton("Log Out");

        class CloseApplication implements Runnable
        {
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                    dispose();
                }
                catch(InterruptedException e2) {e2.printStackTrace();}

            }

            public void closeWindow()
            {
                Thread thread = new Thread(this);
                thread.start();
            }
        }

       logOut.addActionListener(e->
       {
           {
               results.append("Logging out of account # " + accountNumber);
               new CloseApplication().closeWindow();
           }
       });

        JPanel panelTop = new JPanel();
        panelTop.add(deposit);
        panelTop.add(withdrawal);
        panelTop.add(checkAcctInfo);
        panelTop.add(logOut);

        add(panelTop, BorderLayout.NORTH);


        TransactionActionListener transactionActionListener = new TransactionActionListener();

        checkAcctInfo.addActionListener(transactionActionListener);
        deposit.addActionListener(transactionActionListener);
        withdrawal.addActionListener(transactionActionListener);
        checkAcctInfo.addActionListener(transactionActionListener);
        logOut.addActionListener(transactionActionListener);

        //CENTER PANEL - Text Area
        results = new JTextArea(50,50);
        JPanel panelCenter = new JPanel();
        panelCenter.add(results);

        add(panelCenter, BorderLayout.CENTER);

        //BOTTOM PANEL - Submit Buttons & Text Input
        input = new JTextField(15);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e ->
        {
            if(TransactionActionListener.actionPerformed.equals("Deposit"))
               deposit(Double.parseDouble(input.getText()));
            if(TransactionActionListener.actionPerformed.equals("Withdrawal"))
                enoughFunds();
            if(TransactionActionListener.actionPerformed.equals(""))
                results.append("Please make a selection above \n");
        });


        JPanel panelBottom = new JPanel();
        panelBottom.add(input);
        panelBottom.add(submit);
        add(panelBottom, BorderLayout.SOUTH);


        setSize(650, 600);
        setVisible(true);
        setTitle("Access account");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private void deposit(double depositAmount)
    {

        //connect to db and perform deposit
       sqlConnection = databaseConnection.createConnectionToDatabase();

       //added auto increment to checking_deposit but haven't made the change to mysql
       String updateBalance = "UPDATE checking_account SET account_balance = account_balance + ? WHERE account_number = ?";
       String deposit = "INSERT INTO checking_deposit (account_number, deposit) VALUES (?,?)";

       try
       {

       PreparedStatement tbl_checking_deposit = sqlConnection.prepareStatement(deposit);

       PreparedStatement tbl_checking_account = sqlConnection.prepareStatement(updateBalance);

       //DEPOSIT - Checking Deposit
       tbl_checking_deposit.setInt(1, accountNumber);
       tbl_checking_deposit.setDouble(2, depositAmount);

       //UPDATE - Checking account
       tbl_checking_account.setDouble(1, depositAmount);
       tbl_checking_account.setInt(2, accountNumber);

       //Update checking account, deposit into checking account, update checking account
       tbl_checking_account.execute();
       tbl_checking_deposit.execute();


       input.setText(" ");

       results.append(String.format("$ %.2f deposited into account # %d \n", depositAmount, accountNumber));

       }
       catch(SQLException sqlE)
       {
           sqlE.printStackTrace();
       }
    } // close deposit

    private void enoughFunds()
    {

        double amountToWithdraw = Double.parseDouble(input.getText());
        System.out.println("Amount to withdraw: " + amountToWithdraw);
        int accountNumber = this.accountNumber;

        //make connection to db
        sqlConnection = databaseConnection.createConnectionToDatabase();

        String checkFundsStatement = "SELECT account_balance FROM checking_account WHERE account_number = ?";

        try
        {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(checkFundsStatement);

            preparedStatement.setInt(1, accountNumber);

            ResultSet balanceResultSet = preparedStatement.executeQuery();

            while(balanceResultSet.next()) accountBalance = balanceResultSet.getDouble(1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        if(amountToWithdraw <= accountBalance)
        {
            System.out.println("Enough funds available");
            withdrawl(amountToWithdraw);
        }
        else
            results.append("Sorry, you don't have enough funds \n");
    }


    private void withdrawl(double withdrawlAmount)
    {
       // make connection to db

        sqlConnection = databaseConnection.createConnectionToDatabase();

        String updateStatement = "UPDATE checking_account SET account_balance = account_balance - ? where account_number = ?";
        String withdrawalStatement = "INSERT INTO checking_withdrawl (account_number, withdrawal) values(?,?)";

        try
        {
        PreparedStatement preparedStatementWithdrawal = sqlConnection.prepareStatement(withdrawalStatement);
        PreparedStatement preparedStatementUpdate = sqlConnection.prepareStatement(updateStatement);

        //WITHDRAWAL
        preparedStatementWithdrawal.setInt(1, accountNumber);
        preparedStatementWithdrawal.setDouble(2, withdrawlAmount);

        //UPDATE
        preparedStatementUpdate.setDouble(1, withdrawlAmount);
        preparedStatementUpdate.setInt(2, accountNumber);

        //Update checking account then make withdrawal
        preparedStatementUpdate.execute();
        preparedStatementWithdrawal.execute();

        results.append(String.format("$ %.2f withdrawn from account # %d \n", withdrawlAmount, accountNumber));

        input.setText("");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


}// close AccessAccount


class TransactionActionListener implements ActionListener
{

    static String actionPerformed = "";

    public void actionPerformed(ActionEvent e)
    {
        System.out.println("TransactionActionListener");

        if(e.getActionCommand().equals("Deposit"))
        {
            AccessAccount.results.append("Enter amount to deposit" + "\n");
            actionPerformed = "Deposit";
        }
        if(e.getActionCommand().equals("Withdrawal"))
        {
            AccessAccount.results.append("Enter amount to withdraw" + "\n");
            actionPerformed = "Withdrawal";
        }

    }



}




