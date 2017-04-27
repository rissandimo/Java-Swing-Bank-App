import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args)
    {
        new Main();
    }


    private Main() {
        Frame frame = new Frame();
    }

    class Frame extends JFrame {

        private JFrame frame;
        private JButton buttonCreateAccount;
        private JButton buttonAccessAccount;
        private JLabel label;
        private JPanel buttonsPanel;
        private JPanel labelPanel;


        Frame() {
            createFrame();
        }

        private void createFrame() {
            setBounds(400, 300, 450, 350);
            setTitle("Bank");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


            //LABEL

            label = new JLabel();


            //BUTTONS

            buttonAccessAccount = new JButton("Access Account");

            buttonAccessAccount.addActionListener((ActionEvent actionEvent) ->
            {
                this.dispose();
                new AccessAccount();

            });

            buttonCreateAccount = new JButton("Create Account");

            //replace with lambda
            buttonCreateAccount.addActionListener((ActionEvent actionEvent) ->
                {
                    frame.dispose();
                    new AccessAccount();
                });

            buttonsPanel = new JPanel();
            buttonsPanel.add(buttonAccessAccount);
            buttonsPanel.add(buttonCreateAccount);

            add(buttonsPanel, BorderLayout.SOUTH);

            setVisible(true);
        }
    }
}


