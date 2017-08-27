package view;

import javax.swing.*;
import java.awt.*;

//this gets called if the new account info is correct
//it connect to the database, adds client info to the database
//then creates the atm screen
public class CreateAccount extends JFrame // works
{
    private int clientAge;
    private double clientBalance;

    private String clientEmail, clientFirstName, clientLastName, clientSocial;
    private JButton buttonSubmit, buttonClear;
    private JLabel labelFirstName, labelLastName, labelSocial, labelTelephone, labelEmail, labelSelection;
    private JPanel panelLabels, panelInputs, panelButtons, panelTop;
    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputSocial;
    private JTextField inputTelephone;
    private JTextField inputEmail;

    //TESTING

    public static void main(String[] args)
    {
        new CreateAccount();
    }

    //made access private
    private CreateAccount()
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
    }

    public String getClientEmail()
    {
        return clientEmail;
    }

    public String getClientFirstName()
    {
        return clientFirstName;
    }

    public String getClientLastName()
    {
        return clientLastName;
    }

    public String getClientSocial()
    {
        return clientSocial;
    }


}
