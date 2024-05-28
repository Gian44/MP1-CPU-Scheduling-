package source;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

public class SplashScreen extends JFrame {

	// Variables declaration - do not modify                     
    private JLabel AppTitle;
    private JLabel AnimationLabel;
    private JPanel SplashScreenPanel;
    private JLabel underline;
    // End of variables declaration 
	 
    public SplashScreen() {
        initComponents();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreen and open MainUI
                dispose(); // Close the SplashScreen
                
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new MainUI().setVisible(true);
                    }
                });
            }
        }, 100);
    }

    
    //Function to initialize components
    private void initComponents() {

        SplashScreenPanel = new JPanel();
        AppTitle = new JLabel();
        AnimationLabel = new JLabel();
        underline = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        SplashScreenPanel.setBackground(new java.awt.Color(255, 255, 255));
        SplashScreenPanel.setLayout(null);

        AppTitle.setFont(new java.awt.Font("OCR A Extended", 1, 60)); // NOI18N
        AppTitle.setText("ProSched");
        SplashScreenPanel.add(AppTitle);
        AppTitle.setBounds(100, 170, 310, 100);
        
        underline.setText("___________________________________________________________________");
        SplashScreenPanel.add(underline);
        underline.setBounds(90, 230, 330, 30);

        AnimationLabel.setIcon(new ImageIcon(getClass().getResource("/images/FCSC.gif"))); // NOI18N
        SplashScreenPanel.add(AnimationLabel);
        AnimationLabel.setBounds(0, 20, 500, 240);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(SplashScreenPanel, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(SplashScreenPanel, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null); //center the position of the JFrame
    }
    
    //Main
    public static void main(String args[]) {
       
        // Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SplashScreen().setVisible(true);
            }
        });
    }
                      
}

