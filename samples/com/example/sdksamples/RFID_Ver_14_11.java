package com.example.sdksamples;

import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReportConfig;
import com.impinj.octane.ReportMode;
import com.impinj.octane.SearchMode;
import com.impinj.octane.Settings;
import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matee
 */
public class RFID_Ver_14_11 extends javax.swing.JFrame {

    private static short[] antennaIds = new short[2];
    private static ImpinjReader reader = new ImpinjReader();
    private static RFID_Ver_14_11 frame = new RFID_Ver_14_11();
    private static int speed = 0;
    private static String vari = "A";

    public RFID_Ver_14_11() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        first = new javax.swing.JTextField();
        second = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Start = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        Ex_speed = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Variant = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        first.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        first.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        first.setText("1");

        second.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        second.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        second.setText("9");

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Stop.setText("Stop");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        Ex_speed.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        Ex_speed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Ex_speed.setText("0");
        Ex_speed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ex_speedActionPerformed(evt);
            }
        });

        jLabel2.setText("Expected_Speed");

        jLabel3.setText("First");

        jLabel4.setText("Second");

        jLabel5.setText("Varianta");

        Variant.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        Variant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Variant.setText("A");
        Variant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VariantActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(Ex_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(second, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2))
                            .addComponent(Variant, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(second, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ex_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Variant, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        antennaIds[0] = Short.parseShort(first.getText().trim());
        antennaIds[1] = Short.parseShort(second.getText().trim());
        speed = Integer.parseInt(Ex_speed.getText().trim());
        vari  =  Variant.getText();
        System.out.println("Data aktualizovana");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
         new Thread(() -> {
            antennaIds[0] = Short.parseShort(first.getText().trim());
            antennaIds[1] = Short.parseShort(second.getText().trim());
            speed = Integer.parseInt(Ex_speed.getText().trim());
            vari  =  Variant.getText();
            

            try {
                // Nastavení IP adresy nebo hostname čtečky
                String hostname = "speedwayr-12-6a-dc.local";

                // Připojení k čtečce
                System.out.println("Connecting");
                reader.connect(hostname);

                // Získání výchozích nastavení
                Settings settings = reader.queryDefaultSettings();

                // Konfigurace reportu
                ReportConfig report = settings.getReport();
                report.setIncludePeakRssi(true);
                report.setIncludeAntennaPortNumber(true);
                report.setIncludeDopplerFrequency(true);
                report.setIncludeFirstSeenTime(true);
                report.setIncludeLastSeenTime(true);
                report.setIncludePeakRssi(true);
                report.setIncludePhaseAngle(true);
                report.setIncludeCrc(true);
                report.setIncludeSeenCount(true);
                report.setMode(ReportMode.Individual);

                // Nastavení režimu RF a hledání
                settings.setRfMode(1000);
                settings.setSearchMode(SearchMode.DualTarget);
                settings.setSession(1);

                AntennaConfigGroup antennas = settings.getAntennas();

                antennas.disableAll();

                antennas.enableById(antennaIds);

                for (short i = 0; i < antennaIds.length; i++) {
                    antennas.getAntenna(antennaIds[i]).setIsMaxRxSensitivity(true);
                    antennas.getAntenna(antennaIds[i]).setIsMaxTxPower(true);
                }

                // Apply the newly modified settings.
                System.out.println("Applying Settings");
                reader.applySettings(settings);
                
                Connection con = CreateConnection.getConnection("root", "root", 3306, "test_rfid");

                // Assign the TagsReported event listener.
                DBS_Record speed_db = new DBS_Record(frame,17,antennaIds[0],antennaIds[1],con,speed,vari);
                reader.setTagReportListener(speed_db);

                // Start reading.
                System.out.println("Starting");
                reader.start();

                // Wait for the user to press enter.
                System.out.println("Press Enter to exit.");
                Scanner s = new Scanner(System.in);
                s.nextLine();

                // Stop reading.
                System.out.println("Stopping");
                reader.stop();
                
                

                // Disconnect from the reader.
                System.out.println("Disconnecting");
                reader.disconnect();
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }).start(); // Start the new thread
    }//GEN-LAST:event_StartActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        new Thread(() -> {
            System.out.println("Stopping");
            try {
                reader.stop();
            } catch (OctaneSdkException ex) {
                Logger.getLogger(RFID_Ver_14_11.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Disconnecting");
            reader.disconnect();
        }).start(); // Start the new thread
    }//GEN-LAST:event_StopActionPerformed

    private void Ex_speedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ex_speedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ex_speedActionPerformed

    private void VariantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VariantActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                frame.setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField Ex_speed;
    private javax.swing.JButton Start;
    private javax.swing.JButton Stop;
    public javax.swing.JTextField Variant;
    private javax.swing.JTextField first;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField second;
    // End of variables declaration//GEN-END:variables
}
