import javax.swing.*;

public class Main
{
    private JFrame frame;

    private Main()
    {
        createFrame();
    }

    private void createFrame()
    {
        frame = new JFrame("Bank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 350);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
