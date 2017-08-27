package view;

import model.DatabaseConnection;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    //Testing

    public static void main(String[] args)
    {
        new AccessAccount("Omid","Nassir","123456789");
    }




    //made access package-private
     AccessAccount(String clientFirstName, String clientLastName, String clientSocial)
    {
        System.out.println("Access account");
        firstName = clientFirstName;
        lastName = clientLastName;
        social = clientSocial;

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
        connectToDatabase();
    }

    private void connectToDatabase()
    {
        System.out.println("Attempt to connect to database");

        Connection bankConnection = connection.createConnectionToDatabase();
        System.out.println("connection successful");

        try
        {

        Statement statement = bankConnection.createStatement();


        String sqlQuery = "SELECT * FROM clients";


        ResultSet resultSet = statement.executeQuery(sqlQuery);

        System.out.println("Database Accessed");

        System.out.println("Client info: ");

        while (resultSet.next())
        {
            results.append("Client name: \n" + resultSet.getString(1) + " " + resultSet.getString(2) + "   Social Security: " + resultSet.getString(3) + " Account number: " + resultSet.getString(4));
            results.append("\n");
        }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }


}
