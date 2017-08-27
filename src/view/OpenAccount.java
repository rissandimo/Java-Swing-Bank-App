package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenAccount extends JFrame // works
{
    //private int clientAge;
   // private double clientBalance;

   // private String clientEmail, clientFirstName, clientLastName, clientSocial;
    private boolean informationCorrect = false;
    private JButton buttonSubmit, buttonClear;
    private JLabel labelFirstName, labelLastName, labelSocial, labelSelection;
    private JPanel panelLabels, panelInputs, panelButtons, panelTop;
    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputSocial;

    //TESTING

    public static void main(String[] args)
    {
        new OpenAccount();
    }

    public OpenAccount()
    {
            setSize(500, 300);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setTitle("Open Account");

            //LABELS
            labelFirstName = new JLabel("First Name: ");
            labelLastName = new JLabel("Last Name: ");
            labelSocial = new JLabel("Social Security #: ");
            labelSelection = new JLabel("Pleae make a selection");

            //INPUTS
            inputFirstName = new JTextField(15);
            inputLastName = new JTextField(15);
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
            inputSocial.setPreferredSize(textFieldDimension);

            //SET LAYOUTS
            panelLabels.setLayout(new GridLayout(5,1)); // LEFT -- displays correctly
            panelInputs.setLayout(new BoxLayout(panelInputs,BoxLayout.Y_AXIS)); // RIGHT

        //ADD LABELS TO PANEL - North
            panelTop.add(labelSelection);

            //ADD LABELS TO PANEL - WEST
            panelLabels.add(labelFirstName);
            panelLabels.add(labelLastName);
            panelLabels.add(labelSocial);


            //ADD INPUTS TO PANEL - EAST
            panelInputs.add(inputFirstName);
            panelInputs.add(inputLastName);
            panelInputs.add(inputSocial);

            //ADD BUTTONS TO PANEL - SOUTH
            panelButtons.add(buttonSubmit);
            panelButtons.add(buttonClear);

            //ADD PANELS TO FRAME
            add(BorderLayout.NORTH, panelTop);
            add(BorderLayout.WEST, panelLabels);
            add(BorderLayout.EAST, panelInputs);
            add(BorderLayout.SOUTH, panelButtons);

            setVisible(true);
        }

        private void checkClientInfo()
        {

            String firstName = inputFirstName.getText();
            String lastName = inputLastName.getText();
            String social = inputSocial.getText();
            System.out.println("social regular:" + social);

            social = social.replaceAll("[- ]", "");
            System.out.println("social after regex: " + social);


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
                dispose();
                new AccessAccount(firstName, lastName, social);
            }


        }

}// close new account


