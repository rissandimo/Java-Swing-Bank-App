import javax.swing.*;
import java.awt.event.*;

public class Main
{


    private Main()
    {
        Frame frame = new Frame();
    }

    class Frame extends JFrame
    {

        private JFrame frame;
        private JButton buttonCreateAccount;
        private JButton buttonAccessAccount;
        private JLabel label;
        private JPanel buttonsPanel;
        private JPanel labelPanel;



         Frame()
        {
            createFrame();
        }

        private void createFrame()
        {
            setBounds(400, 300, 450, 350);
            setTitle("Bank");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            buttonAccessAccount = new JButton("Acess Account");

            buttonAccessAccount.addActionListener( (actionEvent) ->
            {
                frame.dispose();
                new AccessAccount();

            });

            buttonCreateAccount = new JButton("Create Account");
            //replace with lambda
            buttonCreateAccount.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    frame.dispose();
                    new AccessAccount();
                }
            });

            setVisible(true);
        }
    }


/*    private void createFrame()
    {

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
    }*/

    public static void main(String[] args)
    {
        new Main();
    }
}
