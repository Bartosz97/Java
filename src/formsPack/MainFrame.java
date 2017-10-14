package formsPack;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import loginRegistrationPack.RegistrationFrame;
import skijumpingMechanicPack.ConnectWithDataBase;
import skijumpingMechanicPack.Hill;
import skijumpingMechanicPack.Jumper;
import skijumpingMechanicPack.User;

/**
 *
 * @author Bartosz
 */
public class MainFrame extends javax.swing.JFrame {

     public static ArrayList<Jumper> jumpersList;
    public static ArrayList<Hill> hillsList;
     private final ConnectWithDataBase cwdb;
    public MainFrame(User user) {
       cwdb = new ConnectWithDataBase();
       setResizable(false);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setTitle("SKI JUMPING - Menu Główne");
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
       LookFrame.setLookAndFeel();
       initComponents();
       pack();
       setLocationRelativeTo(null);
  
       jLabelActualUser.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 25));
       jLabelActualUser.setHorizontalAlignment(SwingConstants.RIGHT);        
       jLabelActualUser.setText("Zalogowany(a) jako: " + user.getFirstname() + " " + user.getLastname());
       
       if (user.getAccessLevel() == 0)            // accessLevel: 0 - dostęp ograniczony; 1 - dostęp z uprawnieniami (sędzia); 2 - dostęp całkowity (admin/organizator)
         {
            jButtonCompetition.setEnabled(false);
            jButtonAddEditRemoveHills.setEnabled(false);
            jButtonAddEditRemoveJumper.setEnabled(false);
            jButtonAddUser.setEnabled(false);
            jButtonRefresh.setEnabled(false);
            
            jMenuCompetition.setEnabled(false);
            jMenuAddUser.setEnabled(false);
            jMenuListHills.setEnabled(false);
            jMenuListJumper.setEnabled(false);
            jMenuRefresh.setEnabled(false);
            
         } 
         else if (user.getAccessLevel() == 1) 
         {
            jButtonAddUser.setEnabled(false);
            jMenuAddUser.setEnabled(false);
         }
        
        
        setVisible(true);
        
        //Dodawanie zawodników i skoczni do listy
        
        jumpersList = getJumpersList();
        hillsList = getHillsList();
     
        
    }
