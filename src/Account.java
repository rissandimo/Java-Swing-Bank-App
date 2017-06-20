import javax.swing.*;
import java.awt.*;

class Account
{
   private String email, firstName, lastName, social;
    Account(String clientFirstName, String clientLastName, String clientEmail, String clientSocial)
    {
        firstName = clientFirstName;
        lastName = clientLastName;
        email = clientEmail;
        social = clientSocial;

        startProgram();
    }

    public Account()
    {
        startProgram();
    }

    private void startProgram()
    {
        createFrame();
    }

    private void createFrame()
    {

        JFrame frame = new JFrame(firstName + "'s account");

        TextAreaPanel textAreaPanel = new TextAreaPanel();
        SelectionPanel selectionPanel = new SelectionPanel();
        BottomPanel bottomPanel = new BottomPanel();

        frame.add(selectionPanel, BorderLayout.NORTH);
        frame.add(textAreaPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setBounds(300, 300, 400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    class TextAreaPanel extends JPanel
    {
        private JTextArea textArea_Result;

         TextAreaPanel()
        {
            textArea_Result = new JTextArea(15,30);
            add(textArea_Result);
        }
    }// TextAreaPanel

    class SelectionPanel extends JPanel
    {

         SelectionPanel()
        {

            JLabel label_makeSelection = new JLabel("Please make a selection:");
            add(label_makeSelection);

            ButtonsPanel buttonsPanel = new ButtonsPanel();
            add(buttonsPanel);
        }

        class ButtonsPanel extends JPanel
        {
            ButtonsPanel()
            {

            JButton button_deposit, button_withdrawal, button_checkAcctInfo;

            button_deposit = new JButton("Deposit");
            button_withdrawal = new JButton("Withdrawal");
            button_checkAcctInfo = new JButton("Account Info");

            add(button_deposit);
            add(button_withdrawal);
            add(button_checkAcctInfo);
            }

        }
    }

    class BottomPanel extends JPanel
    {
        JButton button_logOut, button_submit;

         BottomPanel()
        {
            button_logOut = new JButton("Log Out");
            button_submit = new JButton("Submit");

            add(button_logOut);
            add(button_submit);
        }
    }

    public static void main(String[] args)
    {
        new Account();
    }


}// Account
