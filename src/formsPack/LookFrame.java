package formsPack;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Bartosz
 */
public class LookFrame extends JFrame
{
    public static void setLookAndFeel()
    {
        try 
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception exc) {}
    }
}
