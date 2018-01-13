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

    //  private String clientEmail, clientFirstName, clientLastName, clientSocial;
    private JButton buttonSubmit, buttonClear;
    private JLabel labelFirstName, labelLastName, labelSocial, labelTelephone, labelEmail, labelSelection;
    private JPanel panelLabels, panelInputs, panelButtons, panelTop;
    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputSocial;
    private JTextField inputTelephone;
    private JTextField inputEmail;

    private DatabaseConnection connection;
    private boolean doesClientExist;

    //TESTING

    public static void main(String[] args)
    {
        new CreateAccount();
    }

    CreateAccount()
    {

        setSize(350, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Create Account");

        //LABELS
        labelFirstName = new JLabel("First Name: ");
        labelLastName = new JLabel("Last Name: ");
        labelEmail = new JLabel("Email: ");
        labelSelection = new JLabel("Please make a selection below");
        labelSocial = new JLabel("Social Security #: ");
        labelTelephone = new JLabel("Telephone #");

        //INPUTS
        inputFirstName = new JTextField(15);
        inputLastName = new JTextField(15);
        inputEmail = new JTextField(15);
        inputTelephone = new JTextField(15);
        inputSocial = new JTextField(15);

        //PANELS
        panelButtons = new JPanel();
        panelInputs = new JPanel();
        panelLabels = new JPanel();
        panelTop = new JPanel();


        //BUTTONS
        buttonClear = new JButton("Clear");
        buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(e -> checkClientInfo());


        //DIMENSION
        Dimension textFieldDimension = new Dimension(5,0);

        inputFirstName.setPreferredSize(textFieldDimension);
        inputLastName.setPreferredSize(textFieldDimension);
        inputEmail.setPreferredSize(textFieldDimension);
        inputTelephone.setPreferredSize(textFieldDimension);
        inputSocial.setPreferredSize(textFieldDimension);

        //SET LAYOUTS
        panelLabels.setLayout(new GridLayout(5,1)); // LEFT -- displays correctly
        panelInputs.setLayout(new BoxLayout(panelInputs,BoxLayout.Y_AXIS)); // RIGHT

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
        // panelInputs.add(inputEmail);
        panelInputs.add(inputSocial);
        //  panelInputs.add(inputTelephone);

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
        System.out.println("social regular:" + social);

        social = social.replaceAll("[- ]", "");
        System.out.println("social after regex: " + social);


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
            System.out.println("Information correct");
            System.out.println("first name: " + firstName);
            System.out.println("last name: " + lastName);
            System.out.println("social length after trim: " + social.trim().length());
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
