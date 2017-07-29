import javax.swing.*;
import java.awt.*;

class Account
{

    private JButton checkAcctInfo;
    private JButton deposit;
    private JButton logOut;
    private JButton submit;
    private JButton withdrawal;

    private JPanel panelBottom;
    private JPanel panelCenter;
    private JPanel panelTop;

    private JTextArea results;

   private String email, firstName, lastName, social;
    Account(String clientFirstName, String clientLastName, String clientEmail, String clientSocial)
    {
        firstName = clientFirstName;
        lastName = clientLastName;
        email = clientEmail;
        social = clientSocial;

        createFrame();

    }

    private void createFrame()
    {
        JFrame frame = new JFrame(firstName + "'s account");

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

       frame.add(panelTop, BorderLayout.NORTH);

       //CENTER PANEL

        results = new JTextArea(50,35);
        panelCenter = new JPanel();
        panelCenter.add(results);

        frame.add(panelCenter, BorderLayout.CENTER);


        //BOTTOM PANEL
        submit = new JButton("Submit");
        panelBottom = new JPanel();
        panelBottom.add(submit);
        frame.add(panelBottom, BorderLayout.SOUTH);


        frame.setSize(550, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //Testing purposes

    private Account()
    {
        createFrame();
    }

    public static void main(String[] args)
    {
        new Account();
    }
}
