import functions.Functions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CreateAccount
{
    private int clientAge;
    private double clientBalance;


    public static void main(String[] args)
    {
        new CreateAccount();
    }



    CreateAccount()
    {
        new Frame();
    }

        class Frame extends JFrame
        {
            private String clientEmail, clientFirstName, clientLastName, clientSocial;
            private JButton buttonSubmit, buttonClear;
            private JLabel labelFirstName, labelLastName, labelSocial, labelTelephone, labelEmail, labelSelection;
            private JPanel panelLabels, panelInputs, panelButtons, panelTop;
            JTextField inputFirstName, inputLastName, inputSocial, inputTelephone, inputEmail;


              Frame()
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
                labelSelection = new JLabel("Plese make a selection below");
                labelSocial = new JLabel("Social Security #: ");
                labelTelephone = new JLabel("Telphone #");

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

                buttonSubmit.addActionListener( (ActionEvent actionEvent) ->
                        checkClientInfo());



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
                panelLabels.add(labelEmail);
                panelLabels.add(labelSocial);
                panelLabels.add(labelTelephone);

                panelTop.add(labelSelection);

                //ADD INPUTS TO PANEL
                panelInputs.add(inputFirstName);
                panelInputs.add(inputLastName);
                panelInputs.add(inputEmail);
                panelInputs.add(inputTelephone);
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
        }

             boolean checkClientInfo()
            {
                boolean informationCorrect;
                this.clientFirstName = inputFirstName.getText();
                this.clientLastName = inputLastName.getText();
                this.clientEmail = inputEmail.getText();
                this.clientSocial = inputSocial.getText();

                //check WHY I have to put an exclamation point
                if(!Functions.isStringEmpty(clientFirstName))
                {
                    JOptionPane.showMessageDialog(null, "First name can't be empty");
                    informationCorrect = false;
                }

                else
                    {
                    System.out.println("Clients name is : " + clientFirstName);
                    informationCorrect = true;
                }
                /*
                else if(clientLastName.equalsIgnoreCase(""))
                {
                    JOptionPane.showMessageDialog(null, "Last name can't be empty");
                }

                for(int i=0; i < clientEmail.length(); i++)
                {
                    if(clientEmail.charAt(i) != '@')
                    {
                        JOptionPane.showMessageDialog();
                    }

                }
                */

                return informationCorrect;
            }

            private void createNewClient(String clientFirstName, String clientLastName, String clientEmail, String clientSocial)
            {


            }


    }





}
