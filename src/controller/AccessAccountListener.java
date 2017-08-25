package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.*;

/**
 * Class implements ActionListener, creates CreateAccount gui
 */
public class AccessAccountListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        new CreateAccount();
    }
}
