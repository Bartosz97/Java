package formsPack;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import skijumpingMechanicPack.ConnectWithDataBase;
import skijumpingMechanicPack.Jumper;

/**
 *
 * @author Bartosz
 */
public class AddEditRemoveJumperUI extends javax.swing.JFrame {

    
    private final ConnectWithDataBase cwdb;
    private int counter = 1;
    
    public AddEditRemoveJumperUI() {
        this.cwdb = new ConnectWithDataBase();
      
        setResizable(false);
        setTitle("SKI JUMPING - Lista zawodników");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        showJumpersInJTable();
    }

    public ArrayList<Jumper> getJumperList()                                    // WCZYTYWANIE LISTY SKOCZKOW Z BAZY (PUBLIC)
    {    
        ArrayList<Jumper> jumperList = new ArrayList<>();
        try
        {
        Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {
                String query = "SELECT * FROM `skoczkowie` ";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Jumper jumper = new Jumper(rs.getString("first_name"),rs.getString("last_name"),rs.getString("country"));
                    jumperList.add(jumper);
                }
                connection.close();
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
        return jumperList;
        } 
    
    private void showJumpersInJTable()                                           // WYŚWIETLANIE W TABELI SKOCZKOW (PRIVATE)
    {
        ArrayList<Jumper> list = getJumperList();
        DefaultTableModel model = (DefaultTableModel) jTableJumpers.getModel();
        Object[] row = new Object[4];
        counter = 1;
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = counter;
            row[1] = list.get(i).getFirstName();
            row[2] = list.get(i).getLastName();
            row[3] = list.get(i).getCountry();
            
            model.addRow(row);
            counter++;
        }
    }
    
    private boolean isJumperInDatabase(String firstName, String lastName, String country)        // SPRAWDZAM CZY PODANY ZAWODNK JUŻ ISTNIEJE (imię, nazwisko, kraj) (PRIVATE)
    {
        try
            {
                Class.forName(cwdb.getDriver());
                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    PreparedStatement psCheckJumper = connection.prepareStatement("SELECT first_name, last_name, country FROM skoczkowie WHERE first_name = ? AND last_name = ? AND country = ?");
                    psCheckJumper.execute("USE skijumpingproject");
                    psCheckJumper.setString(1, firstName);
                    psCheckJumper.setString(2, lastName);
                    psCheckJumper.setString(3, country);
                    ResultSet resultCheckJumper = psCheckJumper.executeQuery();
                    return resultCheckJumper.next(); // zwraca true, kiedy skoczek o takich danych znajduje się już w bazie danych
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
       return false;
    }
    
    private void clearJTextFields()                                     // CZSZCZĘ POLA TEKSTOWE (PRIVATE)
    {
        jTextFieldNumber.setText("");
        jTextFieldFirstName.setText("");
        jTextFieldLastName.setText("");
        jTextFieldCountry.setText("");
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
        jLabelFirstName = new javax.swing.JLabel();
        jLabelLastName = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldCountry = new javax.swing.JTextField();
        jTextFieldNumber = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableJumpers = new javax.swing.JTable();
        jButtonEditJumper = new javax.swing.JButton();
        jButtonRemoveJumper = new javax.swing.JButton();
        jButtonAddJumper = new javax.swing.JButton();
        jButtonClearTextFields = new javax.swing.JButton();
        jLabelImgae = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelFirstName.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelFirstName.setText("Imię:");
        jPanel1.add(jLabelFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 109, -1, 48));

        jLabelLastName.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelLastName.setText("Nazwisko:");
        jPanel1.add(jLabelLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 164, -1, 48));

