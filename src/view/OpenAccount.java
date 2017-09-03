package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpenAccount extends JFrame // works
{

    private DatabaseConnection connection; // made private
    private boolean informationCorrect = false;
    private JButton buttonSubmit, buttonClear;
    private JLabel labelFirstName, labelLastName, labelSocial, labelAccountNumber;
    private JPanel panelLabels, panelInputs, panelButtons, panelTop;
    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputSocial;
    private JTextField inputAccountNumber;

    //TESTING

    public static void main(String[] args)
    {
        new OpenAccount();
    }

    // made access package-private
     OpenAccount()
    {

        System.out.println("Open Account");
            setSize(500, 120);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setTitle("Open Account");

            //LABELS
            labelFirstName = new JLabel("First Name: ");
            labelLastName = new JLabel("Last Name: ");
            labelSocial = new JLabel("Social Security #: ");
            labelAccountNumber = new JLabel("Account Number: ");

            //INPUTS
            inputFirstName = new JTextField(15);
            inputLastName = new JTextField(15);
            inputSocial = new JTextField(15);
            inputAccountNumber = new JTextField(15);

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
            inputSocial.setPreferredSize(textFieldDimension);

            //SET LAYOUTS
            panelLabels.setLayout(new GridLayout(5,1)); // LEFT -- displays correctly
            panelInputs.setLayout(new BoxLayout(panelInputs,BoxLayout.Y_AXIS)); // RIGHT


            //ADD LABELS TO PANEL - WEST
          /*  panelLabels.add(labelFirstName);
            panelLabels.add(labelLastName);*/
            panelLabels.add(labelAccountNumber);


            //ADD INPUTS TO PANEL - EAST
        /*    panelInputs.add(inputFirstName);
            panelInputs.add(inputLastName);*/
            panelInputs.add(inputAccountNumber);

            //ADD BUTTONS TO PANEL - SOUTH
            panelButtons.add(buttonSubmit);
            panelButtons.add(buttonClear);

            //ADD PANELS TO FRAME
            add(BorderLayout.WEST, panelLabels);
            add(BorderLayout.EAST, panelInputs);
            add(BorderLayout.SOUTH, panelButtons);

            setVisible(true);

            connection = new DatabaseConnection();
        }

        private void checkClientInfo()
        {

       /*     String firstName = inputFirstName.getText();
            String lastName = inputLastName.getText();
            String social = inputSocial.getText();*/
            String accountNumber = inputAccountNumber.getText();
           // System.out.println("social regular:" + social);

         //   social = social.replaceAll("[- ]", "");
           // System.out.println("social after regex: " + social);
/*

            if(firstName.length() == 0)
            {
                JOptionPane.showMessageDialog(null, "First name invalid");
                System.out.println("first name length: " + firstName.length());
            }
            else if(lastName.length() == 0)
            {
                JOptionPane.showMessageDialog(null, "Last name invalid");
                System.out.println("last name length: " + lastName.length());
            }
            else if(social.trim().length() != 9)
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
                */
                int accountNumberInteger = Integer.parseInt(accountNumber);
                boolean exist = doesAccountExist(accountNumberInteger);
                if (exist)
                {
                    dispose();
                    new AccessAccount(accountNumberInteger);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No account found for: " + accountNumberInteger);
                }
            }


    private boolean doesAccountExist(int accountNumber)
    {

        boolean exists = false;
        System.out.println("Attempt to connect to database to verify social");
        Connection bankConnection = connection.createConnectionToDatabase();
        System.out.println("Connection successfull");
        try
        {
        Statement query = bankConnection.createStatement();

        String sqlQuery = "SELECT first_name, last_name, account_number FROM clients";

        ResultSet resultSet = query.executeQuery(sqlQuery);

        while(resultSet.next())
        {

            //account number from database
           String accountNumberDatabase = resultSet.getString(3);
            System.out.println("account number database: " + accountNumberDatabase);
           String accountFromInput = inputAccountNumber.getText();
            System.out.println("account number input" + accountFromInput);
          //  System.out.println("social from database" + socialSecurityNumberDatabase);
            //System.out.println("social from input box" + socialSecurityInteger);

            if(accountNumberDatabase.equals(accountFromInput))
            {
                exists = true;
                System.out.println("Account found for: " + resultSet.getString(1) + " " + resultSet.getString(2));
                break;
            }

            else
                exists = false;
        }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return  exists;
    }

}// close new account


