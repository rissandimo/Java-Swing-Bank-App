package view;

import model.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

 public class AccessAccount extends JFrame // works
{
    private DatabaseConnection connection; // made private

    private JButton checkAcctInfo;
    private JButton deposit;
    private JButton logOut;
    private JButton submit;
    private JButton withdrawal;

    private JPanel panelBottom;
    private JPanel panelCenter;
    private JPanel panelTop;

    private JTextArea results;

    private String firstName, lastName, social;

    private ArrayList<Integer> clients = new ArrayList<>();


    private int accountNumber;


    //made access package-private
    public AccessAccount(int accountNumber)
    {
        System.out.println("Access account");


        //TOP PANEL
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

        //CENTER PANEL

        results = new JTextArea(50,50);
        panelCenter = new JPanel();
        panelCenter.add(results);

        add(panelCenter, BorderLayout.CENTER);


        //BOTTOM PANEL
        submit = new JButton("Submit");
        panelBottom = new JPanel();
        panelBottom.add(submit);
        add(panelBottom, BorderLayout.SOUTH);


        setSize(650, 600);
        setVisible(true);
        setTitle("Access account");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        connection = new DatabaseConnection();
        this.accountNumber = accountNumber;
        connectToDatabase(accountNumber);
    }

    private void connectToDatabase(int accountNumber)
    {
        System.out.println("Attempt to connect to database");

        Connection bankConnection = connection.createConnectionToDatabase();
        System.out.println("connection successful");

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


    }


}
