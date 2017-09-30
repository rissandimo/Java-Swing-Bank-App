package view;

import javax.swing.*;
import java.awt.*;

/**
 * Allows customer to access existing account or to create a new one
 */
public class WelcomeScreen extends JFrame
{

        private JButton buttonCreateAccount;
        private JButton buttonAccessAccount;
        private JLabel labelSelection;
        private JPanel buttonsPanel;
        private JPanel labelPanel;

    public WelcomeScreen() {
        System.out.println("Welcome screen");


        setBounds(400, 300, 500, 400);
        setTitle("Nassir Bank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //BUTTONS

        buttonAccessAccount = new JButton("Access Account");
        buttonAccessAccount.addActionListener(e ->
        {
            dispose();
            new OpenAccount();
        });

        buttonCreateAccount = new JButton("Create Account");
        buttonCreateAccount.addActionListener(e ->
        {
            dispose();
            new CreateAccount();
        });

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
