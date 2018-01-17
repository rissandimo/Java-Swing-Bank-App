package view;

import controller.CreateClientAccount;
import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/*
CreateAccount gets called if the new account info is correct
It connect to the database, adds client info to the database
then creates the atm screen.
*/

public class CreateAccount extends JFrame
{
/*    private int clientAge;
    private double clientBalance;*/

    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputSocial;

    private DatabaseConnection connection;
    private boolean doesClientExist;

    //TESTING

    public static void main(String[] args)
    {
        new CreateAccount();
    }

    CreateAccount()
    {

        setSize(450, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Create Account");

        //LABELS
        JLabel labelFirstName = new JLabel("First Name: ");
        JLabel labelLastName = new JLabel("Last Name: ");
        JLabel labelSelection = new JLabel("Please make a selection below");
        JLabel labelSocial = new JLabel("Social Security #: ");

        //INPUTS
        inputFirstName = new JTextField(15);
        inputLastName  = new JTextField(15);
        inputSocial    = new JTextField(15);

        //PANELS
        JPanel panelButtons = new JPanel();
        JPanel panelInputs = new JPanel();
        JPanel panelLabels = new JPanel();
        JPanel panelTop = new JPanel();


        //BUTTONS
        JButton buttonClear = new JButton("Clear");
        JButton buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(e -> checkClientInfo());


        //DIMENSION
        Dimension textFieldDimension = new Dimension(5,0);

        inputFirstName.setPreferredSize(textFieldDimension);
        inputLastName.setPreferredSize(textFieldDimension);
        inputSocial.setPreferredSize(textFieldDimension);

        //SET LAYOUTS
        panelLabels.setLayout(new GridLayout(3,1)); // LEFT -- displays correctly
       // panelInputs.setLayout(new BoxLayout(panelInputs,BoxLayout.Y_AXIS)); // RIGHT
        panelInputs.setLayout(new GridLayout(3,1));

        //ADD LABELS TO PANEL
        panelLabels.add(labelFirstName);
        panelLabels.add(labelLastName);
        //  panelLabels.add(labelEmail);
        panelLabels.add(labelSocial);
        // panelLabels.add(labelTelephone);

        panelTop.add(labelSelection);

        //ADD INPUTS TO PANEL
        panelInputs.add(inputFirstName);
        panelInputs.add(inputLastName);
        panelInputs.add(inputSocial);

        //ADD BUTTONS TO PANEL
        panelButtons.add(buttonSubmit);
        panelButtons.add(buttonClear);

        //ADD PANELS TO FRAME
        add(BorderLayout.NORTH, panelTop);
        add(BorderLayout.WEST, panelLabels);
        add(BorderLayout.EAST, panelInputs);
        add(BorderLayout.SOUTH, panelButtons);

        setVisible(true);

        connection = new DatabaseConnection();
    }

    private void checkClientInfo()
    {
        int account_number = getBiggestAccountNumber();
        boolean informationCorrect;
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String social = inputSocial.getText();

        social = social.replaceAll("[- ]", "");


        if (firstName.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "First name invalid");
            System.out.println("first name length: " + firstName.length());
        } else if (lastName.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Last name invalid");
            System.out.println("last name length: " + lastName.length());
        } else if (social.trim().length() != 9)
        {
            JOptionPane.showMessageDialog(null, "Social Security Number invalid");
        }
        else
        {
            informationCorrect = true;


            if (informationCorrect)
            {
                doesClientExist = doesClientExist(social);
                if(!doesClientExist)
                {
                    dispose();
                    new CreateClientAccount(firstName, lastName, social, account_number);
                }

            }
        }

    }

    private boolean doesClientExist(String social)
    {
        System.out.println("doesClientExist");
        Connection bankConnection = connection.createConnectionToDatabase();

        try
        {

           // Statement statement = bankConnection.createStatement();

            String checkClient = "SELECT social FROM clients";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(checkClient);

            ResultSet ssnResultSet = preparedStatement.executeQuery();

            while(ssnResultSet.next())
            {
                System.out.println("SSN found: " + ssnResultSet.getString(1));
                if(ssnResultSet.getString(1).equals(social))
                {
                    JOptionPane.showMessageDialog(null, "Social already exists");
                    return true;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }// end does client exist

    private int getBiggestAccountNumber()
    {
        System.out.println("getBiggestAccountNumber()");
        Connection bankConnection = connection.createConnectionToDatabase();

        int largestAccountNumber = 0;

        try
        {
           // Statement statement = bankConnection.createStatement();

            String largestAcctNum = "SELECT MAX(account_number) from clients";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(largestAcctNum);

            ResultSet resultlargestAcctNum = preparedStatement.executeQuery();

            while(resultlargestAcctNum.next())
            {
                largestAccountNumber = resultlargestAcctNum.getInt(1);
                 System.out.println("Largest account# found: " + largestAccountNumber);
                return largestAccountNumber;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

            return largestAccountNumber;
    }

}
