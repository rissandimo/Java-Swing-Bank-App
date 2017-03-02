import java.awt.*;
import javax.swing.*;

class CreateAccount
{
    private JFrame frame;

    private JButton buttonSubmit, buttonClear;
    private JLabel labelFirstName, labelLastName, labelSocial, labelTelephone, labelEmail;
    private JPanel panelLabels, panelInputs, panelButtons, panelTop;
    private JTextField inputFirstName, inputLastName, inputSocial, inputTelephone, inputEmail;

    CreateAccount()
    {
        createFrame();
    }

    private void createFrame()
    {
        frame = new JFrame("Create Account");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        labelEmail = new JLabel("Email: ");
        labelFirstName = new JLabel("First Name: ");
        labelLastName = new JLabel("Last Name: ");
        labelSocial = new JLabel("Social Security #: ");
        labelTelephone = new JLabel("Telphone #");

        buttonClear = new JButton("Clear");
        buttonSubmit = new JButton("Submit");

        inputFirstName = new JTextField();
        inputLastName = new JTextField();
        inputEmail = new JTextField();
        inputTelephone = new JTextField();
        inputSocial = new JTextField();

        panelButtons = new JPanel();
        panelInputs = new JPanel();
        panelLabels = new JPanel();
        panelTop = new JPanel();

        panelButtons.add(buttonSubmit);
        panelButtons.add(buttonClear);

        frame.getContentPane().add(BorderLayout.SOUTH, panelButtons);

        frame.setVisible(true);


    }


}
