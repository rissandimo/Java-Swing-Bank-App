package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class AccessAccount extends JFrame
{

    //TESTING PURPOSES
    public static void main(String[] args)
    {
        new AccessAccount(1);
    }

    private Connection bankConnection;
    private PreparedStatement preparedStatementClient;

    protected static JTextArea results;
    private JTextField input;
    private JPanel panelTop;

    private int accountNumber;
    private double accountBalance;

    public AccessAccount(int accountNumber)
    {
        this.setAccountNumber(accountNumber);

        createView();

        connectToDatabase();

        listClients();

    }

    private void connectToDatabase()
    {
        bankConnection = new DatabaseConnection().createConnectionToDatabase();
    }

    private void showBalance() // used for check balance button
    {

        String accountInfoStatement = "SELECT account_balance as balance from checking_account where account_number = ?";

        try
        {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setInt(1, getAccountNumber());

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


    private void listClients()
    {
        try
        {

            String selectCustomers = "SELECT first_name, last_name, account_number FROM clients where account_number = ?";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(selectCustomers);

            preparedStatement.setInt(1, getAccountNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                results.append("Client info: ");
                results.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n" +
                        "Account number: " + resultSet.getString(3) + "\n");
            }

        }
        catch(SQLException e) { e.printStackTrace(); }
    }

    private void createMenu()
    {
        MenuActionListener menuListener = new MenuActionListener();

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem save = new JMenuItem("Save Deposit History");

        //Register save button
        save.addActionListener(menuListener);

        fileMenu.add(save);

        menuBar.add(fileMenu);

        panelTop.add(menuBar);
    }

    private void createView()
    {
        setSize(650, 600);
        setTitle("Access account");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //TOP PANEL - BUTTONS
        panelTop = new JPanel();

        createMenu();

        JButton deposit = new JButton("Deposit");

        JButton withdrawal = new JButton("Withdrawal");

        JButton checkAcctInfo = new JButton("Account Info");
        checkAcctInfo.addActionListener(e-> showBalance());

        JButton logOut = new JButton("Log Out");

        logOut.addActionListener(e ->
        {
            results.append("Logging out of account");
            try
            {
                Thread.sleep(2000);
                dispose();
            }
            catch(InterruptedException e2){e2.printStackTrace();}
        });

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
                areEnoughFundsAvailable();
            if(TransactionActionListener.actionPerformed.equals(""))
                results.append("Please make a selection above \n");
        });


        JPanel panelBottom = new JPanel();
        panelBottom.add(input);
        panelBottom.add(submit);
        add(panelBottom, BorderLayout.SOUTH);


        setVisible(true);
    }


    private void deposit(double depositAmount)
    {
        String deposit = "INSERT INTO checking_deposit (account_number, deposit) VALUES (?,?)";

        String updateBalance = "UPDATE checking_account SET account_balance = account_balance + ? WHERE account_number = ?";


        try
        {

            PreparedStatement tbl_checking_deposit = bankConnection.prepareStatement(deposit);

            PreparedStatement tbl_checking_account = bankConnection.prepareStatement(updateBalance);

            //DEPOSIT - Checking Deposit
            tbl_checking_deposit.setInt(1, getAccountNumber());
            tbl_checking_deposit.setDouble(2, depositAmount);

            //UPDATE - Checking account
            tbl_checking_account.setDouble(1, depositAmount);
            tbl_checking_account.setInt(2, getAccountNumber());

            tbl_checking_account.execute();
            tbl_checking_deposit.execute();

            input.setText(" ");

            results.append(String.format("$ %.2f deposited into account # %d \n", depositAmount, getAccountNumber()));
        }
        catch(SQLException sqlException) { sqlException.printStackTrace(); }

    } // close deposit

    private void areEnoughFundsAvailable()
    {

        double amountToWithdraw = Double.parseDouble(input.getText());

        String checkFunds = "SELECT account_balance FROM checking_account WHERE account_number = ?";

        try
        {
            preparedStatementClient = bankConnection.prepareStatement(checkFunds);

            preparedStatementClient.setInt(1, getAccountNumber());

            ResultSet balanceResultSet = preparedStatementClient.executeQuery();

            while(balanceResultSet.next()) setAccountBalance(balanceResultSet.getDouble(1));
        }
        catch(SQLException sqlException) { sqlException.printStackTrace(); }

        if(amountToWithdraw <= getAccountBalance())
        {
            withdrawal(amountToWithdraw);
        }
        else results.append("Sorry, you don't have enough funds \n");
    }

    private void saveClientInfo(BufferedWriter fileWriter)
    {
        String clientName = "SELECT first_name, last_name from clients where account_number = ?";

        try
        {

        //Client name
        preparedStatementClient = bankConnection.prepareStatement(clientName);

        preparedStatementClient.setInt(1, getAccountNumber());

        ResultSet resultsClient = preparedStatementClient.executeQuery();

        fileWriter = new BufferedWriter(new PrintWriter(fileWriter));

        if(resultsClient.next())
        {
            fileWriter.write("Client name: " + resultsClient.getString("first_name") + " "
                    + resultsClient.getString("last_name"));
        }

        fileWriter.newLine();
        fileWriter.newLine();

        fileWriter.flush();

        }
        catch(SQLException | IOException e)
        {
            e.printStackTrace();
        }

            System.out.println("File created");

    }

    private void saveCheckingDepositInfo(BufferedWriter fileWriter)
    {

        String checkingDeposit = "SELECT account_number, trans_number, deposit FROM checking_deposit where account_number" +
                " = ?";

        try
        {

            //Checking deposit
            PreparedStatement preparedStatementCheckingDeposit = bankConnection.prepareStatement(checkingDeposit);

            preparedStatementCheckingDeposit.setInt(1, getAccountNumber());

            fileWriter.write("Account \t Transaction \t Deposit Amount \t \n");
            fileWriter.flush();
            fileWriter.newLine();

            ResultSet resultsCheckingDeposit = preparedStatementCheckingDeposit.executeQuery();

            while(resultsCheckingDeposit.next())
            {
                fileWriter.write(resultsCheckingDeposit.getInt(1) + " \t \t \t" +  resultsCheckingDeposit.getInt(2) +
                        " \t\t" + resultsCheckingDeposit.getDouble
                        (3) + "\n");
                fileWriter.newLine();
            }

            results.append("Checking account saved \n");

        }
        catch(SQLException | IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                fileWriter.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void saveCheckingAccount()
    {

        BufferedWriter fileWriter = null;
        File checkingInfo = new File("C:\\Users\\Omid\\Desktop\\checking_info.txt");

        try
        {
        fileWriter = new BufferedWriter(new FileWriter(checkingInfo));

            //noinspection ResultOfMethodCallIgnored
            checkingInfo.createNewFile();

        saveClientInfo(fileWriter);
        System.out.println("Client info saved");

        saveCheckingDepositInfo(fileWriter);
            System.out.println("Checking deposit info saved");
        }
        catch(IOException exception) {exception.printStackTrace();}


    }


    private void withdrawal(double withdrawlAmount)
    {

        String updateStatement = "UPDATE checking_account SET account_balance = account_balance - ? where account_number = ?";

        String withdrawalStatement = "INSERT INTO checking_withdrawl (account_number, withdrawal) values(?,?)";

        try
        {
            PreparedStatement preparedStatementWithdrawal = bankConnection.prepareStatement(withdrawalStatement);
            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            //WITHDRAWAL
            preparedStatementWithdrawal.setInt(1, getAccountNumber());
            preparedStatementWithdrawal.setDouble(2, withdrawlAmount);

            //UPDATE
            preparedStatementUpdate.setDouble(1, withdrawlAmount);
            preparedStatementUpdate.setInt(2, getAccountNumber());

            //Update checking account then make withdrawal
            preparedStatementUpdate.execute();
            preparedStatementWithdrawal.execute();

            results.append(String.format("$ %.2f withdrawn from account # %d \n", withdrawlAmount, getAccountNumber()));

            input.setText("");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance()
    {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance)
    {
        this.accountBalance = accountBalance;
    }

    class MenuActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("action performed");
            if(actionEvent.getActionCommand().equals("Save Deposit History")) saveCheckingAccount();
        }
    }
}// close AccessAccount


class TransactionActionListener implements ActionListener
{
    static String actionPerformed = "";

    public void actionPerformed(ActionEvent e)
    {
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