package formsPack;

import loginRegistrationPack.RegistrationFrame;
import loginRegistrationPack.LoginFrame;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Bartosz
 */
public class StartFrame extends JFrame implements ActionListener
{
    private JPanel jPanelFrame;
    private JButton jButtonRegistration, jButtonLogin;
    
    public StartFrame() 
    {
        LookFrame.setLookAndFeel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SKI JUMPING");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        setSize(400, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        
        jPanelFrame = new JPanel(new GridLayout(1, 2, 20, 10));
        jPanelFrame.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        jButtonLogin = new JButton("Logowanie");      
        jButtonLogin.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jButtonLogin.addActionListener(this);
        
        jButtonRegistration = new JButton("Rejestracja");
        jButtonRegistration.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jButtonRegistration.addActionListener(this);
        
        jPanelFrame.add(jButtonLogin);
        jPanelFrame.add(jButtonRegistration);
        
        add(jPanelFrame);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object evt = e.getSource();
        
        if (jButtonRegistration == evt)
        {
            new RegistrationFrame(false);
        }
        else if ( jButtonLogin == evt)
        {
            new LoginFrame();
            dispose();
        }
    }
    
}
