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
import skijumpingMechanicPack.Hill;

/**
 *
 * @author Bartosz
 */
public class AddEditRemoveHillUI extends javax.swing.JFrame {

    private final ConnectWithDataBase cwdb;
    private int counter = 1;
    
    public AddEditRemoveHillUI() {
        this.cwdb = new ConnectWithDataBase();
      
        setResizable(false);
        setTitle("SKI JUMPING - Lista skoczni");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        initComponents();
        pack();
        setVisible(true);
        setLocationRelativeTo(null);     
        showHillsInJTable();
    }

    public ArrayList<Hill> getHillsList()                                    // WCZYTYWANIE LISTY SKOCZKOW Z BAZY (PUBLIC)
    {    
        ArrayList<Hill> hillsList = new ArrayList<>();
        try
        {
        Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {
                String query = "SELECT * FROM `skocznie` ";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Hill hills = new Hill(rs.getString("name"),rs.getString("country"),rs.getInt("point_k"),rs.getInt("point_hs"), rs.getDouble("point_distance"), rs.getDouble("point_wind"));
                    hillsList.add(hills);
 
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
        return hillsList;
        } 
    
    private void showHillsInJTable()                                           // WYŚWIETLANIE W TABELI SKOCZNI (PRIVATE)
    {
        ArrayList<Hill> list = getHillsList();
        DefaultTableModel model = (DefaultTableModel) jTableHills.getModel();
        Object[] row = new Object[7];
        counter = 1;
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = counter;
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCountry();
            row[3] = list.get(i).getPointK();
            row[4] = list.get(i).getPointHS();
            row[5] = list.get(i).getPointDistance();
            row[6] = list.get(i).getPointWind();
            
            model.addRow(row);
            counter++;
        }
    }
    
    private boolean isHillsInDatabase(String name, String country, int pointKInt, int pointHSInt, double pointDistanceDouble, double pointWindDouble)        // SPRAWDZAM CZY PODANA SKOCZNIA JUŻ ISTNIEJE (imię, kraj, punkt k, punkt hs) (PRIVATE)
    {
        try
            {
                Class.forName(cwdb.getDriver());
                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    PreparedStatement psCheckJumper = connection.prepareStatement("SELECT name, country, point_k, point_hs, point_distance, point_wind FROM skocznie WHERE name = ? AND country = ? AND point_k = ? AND point_hs = ? AND point_distance = ? AND point_wind = ?");
                    psCheckJumper.execute("USE skijumpingproject");
                    psCheckJumper.setString(1, name);
                    psCheckJumper.setString(2, country);
                    psCheckJumper.setInt(3, pointKInt);
                    psCheckJumper.setInt(4, pointHSInt);
                    psCheckJumper.setDouble(5, pointDistanceDouble);
                    psCheckJumper.setDouble(6, pointWindDouble);
                    ResultSet resultCheckJumper = psCheckJumper.executeQuery();
                    return resultCheckJumper.next(); // zwraca true, kiedy skocznia o takich danych znajduje się już w bazie danych
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
        jTextFieldName.setText("");
        jTextFieldCountry.setText("");
        jTextFieldPointK.setText("");
        jTextFieldPointHS.setText("");
        jTextFieldPointDistance.setText("");
        jTextFieldPointWind.setText("");
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
        jLabelName = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jLabelPointK = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldCountry = new javax.swing.JTextField();
        jTextFieldPointK = new javax.swing.JTextField();
        jTextFieldNumber = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHills = new javax.swing.JTable();
        jButtonEditHill = new javax.swing.JButton();
        jButtonRemoveHill = new javax.swing.JButton();
        jButtonAddHill = new javax.swing.JButton();
        jButtonClearTextFields = new javax.swing.JButton();
        jLabelPointHS = new javax.swing.JLabel();
        jTextFieldPointWind = new javax.swing.JTextField();
        jLabelPointDistance = new javax.swing.JLabel();
        jLabelPointWind = new javax.swing.JLabel();
        jTextFieldPointHS = new javax.swing.JTextField();
        jTextFieldPointDistance = new javax.swing.JTextField();
        jLabelImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        jLabelName.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelName.setText("Nazwa:");
        jPanel1.add(jLabelName);
        jLabelName.setBounds(118, 108, 58, 48);

        jLabelCountry.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelCountry.setText("Kraj:");
        jPanel1.add(jLabelCountry);
        jLabelCountry.setBounds(136, 162, 40, 48);

        jLabelPointK.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelPointK.setText("Punkt K:");
        jPanel1.add(jLabelPointK);
        jLabelPointK.setBounds(108, 216, 68, 48);

        jLabelNumber.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelNumber.setText("Numer:");
        jPanel1.add(jLabelNumber);
        jLabelNumber.setBounds(117, 54, 59, 48);

        jTextFieldName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldName);
        jTextFieldName.setBounds(186, 120, 166, 26);

        jTextFieldCountry.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCountryActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCountry);
        jTextFieldCountry.setBounds(186, 174, 166, 26);

        jTextFieldPointK.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPointK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPointKActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPointK);
        jTextFieldPointK.setBounds(186, 228, 166, 26);

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
        jPanel1.add(jTextFieldNumber);
        jTextFieldNumber.setBounds(186, 66, 166, 26);

