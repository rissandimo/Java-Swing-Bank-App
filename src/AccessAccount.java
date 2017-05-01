import javax.swing.*;
import java.awt.*;


class AccessAccount {

    //changed to package-private
    AccessAccount()
    {
        new Frame();
    }


    class Frame extends JFrame
    {
        private JButton submitButton, clearButton;
        private JPanel panelButtons, panelLabels, panelTextFields;
        private JTextField textFieldFullName, textFieldSocial;

        //changed to package-private
        Frame()
        {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setSize(340, 120);
            setTitle("Access Account");


            //BUTTONS

            submitButton = new JButton("Submit");
            clearButton = new JButton("Clear");

            //PANELS
            panelButtons = new JPanel();
            panelLabels = new JPanel();
            panelTextFields = new JPanel();

            //SET LAYOUT FOR PANELS
            panelTextFields.setLayout(new BoxLayout(panelTextFields, BoxLayout.Y_AXIS));

            //DIMENSIONS
            Dimension textFieldDimension = new Dimension(5,0);

            // TEXT FIELDS
            textFieldFullName = new JTextField(15);
            textFieldFullName.setPreferredSize(textFieldDimension);

            textFieldSocial = new JTextField(15);
            textFieldSocial.setPreferredSize(textFieldDimension);
            //ADD BUTTONS TO PANEL
            panelButtons.add(submitButton);
            panelButtons.add(clearButton);

            //ADD TEXT FIELDS TO PANEL
            panelTextFields.add(textFieldFullName);
            panelTextFields.add(textFieldSocial);

            //ADD PANELS TO FRAME
            add(panelButtons, BorderLayout.SOUTH);
            add(panelTextFields, BorderLayout.EAST);

            setVisible(true);
        }
    }



    public static void main(String[] args)
    {
        new AccessAccount();
    }

}