public ArrayList<Jumper> getJumpersList()                                    // WCZYTYWANIE LISTY SKOCZKOW Z BAZY (PUBLIC)
{    
    try
    {
    Class.forName(cwdb.getDriver());
    try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
    {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `skoczkowie`");
            ps.execute("USE skijumpingproject");

            ResultSet rs = ps.executeQuery();
            
            jumpersList = new ArrayList<Jumper>();
            while(rs.next())
            {
                Jumper jumper = new Jumper(rs.getString("first_name"),rs.getString("last_name"),rs.getString("country"));
                jumpersList.add(jumper);
            }
            connection.close();
            return jumpersList;
    }   
        } catch (ClassNotFoundException error1)
        { 
            JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error1.getMessage());
        } catch (SQLException error3)
        {
            JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error3.getMessage());
        }
    return jumpersList;
    } 
    
  public ArrayList<Hill> getHillsList()                                    // WCZYTYWANIE LISTY SKOCZKOW Z BAZY (PUBLIC)
    {
        try
        {
        Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `skocznie`");
            ps.execute("USE skijumpingproject");

            ResultSet rs = ps.executeQuery();
            
            hillsList = new ArrayList<Hill>();
            while(rs.next())
            {
                hillsList.add(new Hill(rs.getString("name"),rs.getString("country"),rs.getInt("point_k"),rs.getInt("point_hs"), rs.getDouble("point_distance"), rs.getDouble("point_wind")));
            }
            connection.close();
            return hillsList;
        }   
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            }
        return hillsList;
        } 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanelUser = new javax.swing.JPanel();
        jButtonStatistic = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jLabelActualUser = new javax.swing.JLabel();
        jButtonCompetition = new javax.swing.JButton();
        jButtonAddEditRemoveHills = new javax.swing.JButton();
        jButtonAddEditRemoveJumper = new javax.swing.JButton();
        jButtonAddUser = new javax.swing.JButton();
        jLabelImage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuProgram = new javax.swing.JMenu();
        jMenuShowStatistic = new javax.swing.JMenuItem();
        jMenuCompetition = new javax.swing.JMenuItem();
        jMenuRefresh = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuLogout = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuListHills = new javax.swing.JMenuItem();
        jMenuListJumper = new javax.swing.JMenuItem();
        jMenuAddUser = new javax.swing.JMenuItem();
        jMenuOption = new javax.swing.JMenu();
        jMenuAboutProgram = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuAboutAuthors = new javax.swing.JMenuItem();

        jMenuItem5.setText("jMenuItem5");

        jMenu4.setText("File");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("jMenu6");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonStatistic.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonStatistic.setText("Statystyki");
        jButtonStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatisticActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonStatistic, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 90, 191, 50));

        jButtonLogout.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonLogout.setText("Wyloguj");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 331, 191, 50));

        jButtonRefresh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonRefresh.setText("Odśwież dane");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 211, 191, 50));
        jPanelUser.add(jLabelActualUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 30, 671, 30));

        jButtonCompetition.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonCompetition.setText("Przeprowadź zawody");
        jButtonCompetition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompetitionActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonCompetition, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 90, 280, 50));

        jButtonAddEditRemoveHills.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonAddEditRemoveHills.setText("Lista skoczni");
        jButtonAddEditRemoveHills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEditRemoveHillsActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonAddEditRemoveHills, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 211, 280, 50));

        jButtonAddEditRemoveJumper.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonAddEditRemoveJumper.setText("Lista zawodników");
        jButtonAddEditRemoveJumper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEditRemoveJumperActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonAddEditRemoveJumper, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 331, 280, 50));

        jButtonAddUser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonAddUser.setText("Dodaj użytkownika");
        jButtonAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddUserActionPerformed(evt);
            }
        });
        jPanelUser.add(jButtonAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 441, 280, 50));

        jLabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Holmenkollbakken2.jpg"))); // NOI18N
        jPanelUser.add(jLabelImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 770, 630));

        jMenuProgram.setText("Program");

        jMenuShowStatistic.setText("Pokaż statystyki");
        jMenuShowStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuShowStatisticActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMenuShowStatistic);

        jMenuCompetition.setText("Przeprowadź zawody");
        jMenuCompetition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCompetitionActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMenuCompetition);

        jMenuRefresh.setText("Odśwież dane");
        jMenuRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRefreshActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMenuRefresh);
        jMenuProgram.add(jSeparator1);

        jMenuLogout.setText("Wyloguj");
        jMenuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLogoutActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMenuLogout);

        jMenuBar1.add(jMenuProgram);

        jMenuEdit.setText("Edycja");

        jMenuListHills.setText("Lista skoczni");
        jMenuListHills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuListHillsActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuListHills);

        jMenuListJumper.setText("Lista zawodników");
        jMenuListJumper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuListJumperActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuListJumper);

        jMenuAddUser.setText("Dodaj użytkownika");
        jMenuAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAddUserActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuAddUser);

        jMenuBar1.add(jMenuEdit);

        jMenuOption.setText("Opcje");

        jMenuAboutProgram.setText("O programie");
        jMenuAboutProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutProgramActionPerformed(evt);
            }
        });
        jMenuOption.add(jMenuAboutProgram);
        jMenuOption.add(jSeparator2);

        jMenuAboutAuthors.setText("Autorzy");
        jMenuAboutAuthors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutAuthorsActionPerformed(evt);
            }
        });
        jMenuOption.add(jMenuAboutAuthors);

        jMenuBar1.add(jMenuOption);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatisticActionPerformed
        // TODO add your handling code here:
        new StatisticFrame();
    }//GEN-LAST:event_jButtonStatisticActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        // TODO add your handling code here:
        new StartFrame();
            dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        // TODO add your handling code here:
        hillsList = getHillsList();
        jumpersList = getJumpersList();
        JOptionPane.showMessageDialog(null, "Lista skoczków i skoczni została zaaktualizowana.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddUserActionPerformed
        // TODO add your handling code here:
        new RegistrationFrame(true);
    }//GEN-LAST:event_jButtonAddUserActionPerformed

    private void jButtonAddEditRemoveJumperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEditRemoveJumperActionPerformed
        // TODO add your handling code here:
        new AddEditRemoveJumperUI();
    }//GEN-LAST:event_jButtonAddEditRemoveJumperActionPerformed

    private void jButtonAddEditRemoveHillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEditRemoveHillsActionPerformed
        // TODO add your handling code here:
        new AddEditRemoveHillUI();
    }//GEN-LAST:event_jButtonAddEditRemoveHillsActionPerformed

    private void jButtonCompetitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompetitionActionPerformed
        // TODO add your handling code here:
        new ChooseHillFrame();
    }//GEN-LAST:event_jButtonCompetitionActionPerformed

    private void jMenuAboutAuthorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutAuthorsActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Autorzy programu: \n\n• Bartosz Dudek\n• Michał Wysocki", "Autorzy programu", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuAboutAuthorsActionPerformed

    private void jMenuAboutProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutProgramActionPerformed
        // TODO add your handling code here:
        // String changelog = "Zmiany:\n- przeczyszczony kod w MainFrame (ustawienie elementów ciągle zostaje do zrobienia, bo dochodzą nowe)(MainFrame)\n- dorobiony menuBar na górze, nie ma jeszcze wszystkich opcji i obsługa jest póki co tylko zakłądki O programie(MainFrame)\n- dodanie informacji o zalogowanym użytkowniku (JLabel)(MainFrame)\n- rejestracja działa, ale coś robi się podwójnie i wyskakuje drugi raz wyjątek, muszę go zapytać o co biega chyba (RegistrationFrame)\nUPDATE - poprawiłem czytelność Labela w MainFrame\n - Rejestracja już działa dobrze, dodałem przycisk - 'powrót' w rejestracji\nUPDATE- pełna obsługa rejestracji (pozostaje kwestia tylko, że nie rozróżnia wielkości liter oraz nie obsługuje polskich znaków w javie(loginy nie mogą się powtarzać, sprawdzenie zgodności haseł, hasła muszą mieć przynajmniej 5 znaków)\n- wykończenie okienka rejestracji\nUPDATE Dodałem klasę Jumper oraz stworzyłem nowe okienko do dodania/edycji/usunięcia skoczka lecz nie wiem jak zrobić aby okienko było na srodku, nazwa okna itp. ponieważ użylem do tworzenia okienka JPanel form i cały kod tego okienka jest w klasie AddEditRemoveJumperUI\nUPDATE W linii 407 uzylem samego ps.excecute() nie wiem dlaczego nie trzeba nic dodawać i tak dobrze działa\nEdytowanie dziala bo edytuje po id i nie trzeba bawić sie w stare i nowe zmienne. Pole z Id jest niedostępne do edycji.\n Jak teraz zrobic aby w sumie to bylo w osobnej funkcji po w sumie tylko zapytanie sie zmienia i w edytowaniu dodaje sie ID?";
        String instruction = "• Statystyki - pokazują aktualną klasyfikację zawodników w danej klasyfikacji.\n\n"
        + "• Przeprowadź zawody - na początku wybieramy skocznię, na której chcemy przeprowadzić konkurs. Następnie wprowadzamy dystans, noty sędziów\n i wiatr dla poszczegołnych zawodników. Do konkursu wczytywani są wszyscy zawodnicy.\n\n"
        + "• Lista skoczni - pozwala dodać, edytować lub usunąć skocznię. Aby edytować lub usunąć skocznię należy wybrać odpowiednią z listy.\n PO AKTUALIZACJI LISTY SKOCZNI NALEŻY NACISNĄĆ ODŚWIEŻ DANE W MENU GŁÓWNYM.\n\n"
        + "• Lista zawodników - pozwala dodać, edytować lub usunąć zawodnika. Aby edytować lub usunąć zawodnika należy wybrać odpowiedniego z listy.\n PO AKTUALIZACJI LISTY ZAWODNIKÓW NALEŻY NACISNĄĆ ODŚWIEŻ DANE W MENU GŁÓWNYM.\n\n"
        + "• Odśwież dane - aktualizuje listy skoczków i skoczni, jeżeli były wprowadzane zmiany.\n\n"
        + "• Dodaj użytkownika - opcja dostępna tylko dla administratora. Pozwala dodać użytkownika z większymi uprawnieniami niż \"gość\".\n\n"
        + "• Wyloguj - wylogowanie się z konta.\n\n";
        JOptionPane.showMessageDialog(null, instruction, "Instrukcja programu", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuAboutProgramActionPerformed

    private void jMenuAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAddUserActionPerformed
        // TODO add your handling code here:
        new RegistrationFrame(true);
    }//GEN-LAST:event_jMenuAddUserActionPerformed

    private void jMenuListJumperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuListJumperActionPerformed
        // TODO add your handling code here:
        new AddEditRemoveJumperUI();
    }//GEN-LAST:event_jMenuListJumperActionPerformed

    private void jMenuListHillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuListHillsActionPerformed
        // TODO add your handling code here:
        new AddEditRemoveHillUI();
    }//GEN-LAST:event_jMenuListHillsActionPerformed

    private void jMenuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLogoutActionPerformed
        // TODO add your handling code here:
        new StartFrame();
        dispose();
    }//GEN-LAST:event_jMenuLogoutActionPerformed

    private void jMenuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRefreshActionPerformed
        // TODO add your handling code here:
        hillsList = getHillsList();
        jumpersList = getJumpersList();
        JOptionPane.showMessageDialog(null, "Lista skoczków i skoczni została zaaktualizowana", "Informacja", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuRefreshActionPerformed

    private void jMenuCompetitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCompetitionActionPerformed
        // TODO add your handling code here:
        new ChooseHillFrame();
    }//GEN-LAST:event_jMenuCompetitionActionPerformed

    private void jMenuShowStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuShowStatisticActionPerformed
        // TODO add your handling code here:
        new StatisticFrame();
    }//GEN-LAST:event_jMenuShowStatisticActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              ///  new MainFrame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddEditRemoveHills;
    private javax.swing.JButton jButtonAddEditRemoveJumper;
    private javax.swing.JButton jButtonAddUser;
    private javax.swing.JButton jButtonCompetition;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonStatistic;
    private javax.swing.JLabel jLabelActualUser;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuAboutAuthors;
    private javax.swing.JMenuItem jMenuAboutProgram;
    private javax.swing.JMenuItem jMenuAddUser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuCompetition;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuListHills;
    private javax.swing.JMenuItem jMenuListJumper;
    private javax.swing.JMenuItem jMenuLogout;
    private javax.swing.JMenu jMenuOption;
    private javax.swing.JMenu jMenuProgram;
    private javax.swing.JMenuItem jMenuRefresh;
    private javax.swing.JMenuItem jMenuShowStatistic;
    private javax.swing.JPanel jPanelUser;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
