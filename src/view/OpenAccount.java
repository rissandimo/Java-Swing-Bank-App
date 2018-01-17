package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpenAccount{

    public static void main(String[] args)
    {
        new OpenAccount();
    }

    OpenAccount()
    {
        new OpenAccountFrame();
    }

    class OpenAccountFrame extends JFrame
    {
        private DatabaseConnection connection;

        private JTextField inputAccountNumber;

        OpenAccountFrame()
        {
            setSize(500, 120);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setTitle("Open Account");

            JLabel labelAccountNumber = new JLabel("Account Number: ");

            inputAccountNumber = new JTextField(15);

            //PANELS
            JPanel panelButtons = new JPanel();
            JPanel panelInputs = new JPanel();
            JPanel panelLabels = new JPanel();


            JButton buttonSubmit = new JButton("Submit");
            buttonSubmit.addActionListener(e -> checkClientInfo());

            //SET LAYOUTS
            panelLabels.setLayout(new GridLayout(5,1)); // LEFT -- displays correctly
            panelInputs.setLayout(new BoxLayout(panelInputs,BoxLayout.Y_AXIS)); // RIGHT


            //ADD LABELS TO PANEL - WEST
            panelLabels.add(labelAccountNumber);


            //ADD INPUTS TO PANEL - EAST
            panelInputs.add(inputAccountNumber);

            //ADD BUTTONS TO PANEL - SOUTH
            panelButtons.add(buttonSubmit);

            //ADD PANELS TO FRAME
            add(BorderLayout.WEST, panelLabels);
            add(BorderLayout.EAST, panelInputs);
            add(BorderLayout.SOUTH, panelButtons);

            setVisible(true);

            connection = new DatabaseConnection();
        }

        private boolean doesAccountExist()
        {

            boolean accountExists = false;
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
                    String accountFromInput = inputAccountNumber.getText();

                    if(accountNumberDatabase.equals(accountFromInput))
                    {
                        accountExists = true;
                        System.out.println("Account found for: " + resultSet.getString(1) + " " + resultSet.getString(2));
                        break;
                    }
                    else
                        accountExists = false;
                }
            }
            catch(SQLException e) { e.printStackTrace(); }
            return  accountExists;
        }


        private void checkClientInfo()
        {
            String accountNumber = inputAccountNumber.getText();

            int accountNumberInteger = Integer.parseInt(accountNumber);
            boolean exist = doesAccountExist();
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

    }

}// close new account


