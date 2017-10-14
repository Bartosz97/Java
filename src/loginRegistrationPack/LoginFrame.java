package loginRegistrationPack;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import skijumpingMechanicPack.ConnectWithDataBase;
import formsPack.LookFrame;
import formsPack.MainFrame;
import java.awt.Toolkit;
import skijumpingMechanicPack.User;

/**
 *
 * @author Bartosz
 */
public class LoginFrame extends LookFrame implements ActionListener, KeyListener
{
    private JPanel jPanelFrame;
    private JLabel jLabelLogin, jLabelPassword;
    private JTextField jTextFieldLogin;
    private JPasswordField jPasswordFieldPassword;
    private JButton jButtonLogin;
    private GridBagConstraints gbc;
    
    public LoginFrame()
    {
        setLookAndFeel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("SKI JUMPING - Logowanie");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //JPanel
        jPanelFrame = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        jPanelFrame.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        //JLabel
        jLabelLogin = new JLabel("Login:");
        jLabelLogin.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelPassword = new JLabel("Hasło:");
        jLabelPassword.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        
        //JTextField and JPasswordField
        jTextFieldLogin = new JTextField();
        jPasswordFieldPassword = new JPasswordField();
                
        //JButton
        jButtonLogin = new JButton("Zaloguj");
        jButtonLogin.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        
        //add to panel
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanelFrame.add(jLabelLogin, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        jPanelFrame.add(jLabelPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanelFrame.add(jTextFieldLogin, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        jPanelFrame.add(jPasswordFieldPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        jPanelFrame.add(jButtonLogin, gbc);
        
        add(jPanelFrame);
        
        //add ActionListener, KeyListener
        jButtonLogin.addActionListener(this);
        jButtonLogin.addKeyListener(this);
        jPasswordFieldPassword.addKeyListener(this);
        
        jTextFieldLogin.grabFocus();
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object action = e.getSource();
        
        if (action == jButtonLogin)
        {
            login();
            jTextFieldLogin.grabFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (KeyEvent.VK_ENTER == e.getKeyCode())
        {
            login();
            jTextFieldLogin.grabFocus();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void login()
    {
            try
            {               
                String login = jTextFieldLogin.getText();
                String password = String.valueOf(jPasswordFieldPassword.getPassword());
                
                jTextFieldLogin.setText("");
                jPasswordFieldPassword.setText("");

                if (login.equals("") || password.equals(""))
                {
                    throw new IllegalAccessException();
                }
              
                ConnectWithDataBase cwdb = new ConnectWithDataBase();
                Class.forName(cwdb.getDriver());

                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    PreparedStatement ps = connection.prepareStatement("SELECT access_level, first_name, last_name FROM users WHERE login=? AND password=?");
                    ps.execute("USE skijumpingproject");
                    ps.setString(1, login);
                    ps.setString(2, password);
                    
                    ResultSet result = ps.executeQuery();
 
                    if (result.next())
                    {                            
                        new MainFrame(new User(result.getString("first_name"), result.getString("last_name"), result.getInt("access_level")));
                        connection.close();
                        dispose();
                    }
                    else 
                    {
                        connection.close();
                        throw new IllegalAccessException();
                    }
                        
                }
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(jPanelFrame, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (IllegalAccessException error2)
            {
                JOptionPane.showMessageDialog(jPanelFrame, "Logowanie nie powiodło się. Proszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error2.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(jPanelFrame, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            }

    }
}