        jLabelCountry.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelCountry.setText("Kraj:");
        jPanel1.add(jLabelCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 219, -1, 48));

        jLabelNumber.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelNumber.setText("Numer:");
        jPanel1.add(jLabelNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 54, -1, 48));

        jTextFieldFirstName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFirstNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 121, 154, -1));

        jTextFieldLastName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLastNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 176, 154, -1));

        jTextFieldCountry.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCountryActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 231, 154, -1));

        jTextFieldNumber.setEditable(false);
        jTextFieldNumber.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNumber.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldNumber.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextFieldNumber.setEnabled(false);
        jTextFieldNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumberActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 66, 154, -1));

        jTableJumpers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer", "Imię", "Nazwisko", "Kraj"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableJumpers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableJumpersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableJumpers);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 13, -1, 580));

        jButtonEditJumper.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonEditJumper.setText("Edytuj");
        jButtonEditJumper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditJumperActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonEditJumper, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 347, 100, 43));

        jButtonRemoveJumper.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonRemoveJumper.setText("Usuń");
        jButtonRemoveJumper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveJumperActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRemoveJumper, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 347, 100, 43));

        jButtonAddJumper.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonAddJumper.setText("Dodaj");
        jButtonAddJumper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddJumperActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAddJumper, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 347, 100, 43));

        jButtonClearTextFields.setText("Wyczyść dane");
        jButtonClearTextFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearTextFieldsActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonClearTextFields, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 274, 154, -1));

        jLabelImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/snow.jpg"))); // NOI18N
        jPanel1.add(jLabelImgae, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 870, 620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:        
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastNameActionPerformed

    private void jTextFieldCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCountryActionPerformed

    private void jTextFieldNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumberActionPerformed

    private void jButtonEditJumperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditJumperActionPerformed

      try
            {               
                String oldName = jTextFieldFirstName.getText();
                String oldLastname = jTextFieldLastName.getText();
                String oldCountry = jTextFieldCountry.getText();               
                
                if(oldName.equals("") || oldLastname.equals("") || oldCountry.equals(""))
                {
                    throw new IllegalAccessException();
                }
                
                String name = JOptionPane.showInputDialog(null, "Wprowadź nowe imię lub zostaw puste, aby zachować aktualne (" + oldName + "): ", "Edycja zawodnika", JOptionPane.INFORMATION_MESSAGE);
                String lastname = JOptionPane.showInputDialog(null, "Wprowadź nowe nazwisko lub zostaw puste, aby zachować aktualne (" + oldLastname + "):", "Edycja zawodnika", JOptionPane.INFORMATION_MESSAGE);
                String country = JOptionPane.showInputDialog(null, "Wprowadź nowy kraj lub zostaw puste, aby zachować aktualny (" + oldCountry + "): ", "Edycja zawodnika", JOptionPane.INFORMATION_MESSAGE);
                
                
                if(name == null || name.equals("")) name = oldName;
                if(lastname == null || lastname.equals("")) lastname = oldLastname;
                if(country == null || country.equals("")) country = oldCountry;
                
                Class.forName(cwdb.getDriver());
                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    if (isJumperInDatabase(name, lastname, country))
                    {
                        JOptionPane.showMessageDialog(null, "W bazie danych istnieje już taki skoczek.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        PreparedStatement ps = connection.prepareStatement("UPDATE skoczkowie SET first_name = ?, last_name = ?, country = ? WHERE first_name = ? AND last_name = ? AND country = ?");
                        ps.execute("USE skijumpingproject");
                        ps.setString(1, name);
                        ps.setString(2, lastname);
                        ps.setString(3, country);
                        ps.setString(4, oldName);
                        ps.setString(5, oldLastname);
                        ps.setString(6, oldCountry);


                        ps.executeUpdate(); 
                        connection.close();

                        clearJTextFields();
                        JOptionPane.showMessageDialog(null, "Edycja skoczka zakończona sukcesem.");
                        
                        DefaultTableModel model = (DefaultTableModel)jTableJumpers.getModel();
                        model.setRowCount(0);
                        showJumpersInJTable();
                    }    
                }
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (IllegalAccessException error2)
            {
                JOptionPane.showMessageDialog(null, "Wybierz zawodnika do edycji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error2.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            } 
       
    
    }//GEN-LAST:event_jButtonEditJumperActionPerformed

    private void jTableJumpersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableJumpersMouseClicked
        int i = jTableJumpers.getSelectedRow();
        TableModel model = jTableJumpers.getModel();
        jTextFieldNumber.setText(model.getValueAt(i, 0).toString());
        jTextFieldFirstName.setText(model.getValueAt(i, 1).toString());
        jTextFieldLastName.setText(model.getValueAt(i, 2).toString());
        jTextFieldCountry.setText(model.getValueAt(i, 3).toString());
    }//GEN-LAST:event_jTableJumpersMouseClicked

    private void jButtonAddJumperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddJumperActionPerformed

      try
            {               
                String name = jTextFieldFirstName.getText();
                String lastName = jTextFieldLastName.getText();
                String country = jTextFieldCountry.getText();
              
                if (name.equals("") || lastName.equals("") || country.equals(""))
                {
                    throw new IllegalAccessException();
                }
                
                Class.forName(cwdb.getDriver());
                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    if (isJumperInDatabase(name, lastName, country))
                    {
                        JOptionPane.showMessageDialog(null, "W bazie danych istnieje już taki skoczek.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO skoczkowie (first_name, last_name, country) VALUES (?,?,?)");
                        ps.execute("USE skijumpingproject");
                        ps.setString(1, name);
                        ps.setString(2, lastName);
                        ps.setString(3, country);
                        ps.executeUpdate(); 
                        
                        PreparedStatement ps1 = connection.prepareStatement("SELECT id_skoczka FROM skoczkowie WHERE first_name=? AND last_name=? AND country=?");
                        ps1.execute("USE skijumpingproject");
                        ps1.setString(1, name);
                        ps1.setString(2, lastName);
                        ps1.setString(3, country);

                        ResultSet rs = ps1.executeQuery();
                        if (rs.next())
                        {
                            int idSkoczka = rs.getInt("id_skoczka");
                            
                            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO world_cup_17_18 (place, id_skoczka, points) VALUES (?,?,0)");
                            ps2.execute("USE skijumpingproject");
                            ps2.setInt(1, 999);
                            ps2.setInt(2, idSkoczka);
                            ps2.executeUpdate(); 
                            
                            PreparedStatement ps3 = connection.prepareStatement("INSERT INTO four_hills_tournament (place, id_skoczka, points) VALUES (?,?,0)");
                            ps3.execute("USE skijumpingproject");
                            ps3.setInt(1, 999);
                            ps3.setInt(2, idSkoczka);
                            ps3.executeUpdate(); 
                            
                        }
                        connection.close();

                        DefaultTableModel model = (DefaultTableModel)jTableJumpers.getModel();
                        model.setRowCount(0);
                        showJumpersInJTable();
                        clearJTextFields();
                        JOptionPane.showMessageDialog(null, "Dodawanie skoczka zakończone sukcesem.");
                    }
                        
                }
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (IllegalAccessException error2)
            {
                JOptionPane.showMessageDialog(null, "Dodawanie zawodnika nie powiodło się. Proszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error2.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            }

    }//GEN-LAST:event_jButtonAddJumperActionPerformed
  
    
    private void jButtonRemoveJumperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveJumperActionPerformed

        try
            {               
                String name = jTextFieldFirstName.getText();
                String lastname = jTextFieldLastName.getText();
                String country = jTextFieldCountry.getText();
                
                if (name.equals("") || lastname.equals("") || country.equals(""))
                {
                    throw new IllegalAccessException();
                }
                
                String jumperData = "\"" + name + " " + lastname + " - " + country + "\"";
                if(JOptionPane.showConfirmDialog (null, "Czy na pewno usunąć wybranego zawodnika?\n" + jumperData,"Potwierdzenie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
                {
                    Class.forName(cwdb.getDriver());
                    try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                    {
                        PreparedStatement ps0 = connection.prepareStatement("SELECT id_skoczka FROM skoczkowie WHERE first_name = ? AND last_name = ? AND country = ?; ");
                        PreparedStatement ps1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0; ");
                        
                        PreparedStatement ps2 = connection.prepareStatement("DELETE FROM skoczkowie WHERE id_skoczka = ?; ");
                        PreparedStatement ps3 = connection.prepareStatement("DELETE FROM world_cup_17_18 WHERE id_skoczka = ?; ");
                        PreparedStatement ps4 = connection.prepareStatement("DELETE FROM four_hills_tournament WHERE id_skoczka = ?; ");
                        
                        PreparedStatement ps5 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1; ");
                        
                        ps0.execute("USE skijumpingproject");
                        ps0.setString(1, name);
                        ps0.setString(2, lastname);
                        ps0.setString(3, country);
                        ResultSet resultPs0 = ps0.executeQuery();
                        int id = -1;
                        if(resultPs0.next()) id = resultPs0.getInt("id_skoczka");
                        
                        ps1.execute("USE skijumpingproject");
                        ps1.executeQuery();
                        
                        ps2.execute("USE skijumpingproject");
                        ps2.setInt(1, id);
                        ps2.executeUpdate();
                        
                        ps3.execute("USE skijumpingproject");
                        ps3.setInt(1, id);
                        ps3.executeUpdate();
                        
                        ps4.execute("USE skijumpingproject");
                        ps4.setInt(1, id);
                        ps4.executeUpdate();
                        
                        ps5.execute("USE skijumpingproject");
                        ps5.executeQuery();
                        
                        connection.close();

                        DefaultTableModel model = (DefaultTableModel)jTableJumpers.getModel();
                        model.setRowCount(0);
                        showJumpersInJTable();
                        clearJTextFields();
                        JOptionPane.showMessageDialog(null, "Usuwanie skoczka zakończone sukcesem.");    
                    }
                }
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (IllegalAccessException error2)
            {
                JOptionPane.showMessageDialog(null, "Usuwanie zawodnika nie powiodło się. Proszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error2.getMessage());
            } catch (SQLException error3)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error3.getMessage());
            }
    }//GEN-LAST:event_jButtonRemoveJumperActionPerformed

    private void jButtonClearTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearTextFieldsActionPerformed
        clearJTextFields();
    }//GEN-LAST:event_jButtonClearTextFieldsActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEditRemoveJumperUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(AddEditRemoveJumperUI::new);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddJumper;
    private javax.swing.JButton jButtonClearTextFields;
    private javax.swing.JButton jButtonEditJumper;
    private javax.swing.JButton jButtonRemoveJumper;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelFirstName;
    private javax.swing.JLabel jLabelImgae;
    private javax.swing.JLabel jLabelLastName;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableJumpers;
    private javax.swing.JTextField jTextFieldCountry;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldNumber;
    // End of variables declaration//GEN-END:variables
}
