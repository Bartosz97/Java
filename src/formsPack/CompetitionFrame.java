package formsPack;

import java.awt.Font;
import java.awt.Toolkit;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import skijumpingMechanicPack.Competition;
import skijumpingMechanicPack.ConnectWithDataBase;
import skijumpingMechanicPack.DoubleJSlider;
import skijumpingMechanicPack.Hill;

/**
 *
 * @author Bartosz
 */
public class CompetitionFrame extends javax.swing.JFrame {
    
    private static ArrayList<Competition> competitionList;
    private Hill competitionHill;
    private static int currentJumper;
    private DoubleJSlider doubleJSlider1, doubleJSlider2;
    
    private Thread thread;
    private Runnable runner;
    private static boolean isOverThread;
    
    private final int firstRoundCounter = 5; // ilosc zawodnikow w pierwszej serii
    private final int secondRoundCounter = 3; // ilosc zawodnikow w drugiej serii
    
    public CompetitionFrame(Hill hill) {
        competitionHill = hill;     // przydaje się przy pobieraniu punktu K i converterów za odległość/wiatr/ ew. rekord skoczni 
        setResizable(false);
        setTitle("SKI JUMPING - Zawody na skoczni " + competitionHill.getName() + " - HS  " + competitionHill.getPointHS());
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        
        ///// JSlider dodawany ręcznie, aby obsługiwał zmienne typu Double
        doubleJSlider1 = new DoubleJSlider(SwingConstants.HORIZONTAL, -300, 300, 0);
        doubleJSlider1.setBounds(755, 370, 83, 20);
        doubleJSlider2 = new DoubleJSlider(SwingConstants.HORIZONTAL, -300, 300, 0);
        doubleJSlider2.setBounds(925, 370, 83, 20);
        
        add(doubleJSlider1);
        add(doubleJSlider2);
            
        initComponents();
        pack();
        setLocationRelativeTo(null);
        
        
        isOverThread = false;        
        refreshWind();
        
        jLabelHillData.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        jLabelHillData.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelHillData.setText(competitionHill.getName() + " (" + competitionHill.getCountry() + ") - K " + competitionHill.getPointK());
        
        competitionList = getCompetitionList();   
        
       if(competitionList.size() < 30) 
       {
                setVisible(true);
                JOptionPane.showMessageDialog(null, "Nie można przeprowadzić konkursu. Na liście startowej musi być co najmniej 30 zawodników.", "Informacja", JOptionPane.WARNING_MESSAGE);
                dispose();
        } else
        {
            currentJumper = 0;

            jLabelCurrentJumper.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
            jLabelCurrentJumper.setHorizontalAlignment(SwingConstants.CENTER);
            jLabelCurrentJumper.setText("Aktualnie skacze: " + competitionList.get(currentJumper).getJumper().getFirstName() + " " + competitionList.get(currentJumper).getJumper().getLastName() + " (" + competitionList.get(currentJumper).getJumper().getCountry() + ")");

            showJumpersInJTable();
            addItemsToComboBox();
            notEnabledSecondRound();
            jButtonFinishCompetition.setEnabled(false);
            jTextFieldDistance1.grabFocus();

            setVisible(true);
        }
    }    
    
