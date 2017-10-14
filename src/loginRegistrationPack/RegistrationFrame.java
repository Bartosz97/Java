package loginRegistrationPack;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import skijumpingMechanicPack.ConnectWithDataBase;
import formsPack.LookFrame;
import static formsPack.LookFrame.setLookAndFeel;
import java.awt.Toolkit;

/**
 *
 * @author Bartosz
 */
public class RegistrationFrame extends LookFrame implements ActionListener
{
    private JPanel jPanelFrame, jPanelRadioButtons;
    private JLabel jLabelLogin, jLabelPassword, jLabelRepeatPassword,  jLabelFirstName, jLabelLastName, jLabelAccessLevel;
    private JTextField jTextFieldLogin, jTextFieldFirstName, jTextFieldLastName;
    private JPasswordField jPasswordFieldPassword, jPasswordFieldRepeatPassword;
    private JButton jButtonRegistration, jButtonReturn;
    private JRadioButton jRadioButtonAccessLevelGuesst, jRadioButtonAccessLevelArbiter, jRadioButtonAccessLevelAdmin;
    private ButtonGroup buttonGroup;

    public RegistrationFrame(boolean isAdmin)
    {
        setLookAndFeel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("SKI JUMPING - Rejestracja");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        setSize(800, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        jPanelFrame = new JPanel(new GridLayout(7,2,10,10));
        jPanelFrame.setBorder(new EmptyBorder(20, 20, 20, 20));
        jPanelRadioButtons = new JPanel(new GridLayout(1,3,0,5));

        jLabelLogin = new JLabel("Login:");
        jLabelLogin.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelLogin.setHorizontalAlignment(JLabel.CENTER);
        
        jLabelPassword = new JLabel("Hasło:");
        jLabelPassword.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelPassword.setHorizontalAlignment(JLabel.CENTER);
        
        jLabelRepeatPassword = new JLabel("Powtórz hasło:");
        jLabelRepeatPassword.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelRepeatPassword.setHorizontalAlignment(JLabel.CENTER);
        
        jLabelFirstName = new JLabel("Imię:");
        jLabelFirstName.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelFirstName.setHorizontalAlignment(JLabel.CENTER);
        
        jLabelLastName = new JLabel("Nazwisko:");
        jLabelLastName.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelLastName.setHorizontalAlignment(JLabel.CENTER);
        
        jLabelAccessLevel = new JLabel("Poziom dostępu:");
        jLabelAccessLevel.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelAccessLevel.setHorizontalAlignment(JLabel.CENTER);

        jTextFieldLogin = new JTextField();
        jTextFieldFirstName = new JTextField();        
        jTextFieldLastName = new JTextField();
        jPasswordFieldPassword = new JPasswordField();
        jPasswordFieldRepeatPassword = new JPasswordField(2);

        //RadioButtons and ButtonGroup(accessLevel)
        jRadioButtonAccessLevelGuesst = new JRadioButton("Gość");
        jRadioButtonAccessLevelGuesst.setSelected(true);
        jRadioButtonAccessLevelArbiter = new JRadioButton("Sędzia");
        jRadioButtonAccessLevelAdmin = new JRadioButton("Administrator");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButtonAccessLevelGuesst);
        buttonGroup.add(jRadioButtonAccessLevelArbiter);
        buttonGroup.add(jRadioButtonAccessLevelAdmin);

        jButtonRegistration = new JButton("Rejestruj");
        jButtonRegistration.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        
        jButtonReturn = new JButton("Powrót");
        jButtonReturn.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));

        if (!isAdmin) {
            jRadioButtonAccessLevelAdmin.setEnabled(false);
            jRadioButtonAccessLevelArbiter.setEnabled(false);
        }
        
        jPanelRadioButtons.add(jRadioButtonAccessLevelGuesst);
        jPanelRadioButtons.add(jRadioButtonAccessLevelArbiter);
        jPanelRadioButtons.add(jRadioButtonAccessLevelAdmin);
        
        jPanelFrame.add(jLabelFirstName);
        jPanelFrame.add(jTextFieldFirstName);
        jPanelFrame.add(jLabelLastName);
        jPanelFrame.add(jTextFieldLastName);
        jPanelFrame.add(jLabelLogin);
        jPanelFrame.add(jTextFieldLogin);
        jPanelFrame.add(jLabelPassword);
        jPanelFrame.add(jPasswordFieldPassword);
        jPanelFrame.add(jLabelRepeatPassword);
        jPanelFrame.add(jPasswordFieldRepeatPassword);
        jPanelFrame.add(jLabelAccessLevel);
        jPanelFrame.add(jPanelRadioButtons);        
        jPanelFrame.add(jButtonReturn);
        jPanelFrame.add(jButtonRegistration);

        add(jPanelFrame);

        jButtonRegistration.addActionListener(this);
        jButtonReturn.addActionListener(this);
        
        jTextFieldFirstName.grabFocus();

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();

        if (action == jButtonReturn)
        {
            dispose();
        }
        else if (action == jButtonRegistration)
        {
            try
            {
                String firstname = jTextFieldFirstName.getText();
                String lastname = jTextFieldLastName.getText();
                String login = jTextFieldLogin.getText();
                String password = String.valueOf(jPasswordFieldPassword.getPassword());
                String repeatPassword = String.valueOf(jPasswordFieldRepeatPassword.getPassword());
                int accessLevel = 0;
                if (jRadioButtonAccessLevelArbiter.isSelected()) accessLevel = 1;
                if (jRadioButtonAccessLevelAdmin.isSelected()) accessLevel = 2;

                jTextFieldLogin.setText("");
                jPasswordFieldPassword.setText("");
                jPasswordFieldRepeatPassword.setText("");
                
                if (login.equals("") || password.equals("") || firstname.equals("") || lastname.equals(""))
                {
                    JOptionPane.showMessageDialog(jPanelFrame, "Wszystkie pola muszą zostać wypełnione.\nProszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    throw new IllegalAccessException();
                } else if (password.length() < 5 || repeatPassword.length() < 5)
                {
                    JOptionPane.showMessageDialog(jPanelFrame, "Hasła powinny zawierać przynajmniej 5 znaków.\nProszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);   
                    throw new IllegalAccessException();
                } else if (!password.equals(repeatPassword))
                {
                    JOptionPane.showMessageDialog(jPanelFrame, "Niezgodne hasła.\nProszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);   
                    throw new IllegalAccessException();
                } else
                {                
                    ConnectWithDataBase cwdb = new ConnectWithDataBase();
                    Class.forName(cwdb.getDriver());
                    try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                    {
                        PreparedStatement psCheckLogin = connection.prepareStatement("SELECT login FROM users WHERE login = ?");
                        psCheckLogin.execute("USE skijumpingproject");
                        psCheckLogin.setString(1, login);
                        ResultSet resultCheckLogin = psCheckLogin.executeQuery();
                        if (resultCheckLogin.next())
                        {
                            JOptionPane.showMessageDialog(null, "Podany login jest już zajęty.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        } else
                        {
                            PreparedStatement psInsertUser = connection.prepareStatement("INSERT INTO users (login, password, first_name, last_name, access_level) VALUES (?,?,?,?,?)");
                            psInsertUser.execute("USE skijumpingproject");
                            psInsertUser.setString(1, login);
                            psInsertUser.setString(2, password);
                            psInsertUser.setString(3, firstname);
                            psInsertUser.setString(4, lastname);
                            psInsertUser.setString(5, String.valueOf(accessLevel));                    
                            psInsertUser.executeUpdate();
                            connection.close();
                            JOptionPane.showMessageDialog(null, "Rejestracja zakończona pomyślnie. Możesz teraz zalogować się na swoje konto.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    } 
              catch (SQLException error1)
            {
                JOptionPane.showMessageDialog(jPanelFrame, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                //System.out.println(error1.getMessage());
            }
                }
            } catch (ClassNotFoundException error2)
            {
                JOptionPane.showMessageDialog(jPanelFrame, "Błąd programu.", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                //System.out.println(error2.getMessage());
            } catch (IllegalAccessException error3)
            {
                JOptionPane.showMessageDialog(jPanelFrame, "Rejestracja nie powiodła się. Niezgodne hasła lub nie wszystkie pola formularza zostały wypełnione.\nProszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            }
            jTextFieldLogin.grabFocus();
        }
    }

}