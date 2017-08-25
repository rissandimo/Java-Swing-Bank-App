package application;

import controller.AccessAccountListener;

import javax.swing.*;
import java.awt.*;

public class Main // works
{

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
            setBounds(400, 300, 500, 400);
            setTitle("Bank");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //BUTTONS

            buttonAccessAccount = new JButton("Access Account");
            buttonAccessAccount.addActionListener(new AccessAccountListener());

            buttonCreateAccount = new JButton("Create Account");

            //LABELS

            labelSelection = new JLabel("Please make a selection below");


            //PANELS
            buttonsPanel = new JPanel();
            labelPanel = new JPanel();


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