    private void refreshWind()
    {
        runner = new Runnable() 
        {
            @Override
            public void run()
            {
                double wind1 = 0, wind2 = 0;
                while(!isOverThread)
                {
                    try 
                    { 
                        wind1 = doubleJSlider1.getDoubleValue() / 100;
                        wind2 = doubleJSlider2.getDoubleValue() / 100;

                        doubleJSlider1.setToolTipText(wind1 + " m/s");
                        doubleJSlider2.setToolTipText(wind2 + " m/s");

                        jLabelWind1.setText(wind1 + " m/s");
                        jLabelWind2.setText(wind2 + " m/s");

                        Thread.sleep(100);
                    } 
                    catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(null, "Coś poszło nie tak...", "Informacja", JOptionPane.INFORMATION_MESSAGE); dispose();} 
                    catch (InterruptedException ex) { Logger.getLogger(CompetitionFrame.class.getName()).log(Level.SEVERE, null, ex);}
                }
            }
        };
        this.thread = new Thread(runner);
        thread.start(); 
    }

    private ArrayList<Competition> getCompetitionList()                     // wczytuje wszystkich skoczków do konkursu z bazy
    {
        competitionList = new ArrayList<Competition>();
        
        for (int i = 0; i < MainFrame.jumpersList.size(); i++)
        {
            competitionList.add(new Competition(0 , MainFrame.jumpersList.get(i), 0, 0, 0, 0));
        }   
        return competitionList;
    }
    
     private void showJumpersInJTable()                                           // WYŚWIETLANIE W TABELI SKOCZKOW (PRIVATE) 
    {                                                                             
        DefaultTableModel model = (DefaultTableModel) jTableCompetition.getModel();
        Object[] row = new Object[5];
        
        for(int i = 0; i < MainFrame.jumpersList.size(); i++)
        {
            row[0] = competitionList.get(i).getPlace();
            row[1] = competitionList.get(i).getJumper().getFirstName() + " " + competitionList.get(i).getJumper().getLastName() + " (" + competitionList.get(i).getJumper().getCountry()+ ")";
            row[2] = competitionList.get(i).getFirstRound();
            row[3] = competitionList.get(i).getSecondRound();
            row[4] = competitionList.get(i).getPoints();
            
            model.addRow(row);
        }
        
    }
     private void notEnabledSecondRound() {
         jTextFieldDistance2.setEnabled(false);
         jJudgeComboBox6.setEnabled(false);
         jJudgeComboBox7.setEnabled(false);
         jJudgeComboBox8.setEnabled(false);
         jJudgeComboBox9.setEnabled(false);
         jJudgeComboBox10.setEnabled(false);
         doubleJSlider2.setEnabled(false);
         jLabelWind2.setEnabled(false);
         jButtonInSecondRound.setEnabled(false);
     }
     private void notEnabledFirstRound() {
         jTextFieldDistance1.setEnabled(false);
         jJudgeComboBox1.setEnabled(false);
         jJudgeComboBox2.setEnabled(false);
         jJudgeComboBox3.setEnabled(false);
         jJudgeComboBox4.setEnabled(false);
         jJudgeComboBox5.setEnabled(false);
         doubleJSlider1.setEnabled(false);
         jLabelWind1.setEnabled(false);
         jButtonInFirstRound.setEnabled(false);
     }
     
     private void setEnabledSecondRound() {
         jTextFieldDistance2.setEnabled(true);
         jJudgeComboBox6.setEnabled(true);
         jJudgeComboBox7.setEnabled(true);
         jJudgeComboBox8.setEnabled(true);
         jJudgeComboBox9.setEnabled(true);
         jJudgeComboBox10.setEnabled(true);
         doubleJSlider2.setEnabled(true);
         jLabelWind2.setEnabled(true);
         jButtonInSecondRound.setEnabled(true);
     }
     
     
     private void saveCompetitionToDatabase()                                   // po naciśnieściu zakoncz zawody
     {
          try
        {
            for (int i = 0; i < competitionList.size(); i++)
         {
             competitionList.get(i).setPointsToWorldCup(Competition.pointsToWorldCup(competitionList.get(i).getPlace()));   // obsługa egzekwo
         }
         
        ConnectWithDataBase cwdb = new ConnectWithDataBase();
        Class.forName(cwdb.getDriver());
        try (Connection connection = DriverManager.getConnection(cwdb.getUrl(), cwdb.getLogin(), cwdb.getPassword()))
        {
            if (competitionHill.getName().equals("Oberstdorf") || competitionHill.getName().equals("Garmisch-Partenkirchen") || competitionHill.getName().equals("Innsbruck") || competitionHill.getName().equals("Bischofshofen"))
            {
                double points = 0;
                for (int i = 0; i < competitionList.size(); i++)
                    {
                        PreparedStatement psSetPoints4HT = connection.prepareStatement("SELECT fht.points FROM four_hills_tournament fht INNER JOIN skoczkowie s ON fht.id_skoczka = s.id_skoczka WHERE s.first_name=? AND s.last_name=? AND s.country=?");
                        psSetPoints4HT.execute("USE skijumpingproject");

                        psSetPoints4HT.setString(1, competitionList.get(i).getJumper().getFirstName());
                        psSetPoints4HT.setString(2, competitionList.get(i).getJumper().getLastName());
                        psSetPoints4HT.setString(3, competitionList.get(i).getJumper().getCountry()); 

                        ResultSet result = psSetPoints4HT.executeQuery();
                        if (result.next())
                        {  
                            points = (result.getDouble("points") + competitionList.get(i).getPoints());
                            competitionList.get(i).setPoints(points);

                        }
                    }
                    for (int i = 0; i < competitionList.size(); i++)
                    {
                        PreparedStatement psSavePoints4HT = connection.prepareStatement("UPDATE four_hills_tournament fht INNER JOIN skoczkowie s ON fht.id_skoczka = s.id_skoczka SET fht.points=? WHERE s.first_name = ? AND s.last_name = ? AND s.country = ?");
                        psSavePoints4HT.execute("USE skijumpingproject");

                        psSavePoints4HT.setDouble(1, competitionList.get(i).getPoints());
                        psSavePoints4HT.setString(2, competitionList.get(i).getJumper().getFirstName());
                        psSavePoints4HT.setString(3, competitionList.get(i).getJumper().getLastName());
                        psSavePoints4HT.setString(4, competitionList.get(i).getJumper().getCountry());
                        
                        psSavePoints4HT.executeUpdate();
                        
                    }
            }
                    for (int i = 0; i < competitionList.size(); i++)
                    {
                        PreparedStatement psSetPointsWC = connection.prepareStatement("SELECT wc.points FROM world_cup_17_18 wc INNER JOIN skoczkowie s ON wc.id_skoczka = s.id_skoczka WHERE s.first_name=? AND s.last_name=? AND s.country=?");
                        psSetPointsWC.execute("USE skijumpingproject");

                        psSetPointsWC.setString(1, competitionList.get(i).getJumper().getFirstName());
                        psSetPointsWC.setString(2, competitionList.get(i).getJumper().getLastName());
                        psSetPointsWC.setString(3, competitionList.get(i).getJumper().getCountry()); 

                        ResultSet result = psSetPointsWC.executeQuery();
                        if (result.next())
                        {
                            int points = 0;
                            points = result.getInt("points") + competitionList.get(i).getPointsToWorldCup();
                            competitionList.get(i).setPointsToWorldCup(points);
                        }
                    }
                    for (int i = 0; i < competitionList.size(); i++)
                    {
                        PreparedStatement psSavePointsWC = connection.prepareStatement("UPDATE world_cup_17_18 wc INNER JOIN skoczkowie s ON wc.id_skoczka = s.id_skoczka SET wc.points=? WHERE s.first_name = ? AND s.last_name = ? AND s.country = ?");
                        psSavePointsWC.execute("USE skijumpingproject");

                        psSavePointsWC.setInt(1, competitionList.get(i).getPointsToWorldCup());
                        psSavePointsWC.setString(2, competitionList.get(i).getJumper().getFirstName());
                        psSavePointsWC.setString(3, competitionList.get(i).getJumper().getLastName());
                        psSavePointsWC.setString(4, competitionList.get(i).getJumper().getCountry());
                        
                        psSavePointsWC.executeUpdate();
                        
                    }
                    connection.close();
                    
                    if (JOptionPane.showConfirmDialog(null, "Konkurs zakończony. Czy chcesz wrócić do menu głównego?", "Informacja", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    {
                        dispose();
                    }
                    
                            
        }   
            } catch (ClassNotFoundException error1)
            { 
                JOptionPane.showMessageDialog(null, "Błąd programu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error1.getMessage());
            } catch (SQLException error2)
            {
                JOptionPane.showMessageDialog(null, "Brak połączenia z serwerem. Sprawdź połączenie sieciowe.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(error2.getMessage());
            }
     }
     
     private void addItemsToComboBox()
     {
         double i = 20;
          while (-0.5 < i) {
            jJudgeComboBox1.addItem(i);
            jJudgeComboBox2.addItem(i);
            jJudgeComboBox3.addItem(i);
            jJudgeComboBox4.addItem(i);
            jJudgeComboBox5.addItem(i);
            jJudgeComboBox6.addItem(i);
            jJudgeComboBox7.addItem(i);
            jJudgeComboBox8.addItem(i);
            jJudgeComboBox9.addItem(i);
            jJudgeComboBox10.addItem(i);
            i -= 0.5;
        } 
     }
     
     private double getDistancePoints(int whichSeries, int pointK, double converterDistPoints)
     {
         double points = 0;
         double distance  = getDistnaceFromJTextField(whichSeries);             
         
             double distanceFromPointK = abs(pointK - distance);
         
             if (distance < pointK) 
             {
                 points = 60 - distanceFromPointK*converterDistPoints;
                 if (points < 0) points = 0;
             }
             else if (distance > pointK) 
             {
                 points = 60 + distanceFromPointK*converterDistPoints;
             }
             else points = 60.0;

             return points;
     }
     
     private double getDistnaceFromJTextField(int whichSeries)
     {
         double distance = 0;
         
             if (whichSeries == 1) distance = Double.parseDouble(jTextFieldDistance1.getText());
             else if (whichSeries == 2) distance = Double.parseDouble(jTextFieldDistance2.getText());
         return distance;          
     }
     
     private double getWindPoints(int whichSeries, double converterWindPoints)
     {
         double points = 0;
         double windSpeed = 0;
         
         if(whichSeries == 1)
         {
             windSpeed = doubleJSlider1.getDoubleValue() / 100;
             points = (double) Math.round(windSpeed * converterWindPoints); // zaokrąglenie do 2 miejsc po przecinku
         } else if (whichSeries == 2)
         {
             windSpeed = doubleJSlider2.getDoubleValue() / 100;
             points = (double) Math.round(windSpeed * converterWindPoints);
         }

         return points;
     }
     
     private double getArbitersPoints(int whichSeries)              
     {
         double points = 0;
         double notes [] = new double[5];
         
             if(whichSeries == 1)                                 
             {                 
                 notes[0] = (double) jJudgeComboBox1.getSelectedItem();
                 notes[1] = (double) jJudgeComboBox2.getSelectedItem();
                 notes[2] = (double) jJudgeComboBox3.getSelectedItem();
                 notes[3] = (double) jJudgeComboBox4.getSelectedItem();
                 notes[4] = (double) jJudgeComboBox5.getSelectedItem();
                 
             } else if (whichSeries == 2)                           
             {
                 notes[0] = (double) jJudgeComboBox6.getSelectedItem();
                 notes[1] = (double) jJudgeComboBox7.getSelectedItem();
                 notes[2] = (double) jJudgeComboBox8.getSelectedItem();
                 notes[3] = (double) jJudgeComboBox9.getSelectedItem();
                 notes[4] = (double) jJudgeComboBox10.getSelectedItem();
             }

             Arrays.sort(notes);
             for (int i = 1; i < 4; i++) // pętla tylko dla 3 środkowych wartości(not)
             {
                 points += notes[i];
             }
         return points;
     }
     
     private void operateExAequo () // obsługa egzekwo
     {
        int place = 1;
        int variable = 1;
        for (int i = 0; i < competitionList.size() - 1; i++)        
        {
            if (competitionList.get(i).getPoints() == competitionList.get(i + 1).getPoints())
            {
                competitionList.get(i).setPlace(place);
                competitionList.get(i + 1).setPlace(place);
                variable++;
            }
            else {
                place += variable;
                competitionList.get(i + 1).setPlace(place);
                variable = 1;

            }
        }
        if (competitionList.get(0).getPoints() != competitionList.get(1).getPoints())
        {
            competitionList.get(0).setPlace(1);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCompetition = new javax.swing.JTable();
        jLabelEnterData = new javax.swing.JLabel();
        jLabelDistance = new javax.swing.JLabel();
        jLabelFirstNote = new javax.swing.JLabel();
        jLabelSecondNote = new javax.swing.JLabel();
        jLabelThirdNote = new javax.swing.JLabel();
        jLabelFourthNote = new javax.swing.JLabel();
        jLabelFivethNote = new javax.swing.JLabel();
        jLabelFirstRound = new javax.swing.JLabel();
        jLabelSecondRound = new javax.swing.JLabel();
        jLabelWind = new javax.swing.JLabel();
        jButtonInFirstRound = new javax.swing.JButton();
        jButtonInSecondRound = new javax.swing.JButton();
        jTextFieldDistance1 = new javax.swing.JTextField();
        jTextFieldDistance2 = new javax.swing.JTextField();
        jButtonFinishCompetition = new javax.swing.JButton();
        jLabelHillData = new javax.swing.JLabel();
        jJudgeComboBox1 = new javax.swing.JComboBox<>();
        jJudgeComboBox2 = new javax.swing.JComboBox<>();
        jJudgeComboBox6 = new javax.swing.JComboBox<>();
        jJudgeComboBox3 = new javax.swing.JComboBox<>();
        jJudgeComboBox4 = new javax.swing.JComboBox<>();
        jJudgeComboBox5 = new javax.swing.JComboBox<>();
        jJudgeComboBox7 = new javax.swing.JComboBox<>();
        jJudgeComboBox8 = new javax.swing.JComboBox<>();
        jJudgeComboBox9 = new javax.swing.JComboBox<>();
        jJudgeComboBox10 = new javax.swing.JComboBox<>();
        jLabelCurrentJumper = new javax.swing.JLabel();
        jLabelWind1 = new javax.swing.JLabel();
        jLabelWind2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTableCompetition.setAutoCreateRowSorter(true);
        jTableCompetition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Miejsce", "Zawodnik", "I seria [m]", "II seria [m]", "Punkty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
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
        jScrollPane2.setViewportView(jTableCompetition);
        if (jTableCompetition.getColumnModel().getColumnCount() > 0) {
            jTableCompetition.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTableCompetition.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jLabelEnterData.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEnterData.setText("Wprowadź dane:");

        jLabelDistance.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelDistance.setText("Odległość:");

        jLabelFirstNote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFirstNote.setText("Sędzia 1:");

        jLabelSecondNote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelSecondNote.setText("Sędzia 2:");

        jLabelThirdNote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelThirdNote.setText("Sędzia 3:");

        jLabelFourthNote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFourthNote.setText("Sędzia 4:");

        jLabelFivethNote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFivethNote.setText("Sędzia 5:");

        jLabelFirstRound.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFirstRound.setText("I Seria:");

        jLabelSecondRound.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelSecondRound.setText("II Seria:");

        jLabelWind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelWind.setText("Wiatr:");

        jButtonInFirstRound.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonInFirstRound.setText("Zatwierdź");
        jButtonInFirstRound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInFirstRoundActionPerformed(evt);
            }
        });

        jButtonInSecondRound.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonInSecondRound.setText("Zatwierdź");
        jButtonInSecondRound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInSecondRoundActionPerformed(evt);
            }
        });

        jTextFieldDistance1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDistance1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDistance1ActionPerformed(evt);
            }
        });

        jTextFieldDistance2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDistance2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDistance2ActionPerformed(evt);
            }
        });

        jButtonFinishCompetition.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButtonFinishCompetition.setText("Zakończ zawody");
        jButtonFinishCompetition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinishCompetitionActionPerformed(evt);
            }
        });

        jJudgeComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jJudgeComboBox1.setToolTipText("");
        jJudgeComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jJudgeComboBox1ActionPerformed(evt);
            }
        });

        jJudgeComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jJudgeComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jJudgeComboBox2ActionPerformed(evt);
            }
        });

        jJudgeComboBox6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jJudgeComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jJudgeComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jJudgeComboBox3ActionPerformed(evt);
            }
        });

        jJudgeComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jJudgeComboBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jJudgeComboBox7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jJudgeComboBox8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jJudgeComboBox9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jJudgeComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jJudgeComboBox9ActionPerformed(evt);
            }
        });

        jJudgeComboBox10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabelWind1.setBackground(new java.awt.Color(255, 255, 255));
        jLabelWind1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelWind1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelWind1.setToolTipText("");
        jLabelWind1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelWind2.setBackground(new java.awt.Color(255, 255, 255));
        jLabelWind2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelWind2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelWind2.setToolTipText("");
        jLabelWind2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jButtonFinishCompetition, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelHillData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelFirstRound)
                                .addGap(100, 100, 100))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelDistance)
                                    .addComponent(jLabelFivethNote)
                                    .addComponent(jLabelFourthNote)
                                    .addComponent(jLabelThirdNote)
                                    .addComponent(jLabelSecondNote)
                                    .addComponent(jLabelFirstNote)
                                    .addComponent(jLabelWind))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonInFirstRound, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDistance1)
                                    .addComponent(jJudgeComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jJudgeComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jJudgeComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jJudgeComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jJudgeComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelWind1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(86, 86, 86)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonInSecondRound, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDistance2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelSecondRound)
                                .addGap(14, 14, 14))
                            .addComponent(jJudgeComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jJudgeComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jJudgeComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jJudgeComboBox9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jJudgeComboBox10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelWind2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelEnterData, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCurrentJumper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEnterData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelHillData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFirstRound)
                            .addComponent(jLabelSecondRound))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDistance)
                            .addComponent(jTextFieldDistance1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDistance2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelCurrentJumper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jJudgeComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelFirstNote)
                            .addComponent(jJudgeComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSecondNote)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jJudgeComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jJudgeComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelThirdNote)
                            .addComponent(jJudgeComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jJudgeComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFourthNote)
                            .addComponent(jJudgeComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jJudgeComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFivethNote)
                            .addComponent(jJudgeComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jJudgeComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelWind)
                                .addGap(24, 24, 24)
                                .addComponent(jLabelWind1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelWind2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonFinishCompetition, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonInFirstRound)
                        .addComponent(jButtonInSecondRound)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jJudgeComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jJudgeComboBox9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jJudgeComboBox9ActionPerformed

    private void jJudgeComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jJudgeComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jJudgeComboBox3ActionPerformed

    private void jJudgeComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jJudgeComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jJudgeComboBox2ActionPerformed

    private void jJudgeComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jJudgeComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jJudgeComboBox1ActionPerformed

    private void jButtonFinishCompetitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinishCompetitionActionPerformed
        // TODO add your handling code here:
        jButtonFinishCompetition.setEnabled(false);
        saveCompetitionToDatabase();
    }//GEN-LAST:event_jButtonFinishCompetitionActionPerformed

    private void jTextFieldDistance2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistance2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDistance2ActionPerformed

    private void jTextFieldDistance1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistance1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDistance1ActionPerformed

    private void jButtonInSecondRoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInSecondRoundActionPerformed

        double points = 0;
        jTextFieldDistance2.grabFocus();
        try
        {
            points = getDistancePoints(2, competitionHill.getPointK(), competitionHill.getPointDistance()) + getArbitersPoints(2) + getWindPoints(2, competitionHill.getPointWind());

            points = points + competitionList.get(currentJumper).getPoints();
            if (points < 0) points = 0;            
            competitionList.get(currentJumper).setPoints(points);   // dodawanie punktow do aktualnego skoczka ( do listy)
            competitionList.get(currentJumper).setSecondRound(getDistnaceFromJTextField(2)); // dodawanie odleglosci w II serii do aktualnego skoczka ( do listy)
            jTextFieldDistance2.setText("");

            DefaultTableModel model = (DefaultTableModel)jTableCompetition.getModel();      //odświeżanie widoku tabeli (zaktualizowana lista do jTable)
            model.setRowCount(0);
            showJumpersInJTable();
            
            if (currentJumper - 1 >= 0) jLabelCurrentJumper.setText("Aktualnie skacze: " + competitionList.get(currentJumper - 1).getJumper().getFirstName() + " " + competitionList.get(currentJumper - 1).getJumper().getLastName() + " (" + competitionList.get(currentJumper - 1).getJumper().getCountry() + ")");

            if(currentJumper == 0) // zakończenie zawodów
            {
                isOverThread = true;
               jLabelCurrentJumper.setText("KONIEC ZAWODÓW");
                
                notEnabledFirstRound();
                notEnabledSecondRound();

                competitionList = Competition.sort(competitionList);   // SORTOWANIE WYNIKOW W competitionList

                operateExAequo(); // osługa egzekwo

                model = (DefaultTableModel)jTableCompetition.getModel();      //odświeżanie widoku tabeli (zaktualizowana lista do jTable) RAZEM z miejscami
                model.setRowCount(0);
                showJumpersInJTable();

                jButtonFinishCompetition.setEnabled(true);
            }
            if (currentJumper > 0)
            currentJumper--;    // przejscie do kolejnego skoczka


        } catch (NumberFormatException error1) {
            JOptionPane.showMessageDialog(null, "Źle wprowadzone odległość. (wprowadź wg modelu: XXX.X)", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
            //System.out.println(error1.getMessage());
        }
    }//GEN-LAST:event_jButtonInSecondRoundActionPerformed

    private void jButtonInFirstRoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInFirstRoundActionPerformed

        double points = 0;
        jTextFieldDistance1.grabFocus();
        try
        {
            points = getDistancePoints(1, competitionHill.getPointK(), competitionHill.getPointDistance()) + getArbitersPoints(1) + getWindPoints(1, competitionHill.getPointWind());
            if (points < 0) points = 0;
            competitionList.get(currentJumper).setPoints(points);   // dodawanie punktow do aktualnego skoczka ( do listy)
            competitionList.get(currentJumper).setFirstRound(getDistnaceFromJTextField(1)); // dodawanie odleglosci w I serii do aktualnego skoczka ( do listy)
            jTextFieldDistance1.setText("");

            DefaultTableModel model = (DefaultTableModel)jTableCompetition.getModel();      //odświeżanie widoku tabeli (zaktualizowana lista do jTable)
            model.setRowCount(0);
            showJumpersInJTable();

            if (currentJumper + 1 < competitionList.size()) jLabelCurrentJumper.setText("Aktualnie skacze: " + competitionList.get(currentJumper+1).getJumper().getFirstName() + " " + competitionList.get(currentJumper+1).getJumper().getLastName() + " (" + competitionList.get(currentJumper+1).getJumper().getCountry() + ")");

            currentJumper++;    // przejscie do kolejnego skoczka

            if (currentJumper == firstRoundCounter){ //////// Koniec I serii
                notEnabledFirstRound();
                setEnabledSecondRound(); 

                
                competitionList = Competition.sort(competitionList); // aktualizowanie tabeli po I serii
                
                operateExAequo(); // osługa egzekwo

                model = (DefaultTableModel)jTableCompetition.getModel();      //odświeżanie widoku tabeli (zaktualizowana lista do jTable) RAZEM z miejscami
                model.setRowCount(0);
                showJumpersInJTable();

                currentJumper = secondRoundCounter - 1; // bo od 30 zawodnika do 1 będę teraz wprowadzał liczby
                jLabelCurrentJumper.setText("Aktualnie skacze: " + competitionList.get(currentJumper).getJumper().getFirstName() + " " + competitionList.get(currentJumper).getJumper().getLastName() + " (" + competitionList.get(currentJumper).getJumper().getCountry() + ")");
            }

        } catch (NumberFormatException error1) {
            JOptionPane.showMessageDialog(null, "Źle wprowadzone odległość. (wprowadź wg modelu: XXX.X)", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
            //System.out.println(error1.getMessage());
        }
    }//GEN-LAST:event_jButtonInFirstRoundActionPerformed

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
            java.util.logging.Logger.getLogger(CompetitionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompetitionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompetitionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompetitionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               //new CompetitionFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFinishCompetition;
    private javax.swing.JButton jButtonInFirstRound;
    private javax.swing.JButton jButtonInSecondRound;
    private javax.swing.JComboBox<Double> jJudgeComboBox1;
    private javax.swing.JComboBox<Double> jJudgeComboBox10;
    private javax.swing.JComboBox<Double> jJudgeComboBox2;
    private javax.swing.JComboBox<Double> jJudgeComboBox3;
    private javax.swing.JComboBox<Double> jJudgeComboBox4;
    private javax.swing.JComboBox<Double> jJudgeComboBox5;
    private javax.swing.JComboBox<Double> jJudgeComboBox6;
    private javax.swing.JComboBox<Double> jJudgeComboBox7;
    private javax.swing.JComboBox<Double> jJudgeComboBox8;
    private javax.swing.JComboBox<Double> jJudgeComboBox9;
    private javax.swing.JLabel jLabelCurrentJumper;
    private javax.swing.JLabel jLabelDistance;
    private javax.swing.JLabel jLabelEnterData;
    private javax.swing.JLabel jLabelFirstNote;
    private javax.swing.JLabel jLabelFirstRound;
    private javax.swing.JLabel jLabelFivethNote;
    private javax.swing.JLabel jLabelFourthNote;
    private javax.swing.JLabel jLabelHillData;
    private javax.swing.JLabel jLabelSecondNote;
    private javax.swing.JLabel jLabelSecondRound;
    private javax.swing.JLabel jLabelThirdNote;
    private javax.swing.JLabel jLabelWind;
    private javax.swing.JLabel jLabelWind1;
    private javax.swing.JLabel jLabelWind2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCompetition;
    private javax.swing.JTextField jTextFieldDistance1;
    private javax.swing.JTextField jTextFieldDistance2;
    // End of variables declaration//GEN-END:variables

}
