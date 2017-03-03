import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main
{
    private JFrame frame;
    private JButton createAccount;
    private JButton accessAccount;
    private JLabel label;
    private JPanel buttonsPanel;
    private JPanel labelPanel;

    private Main()
    {
        createFrame();
    }


    private void createFrame()
    {
        frame = new JFrame("Bank");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 350);


        createAccount = new JButton("Create Account");
        createAccount.addActionListener(new CreateAccountListener());
        accessAccount = new JButton("Access Account");
        accessAccount.addActionListener(new AccessAccountListener());

        label = new JLabel("Please make a selection below");
        labelPanel = new JPanel();
        labelPanel.add(label);

        frame.getContentPane().add(BorderLayout.CENTER, labelPanel);

        buttonsPanel = new JPanel();
        buttonsPanel.add(createAccount);
        buttonsPanel.add(accessAccount);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);


        frame.setVisible(true);
    }


    class CreateAccountListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            frame.dispose();
            new CreateAccount();
        }
    }

    class AccessAccountListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            frame.dispose();
            new AccessAccount();
        }
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
