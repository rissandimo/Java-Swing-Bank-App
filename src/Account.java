import javax.swing.*;

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

    private void startProgram()
    {
        JFrame frame = new JFrame(firstName + "'s account");
        frame.setSize(350, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
