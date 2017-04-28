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
        private JLabel labelSelection;
        private JPanel buttonsPanel;
        private JPanel labelPanel;


        Frame() {
            createFrame();
        }

        private void createFrame() {
            setBounds(400, 300, 450, 350);
            setTitle("Bank");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //BUTTONS

            buttonAccessAccount = new JButton("Access Account");
            buttonCreateAccount = new JButton("Create Account");

            //LABELS

            labelSelection = new JLabel("Please make a selection below");


            //PANELS
            buttonsPanel = new JPanel();
            labelPanel = new JPanel();

            //Action Listeners

            buttonAccessAccount.addActionListener((ActionEvent actionEvent) ->
            {
                this.dispose();
                new AccessAccount();

            });


            //replace with lambda
            buttonCreateAccount.addActionListener((ActionEvent actionEvent) ->
                {
                    this.dispose();
                    new CreateAccount();
                });

            // ADD COMPONENTS TO FRAME

            buttonsPanel.add(buttonAccessAccount);
            buttonsPanel.add(buttonCreateAccount);

            labelPanel.add(labelSelection);

            add(buttonsPanel, BorderLayout.SOUTH);
            add(labelPanel, BorderLayout.CENTER);

            setVisible(true);
        }
    }
}


