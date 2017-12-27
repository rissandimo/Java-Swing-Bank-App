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

    private int accountNumber;

    public AccessAccount(int accountNumber)
    {
        System.out.println("AccessAccount");

        createView();

        databaseConnection = new DatabaseConnection();
        connectToDatabase(accountNumber);

        this.accountNumber = accountNumber;
    }

    private void createView()
    {

        //TOP PANEL - BUTTONS
        deposit = new JButton("Deposit");
        withdrawal = new JButton("Withdrawal");
        checkAcctInfo = new JButton("Account Info");
        logOut = new JButton("Log Out");

        panelTop = new JPanel();
        panelTop.add(deposit);
        panelTop.add(withdrawal);
        panelTop.add(checkAcctInfo);
        panelTop.add(logOut);

        add(panelTop, BorderLayout.NORTH);


        transactionActionListener = new TransactionActionListener();

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
               deposit(Integer.parseInt(input.getText()));

        });

/*        submit.addActionListener(e ->
        {
            if(TransactionActionListener.actionPerformed.equals("Deposit"))
                deposit(Double.parseDouble(input.getText()));
        });*/

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
        System.out.println("deposit amount: " + depositAmount);

        //connect to db and perform deposit
       sqlConnection = databaseConnection.createConnectionToDatabase();

       String depositStatement = "UPDATE checking_account SET account_balance = account_balance + ? WHERE account_number = ?";

       try
       {

       PreparedStatement preparedStatement = sqlConnection.prepareStatement(depositStatement);

       preparedStatement.setDouble(1, depositAmount);
       preparedStatement.setInt(2, accountNumber);

       preparedStatement.execute();

       results.append("$"+ depositAmount + " deposited into account# :" + accountNumber);
       }
       catch(SQLException sqlE)
       {
           sqlE.printStackTrace();
       }
    } // close deposit

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


}// close AccessAccount


class TransactionActionListener implements ActionListener
{

    static String actionPerformed;

    public void actionPerformed(ActionEvent e)
    {
        System.out.println("TransactionActionListener");

        if(e.getActionCommand().equals("Deposit"))
        {
            System.out.println("deposit button pressed");
            AccessAccount.results.append("Please enter amount to deposit below \n");
            actionPerformed = e.getActionCommand();
        }

        else if(e.getActionCommand().equals("Withdrawal"))
        {
            AccessAccount.results.append("Please enter amount to withdrawal below \n");
            actionPerformed = e.getActionCommand();
        }

    }

}


