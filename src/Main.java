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

    // Can't figure out how to position the elements correctly
    private void createFrame()
    {
        frame = new JFrame("Bank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 350);


        contentPane = new JPanel();
        frame.setContentPane(contentPane);


        createAccount = new JButton("Create Account");
        accessAccount = new JButton("Access Account");

        label = new JLabel("Please make a selection below");
        labelPanel = new JPanel();
        labelPanel.add(label);

        contentPane.add(labelPanel, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.add(createAccount);
        buttonsPanel.add(accessAccount);

        contentPane.add(buttonsPanel, BorderLayout.SOUTH);


        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
