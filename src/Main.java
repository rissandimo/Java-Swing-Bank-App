import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main
{
    private JFrame frame;
    private JButton createAccount;
    private JButton accessAccount;
    private JLabel label;
    private JPanel contentPane;
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
        accessAccount = new JButton("Access Account");

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

    public static void main(String[] args)
    {
        new Main();
    }
}
