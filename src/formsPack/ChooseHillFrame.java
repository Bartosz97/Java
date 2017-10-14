package formsPack;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import skijumpingMechanicPack.Hill;

/**
 *
 * @author Bartosz
 */
public class ChooseHillFrame extends JFrame implements ActionListener{
    private JPanel jPanel;
    private JButton jButton;
    private JComboBox jCombo;

        public ChooseHillFrame () {

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("SKI JUMPING - Wybór skoczni");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_skiJumpingProjectKask.png")));
        LookFrame.setLookAndFeel();

        jPanel = new JPanel(new GridLayout(2,1, 40, 40));
        jPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        jCombo = new JComboBox();
        jCombo.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));
        ((JLabel)jCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < MainFrame.hillsList.size(); i++)
        {
            jCombo.addItem(MainFrame.hillsList.get(i).getName() + " - K " + MainFrame.hillsList.get(i).getPointK() + " (HS - " + MainFrame.hillsList.get(i).getPointHS() + ")");
        }

        jButton = new JButton("Zatwierdź");
        jButton.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 15));

        jPanel.add(jCombo);
        jPanel.add(jButton);
        add(jPanel);

        jButton.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();

        if (jButton == evt)
        {   
            int indexHill = jCombo.getSelectedIndex();
            new CompetitionFrame(new Hill(MainFrame.hillsList.get(indexHill).getName(), MainFrame.hillsList.get(indexHill).getCountry(), MainFrame.hillsList.get(indexHill).getPointK(), MainFrame.hillsList.get(indexHill).getPointHS(), MainFrame.hillsList.get(indexHill).getPointDistance(), MainFrame.hillsList.get(indexHill).getPointWind()));
            dispose();
        }
    }
}
