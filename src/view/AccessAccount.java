package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

 public class AccessAccount extends JFrame
{

/*    public static void main(String[] args)
    {
        new AccessAccount(1);
    }*/

    private DatabaseConnection databaseConnection;
    private Connection sqlConnection;
    private TransactionActionListener transactionActionListener;

    private JButton checkAcctInfo;
    private JButton deposit;
    private JButton logOut;
    private JButton submit;
    private JButton withdrawal;

    private JPanel panelBottom;
    private JPanel panelCenter;
    private JPanel panelTop;

    private JTextField input;
    static JTextArea results;

    private int     accountNumber;
    private double  accountBalance;

    public AccessAccount(int accountNumber)
    {
        System.out.println("AccessAccount");

        createView();

        databaseConnection = new DatabaseConnection();
        connectToDatabase(accountNumber);

        this.accountNumber = accountNumber;
    }

    private void checkAccountInfo()
    {
        System.out.println("checkAccountInfo");

        sqlConnection = databaseConnection.createConnectionToDatabase();

        String accountInfoStatement = "SELECT account_balance as balance from checking_account where account_number = ?";

        try
        {

            PreparedStatement preparedStatement = sqlConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                AccessAccount.results.append("Balance: " + resultSet.getDouble(1) + "\n");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void connectToDatabase(int accountNumber)
    {
        System.out.println("Attempt to connect to database");

        Connection bankConnection = databaseConnection.createConnectionToDatabase();
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
        deposit         = new JButton("Deposit");
        withdrawal      = new JButton("Withdrawal");

        checkAcctInfo   = new JButton("Account Info");
        checkAcctInfo.addActionListener(e-> checkAccountInfo());

        logOut          = new JButton("Log Out");

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

        panelTop = new JPanel();
        panelTop.add(deposit);
        panelTop.add(withdrawal);
        panelTop.add(checkAcctInfo);
        panelTop.add(logOut);

        add(panelTop, BorderLayout.NORTH);


        transactionActionListener = new TransactionActionListener();

        checkAcctInfo.addActionListener(transactionActionListener);
        deposit.addActionListener(transactionActionListener);
        withdrawal.addActionListener(transactionActionListener);
        checkAcctInfo.addActionListener(transactionActionListener);
        logOut.addActionListener(transactionActionListener);

        //CENTER PANEL - Text Area
        results = new JTextArea(50,50);
        panelCenter = new JPanel();
        panelCenter.add(results);

        add(panelCenter, BorderLayout.CENTER);

        //BOTTOM PANEL - Submit Buttons & Text Input
        input = new JTextField(15);
        submit = new JButton("Submit");
        submit.addActionListener(e ->
        {
            if(TransactionActionListener.actionPerformed.equals("Deposit"))
               deposit(Double.parseDouble(input.getText()));
            if(TransactionActionListener.actionPerformed.equals("Withdrawal"))
                enoughFunds();
        });


        panelBottom = new JPanel();
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
       String deposit = "INSERT INTO checking_deposit (account_number, deposit) VALUES (?,?)";
       String updateBalance = "UPDATE checking_account SET account_balance = account_balance + ? WHERE account_number = ?";

       try
       {

       PreparedStatement preparedStatementDeposit = sqlConnection.prepareStatement(deposit);

       PreparedStatement preparedStatementUpdate = sqlConnection.prepareStatement(updateBalance);

       //DEPOSIT
       preparedStatementDeposit.setInt(1, accountNumber);
       preparedStatementDeposit.setDouble(2, depositAmount);

       //UPDATE
       preparedStatementUpdate.setDouble(1, depositAmount);
       preparedStatementUpdate.setInt(2, accountNumber);

       preparedStatementUpdate.execute(); //2nd
       preparedStatementDeposit.execute();


       input.setText(" ");

       results.append("$"+ depositAmount + " deposited into account # :" + accountNumber + "\n");
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

        if(amountToWithdraw < accountBalance)
        {
            System.out.println("Enough funds available");
            withdrawl(accountNumber, amountToWithdraw);
        }
        else
            results.append("Sorry, you don't have enough funds");
    }


    private void withdrawl(int accountNumber, double withdrawlAmount)
    {
       // make connection to db

        sqlConnection = databaseConnection.createConnectionToDatabase();

        String withdrawlStatement = "UPDATE checking_account SET account_balance = account_balance - ? where account_number = ?";

        try
        {
        PreparedStatement preparedStatement = sqlConnection.prepareStatement(withdrawlStatement);

        preparedStatement.setDouble(1, withdrawlAmount);
        preparedStatement.setInt(2, accountNumber);

        preparedStatement.execute();

        results.append(withdrawlAmount + " withdrawn from account # " + accountNumber + "\n");
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




