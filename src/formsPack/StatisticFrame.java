package formsPack;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import skijumpingMechanicPack.ConnectWithDataBase;
import skijumpingMechanicPack.FourHillsTournamentClassification;
import skijumpingMechanicPack.Jumper;
import skijumpingMechanicPack.WorldCupClassification;

/**
 *
 * @author Bartosz
 */
public class StatisticFrame extends javax.swing.JFrame {

    private final ConnectWithDataBase cwdb;
    private ArrayList<WorldCupClassification> worldCupList = new ArrayList<>();
    private ArrayList<FourHillsTournamentClassification> fourHillsList = new ArrayList<>();
      
    public StatisticFrame() {
        this.cwdb = new ConnectWithDataBase();
        
        setResizable(false);
        setTitle("SKI JUMPING - Statystyki");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        ((JLabel)jComboBox1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);      
        pack();
        setLocationRelativeTo(null);
        worldCupList = getWorldCupList();
        fourHillsList = getFourHillsList();
        showStatisticWorldCupInJTable();
        setVisible(true);
      
    }
    private ArrayList<WorldCupClassification> getWorldCupList()
    {
          try
        {         
            Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {

            PreparedStatement psGetWCList = connection.prepareStatement("SELECT wc.points, s.first_name, s.last_name, s.country FROM world_cup_17_18 wc INNER JOIN skoczkowie s ON wc.id_skoczka = s.id_skoczka ORDER BY wc.points DESC");
            psGetWCList.execute("USE skijumpingproject");
 
            ResultSet result = psGetWCList.executeQuery();
            int place = 1;
            while (result.next())
            {
             
               worldCupList.add(new WorldCupClassification(place, new Jumper(result.getString("first_name"), result.getString("last_name"), result.getString("country")), result.getInt("points")));
               place++;
            }        
            return worldCupList;
        }   
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
               // System.out.println(error3.getMessage());
            }
          return worldCupList;
          
    }

   private ArrayList<FourHillsTournamentClassification> getFourHillsList()
    {
          try
        {         
            Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {

            PreparedStatement psGet4HList = connection.prepareStatement("SELECT fth.points, s.first_name, s.last_name, s.country FROM four_hills_tournament fth INNER JOIN skoczkowie s ON fth.id_skoczka = s.id_skoczka ORDER BY fth.points DESC");
            psGet4HList.execute("USE skijumpingproject");
 
            ResultSet result = psGet4HList.executeQuery();
            
            int place = 1;
            while (result.next())
            {
                fourHillsList.add(new FourHillsTournamentClassification((place), new Jumper(result.getString("first_name"), result.getString("last_name"), result.getString("country")), result.getDouble("points")));
                place++;
            }        
            return fourHillsList;
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
          return fourHillsList;
          
    } 
    
     private void showStatisticWorldCupInJTable()                                           // WYŚWIETLANIE W TABELI STATYSTYKI (PRIVATE)
    {
        DefaultTableModel model = (DefaultTableModel) jStatisticTable.getModel();
        Object[] row = new Object[5];
        for(int i = 0; i < worldCupList.size(); i++)
        {
            row[0] = worldCupList.get(i).getPlaceInWC();
            row[1] = worldCupList.get(i).getJumper().getFirstName();
            row[2] = worldCupList.get(i).getJumper().getLastName();
            row[3] = worldCupList.get(i).getJumper().getCountry();
            row[4] = worldCupList.get(i).getPointsInWorldCup();
            
            model.addRow(row);
        }
    }
     private void showStatistic4HTInJTable()                                           // WYŚWIETLANIE W TABELI STATYSTYKI (PRIVATE)
    {
        DefaultTableModel model = (DefaultTableModel) jStatisticTable.getModel();
        Object[] row = new Object[5];
        for(int i = 0; i < fourHillsList.size(); i++)
        {
            row[0] = fourHillsList.get(i).getPlaceIn4HT();
            row[1] = fourHillsList.get(i).getJumper().getFirstName();
            row[2] = fourHillsList.get(i).getJumper().getLastName();
            row[3] = fourHillsList.get(i).getJumper().getCountry();
            row[4] = fourHillsList.get(i).getPointsIn4HT();
            
            model.addRow(row);
        }
    }

     
     
     
     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelStatistic = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jStatisticTable = new javax.swing.JTable();
        jLabelImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelStatistic.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabelStatistic.setText("Statystyki");
        jPanel1.add(jLabelStatistic, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 13, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Puchar Świata", "Turniej Czterech Skoczni" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 65, 337, -1));

        jStatisticTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Miejsce", "Imię", "Nazwisko", "Kraj", "Punkty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jStatisticTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 118, 597, 578));

        jLabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/snow.jpg"))); // NOI18N
        jPanel1.add(jLabelImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 710));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        if(jComboBox1.getSelectedIndex() == 0)
        {
            DefaultTableModel model = (DefaultTableModel) jStatisticTable.getModel();
            model.setRowCount(0);
            showStatisticWorldCupInJTable();
            
        } else if (jComboBox1.getSelectedIndex() == 1)
        {
            DefaultTableModel model = (DefaultTableModel) jStatisticTable.getModel();
            model.setRowCount(0);
            showStatistic4HTInJTable();
        }        
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(StatisticFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StatisticFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelStatistic;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jStatisticTable;
    // End of variables declaration//GEN-END:variables
}