        jTableHills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer", "Nazwa", "Kraj", "Punkt K", "Punkt HS", "Pkty za 1m", "Pkty za 1m/s"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableHills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableHillsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableHills);
        if (jTableHills.getColumnModel().getColumnCount() > 0) {
            jTableHills.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTableHills.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(370, 11, 811, 577);

        jButtonEditHill.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonEditHill.setText("Edytuj");
        jButtonEditHill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditHillActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonEditHill);
        jButtonEditHill.setBounds(128, 489, 100, 43);

        jButtonRemoveHill.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonRemoveHill.setText("Usuń");
        jButtonRemoveHill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveHillActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRemoveHill);
        jButtonRemoveHill.setBounds(246, 489, 100, 43);

        jButtonAddHill.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonAddHill.setText("Dodaj");
        jButtonAddHill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddHillActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAddHill);
        jButtonAddHill.setBounds(10, 489, 100, 43);

        jButtonClearTextFields.setText("Wyczyść dane");
        jButtonClearTextFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearTextFieldsActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonClearTextFields);
        jButtonClearTextFields.setBounds(186, 440, 166, 23);

        jLabelPointHS.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelPointHS.setText("Punkt HS:");
        jPanel1.add(jLabelPointHS);
        jLabelPointHS.setBounds(97, 270, 79, 48);

        jTextFieldPointWind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPointWind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPointWindActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPointWind);
        jTextFieldPointWind.setBounds(186, 386, 166, 26);

        jLabelPointDistance.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelPointDistance.setText("Pkty za odl:");
        jPanel1.add(jLabelPointDistance);
        jLabelPointDistance.setBounds(81, 324, 95, 48);

        jLabelPointWind.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabelPointWind.setText("Pkty za wiatr:");
        jPanel1.add(jLabelPointWind);
        jLabelPointWind.setBounds(65, 374, 111, 48);

        jTextFieldPointHS.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPointHS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPointHSActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPointHS);
        jTextFieldPointHS.setBounds(186, 282, 166, 26);

        jTextFieldPointDistance.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPointDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPointDistanceActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPointDistance);
        jTextFieldPointDistance.setBounds(186, 336, 166, 26);

        jLabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/snow.jpg"))); // NOI18N
        jLabelImage.setToolTipText("");
        jLabelImage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabelImage);
        jLabelImage.setBounds(-20, 0, 1220, 690);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1198, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPointWindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPointWindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPointWindActionPerformed

    private void jButtonClearTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearTextFieldsActionPerformed
        clearJTextFields();
    }//GEN-LAST:event_jButtonClearTextFieldsActionPerformed

    private void jButtonAddHillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddHillActionPerformed

        try
        {
            String name = jTextFieldName.getText();
            String country = jTextFieldCountry.getText();
            String pointK = jTextFieldPointK.getText();
            String pointHS = jTextFieldPointHS.getText();
            String pointDistance = jTextFieldPointDistance.getText();
            String pointWind = jTextFieldPointWind.getText();
            
            if (name.equals("") || country.equals("") || pointK.equals("") || pointHS.equals("") || pointDistance.equals("") || pointWind.equals(""))
            {
                throw new IllegalAccessException();
            }
            
            Class.forName(cwdb.getDriver());
            try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
            {

                int pointKInt = Integer.parseInt(pointK);
                int pointHSInt = Integer.parseInt(pointHS);
                double pointDistanceDouble = Double.parseDouble(pointDistance);
                double pointWindDouble = Double.parseDouble(pointWind);

              
                if (isHillsInDatabase(name, country, pointKInt, pointHSInt, pointDistanceDouble, pointWindDouble))
                {
                    JOptionPane.showMessageDialog(null, "W bazie danych istnieje już taka skocznia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO skocznie (name, country, point_k, point_hs, point_distance, point_wind) VALUES (?,?,?,?,?,?)");
                    ps.execute("USE skijumpingproject");
                    ps.setString(1, name);
                    ps.setString(2, country);
                    ps.setInt(3, pointKInt);
                    ps.setInt(4, pointHSInt);
                    ps.setDouble(5, pointDistanceDouble);
                    ps.setDouble(6, pointWindDouble);

                    ps.executeUpdate();
                    connection.close();

                    DefaultTableModel model = (DefaultTableModel)jTableHills.getModel();
                    model.setRowCount(0);
                    showHillsInJTable();
                    clearJTextFields();
                    JOptionPane.showMessageDialog(null, "Dodawanie skoczni zakończone sukcesem.");
                }

            }
        } 
         catch (NumberFormatException e4)
            {
                JOptionPane.showMessageDialog(null, "Źle wprowadzone dane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
               // System.out.println(e4.getMessage());
            }catch (ClassNotFoundException error1)
        {
            JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error1.getMessage());
        } catch (IllegalAccessException error2)
        {
            JOptionPane.showMessageDialog(null, "Dodawanie skoczni nie powiodło się. Proszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error2.getMessage());
        } catch (SQLException error3)
        {
            JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error3.getMessage());
        }
    }//GEN-LAST:event_jButtonAddHillActionPerformed

    private void jButtonRemoveHillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveHillActionPerformed

        try
        {
            String name = jTextFieldName.getText();
            String country = jTextFieldCountry.getText();
            String pointK = jTextFieldPointK.getText();
            String pointHS = jTextFieldPointHS.getText();
            String pointDistance = jTextFieldPointDistance.getText();
            String pointWind = jTextFieldPointWind.getText();

            if (name.equals("") || country.equals("") || pointK.equals("") || pointHS.equals("") || pointDistance.equals("") || pointWind.equals(""))
            {
                throw new IllegalAccessException();
            }

            String hillData = "\"" + name + " - " + country + " - " + pointK + " - " + pointHS +  "\"";
            if(JOptionPane.showConfirmDialog (null, "Czy na pewno usunąć wybraną skocznię?\n" + hillData,"Potwierdzenie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                Class.forName(cwdb.getDriver());
                try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
                {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM skocznie WHERE name = ? AND country = ? AND point_k = ? AND point_hs = ? AND point_distance = ? AND point_wind = ?");
                    ps.execute("USE skijumpingproject");
                    ps.setString(1, name);
                    ps.setString(2, country);
                    ps.setString(3, pointK);
                    ps.setString(4, pointHS);
                    ps.setString(5, pointDistance);
                    ps.setString(6, pointWind);

                    ps.executeUpdate();
                    connection.close();

                    DefaultTableModel model = (DefaultTableModel)jTableHills.getModel();
                    model.setRowCount(0);
                    showHillsInJTable();
                    clearJTextFields();
                    JOptionPane.showMessageDialog(null, "Usuwanie skoczni zakończone sukcesem.");
                }
            }
        } catch (ClassNotFoundException error1)
        {
            JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error1.getMessage());
        } catch (IllegalAccessException error2)
        {
            JOptionPane.showMessageDialog(null, "Usuwanie skoczni nie powiodło się. Proszę spróbować ponownie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error2.getMessage());
        } catch (SQLException error3)
        {
            JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error3.getMessage());
        }
    }//GEN-LAST:event_jButtonRemoveHillActionPerformed

    private void jButtonEditHillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditHillActionPerformed

        try
        {
            String oldName = jTextFieldName.getText();
            String oldCountry = jTextFieldCountry.getText();
            String oldPointK = jTextFieldPointK.getText();
            String oldPointHS = jTextFieldPointHS.getText();
            String oldPointDistance = jTextFieldPointDistance.getText();
            String oldPointWind = jTextFieldPointWind.getText();

            if(oldName.equals("") || oldCountry.equals("") || oldPointK.equals("") || oldPointHS.equals("") || oldPointDistance.equals("") || oldPointWind.equals(""))
            {
                throw new IllegalAccessException();
            }
             
            
            String name = JOptionPane.showInputDialog(null, "Wprowadź nową nazwę skoczni lub zostaw puste, aby zachować aktualne (" + oldName + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            String country = JOptionPane.showInputDialog(null, "Wprowadź nowy kraj lub zostaw puste, aby zachować aktualny (" + oldCountry + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            String pointK = JOptionPane.showInputDialog(null, "Wprowadź nowy punkt K lub zostaw puste, aby zachować aktualny (" + oldPointK + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            String pointHS = JOptionPane.showInputDialog(null, "Wprowadź nowy punkt HS lub zostaw puste, aby zachować aktualny (" + oldPointHS + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            String pointDistance = JOptionPane.showInputDialog(null, "Wprowadź nowy przelicznik za 1m lub zostaw puste, aby zachować aktualny (" + oldPointDistance + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            String pointWind = JOptionPane.showInputDialog(null, "Wprowadź nowy przelicznik za 1m/s wiatru lub zostaw puste, aby zachować aktualny (" + oldPointWind + "): ", "Edycja skoczni", JOptionPane.INFORMATION_MESSAGE);
            
            
            if(name == null || name.equals("")) name = oldName;
            if(country == null || country.equals("")) country = oldCountry;
            if(pointK == null || pointK.equals("")) pointK = oldPointK;
            if(pointHS == null || pointHS.equals("")) pointHS = oldPointHS;
            if(pointDistance == null || pointDistance.equals("")) pointDistance = oldPointDistance;
            if(pointWind == null || pointWind.equals("")) pointWind = oldPointWind;
            
            Class.forName(cwdb.getDriver());
            try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
            {
                int oldPointKInt = Integer.parseInt(oldPointK);
                int oldPointHSInt = Integer.parseInt(oldPointHS);
                
                double oldPointDistanceDouble = Double.parseDouble(oldPointDistance);
                double oldPointWindDouble = Double.parseDouble(oldPointWind);
            
                int pointKInt = Integer.parseInt(pointK);
                int pointHSInt = Integer.parseInt(pointHS);
                
                double pointDistanceDouble = Double.parseDouble(pointDistance);
                double pointWindDouble = Double.parseDouble(pointWind);
                
                if (isHillsInDatabase(name, country, pointKInt, pointHSInt, pointDistanceDouble, pointWindDouble))
                {
                    JOptionPane.showMessageDialog(null, "W bazie danych istnieje już taka skocznia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    PreparedStatement ps = connection.prepareStatement("UPDATE skocznie SET name = ?, country = ?, point_k = ?, point_hs = ?, point_distance = ?, point_wind = ? WHERE name = ? AND country = ? AND point_k = ? AND point_hs = ? AND point_distance = ? AND point_wind = ?");
                    ps.execute("USE skijumpingproject");
                    ps.setString(1, name);
                    ps.setString(2, country);
                    ps.setInt(3, pointKInt);
                    ps.setInt(4, pointHSInt);
                    ps.setDouble(5, pointDistanceDouble);
                    ps.setDouble(6, pointWindDouble);
                    ps.setString(7, oldName);
                    ps.setString(8, oldCountry);
                    ps.setInt(9, oldPointKInt);
                    ps.setInt(10, oldPointHSInt);
                    ps.setDouble(11, oldPointDistanceDouble);
                    ps.setDouble(12, oldPointWindDouble);

                    ps.executeUpdate();
                    connection.close();

                    clearJTextFields();
                    JOptionPane.showMessageDialog(null, "Edycja skoczni zakończona sukcesem.");

                    DefaultTableModel model = (DefaultTableModel)jTableHills.getModel();
                    model.setRowCount(0);
                    showHillsInJTable();
                }
            }
        } catch (NumberFormatException e4)
            {
                JOptionPane.showMessageDialog(null, "Źle wprowadzone dane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(e4.getMessage());
            }catch (ClassNotFoundException error1)
        {
            JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error1.getMessage());
        } catch (IllegalAccessException error2)
        {
            JOptionPane.showMessageDialog(null, "Wybierz skocznię do edycji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error2.getMessage());
        } catch (SQLException error3)
        {
            JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println(error3.getMessage());
        }
    }//GEN-LAST:event_jButtonEditHillActionPerformed

    private void jTableHillsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHillsMouseClicked
        int i = jTableHills.getSelectedRow();
        TableModel model = jTableHills.getModel();
        jTextFieldNumber.setText(model.getValueAt(i, 0).toString());
        jTextFieldName.setText(model.getValueAt(i, 1).toString());
        jTextFieldCountry.setText(model.getValueAt(i, 2).toString());
        jTextFieldPointK.setText(model.getValueAt(i, 3).toString());
        jTextFieldPointHS.setText(model.getValueAt(i, 4).toString());
        jTextFieldPointDistance.setText(model.getValueAt(i, 5).toString());
        jTextFieldPointWind.setText(model.getValueAt(i, 6).toString());
    }//GEN-LAST:event_jTableHillsMouseClicked

    private void jTextFieldNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumberActionPerformed

    private void jTextFieldPointKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPointKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPointKActionPerformed

    private void jTextFieldCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCountryActionPerformed

    private void jTextFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNameActionPerformed

    private void jTextFieldPointHSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPointHSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPointHSActionPerformed

    private void jTextFieldPointDistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPointDistanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPointDistanceActionPerformed

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
        java.awt.EventQueue.invokeLater(AddEditRemoveHillUI::new);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddHill;
    private javax.swing.JButton jButtonClearTextFields;
    private javax.swing.JButton jButtonEditHill;
    private javax.swing.JButton jButtonRemoveHill;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JLabel jLabelPointDistance;
    private javax.swing.JLabel jLabelPointHS;
    private javax.swing.JLabel jLabelPointK;
    private javax.swing.JLabel jLabelPointWind;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableHills;
    private javax.swing.JTextField jTextFieldCountry;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldNumber;
    private javax.swing.JTextField jTextFieldPointDistance;
    private javax.swing.JTextField jTextFieldPointHS;
    private javax.swing.JTextField jTextFieldPointK;
    private javax.swing.JTextField jTextFieldPointWind;
    // End of variables declaration//GEN-END:variables
}
