import javax.swing.*;
import java.awt.*;


class AccessAccount {

    private JFrame frame;
    private JButton submitButton, clearButton;
    private JTextField textFieldFullName, textFieldSocial;


    AccessAccount()
    {
        createFrame();
    }

    private void createFrame()
    {
        frame = new JFrame("Access Account");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);


    }
}
