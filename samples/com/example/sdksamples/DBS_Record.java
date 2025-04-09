package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Speed_Calculate class to handle tag reports and calculate speed and RSSI.
 */
public class DBS_Record implements TagReportListener {

    private Map<String, Integer> tagSeenCountMap = new HashMap<>();
    private Map<String, Double> tagRssiSumMap = new HashMap<>();

    private RFID_Ver_14_11 frame;
    private double antennaDistance; // Distance between ANT1 and ANT2 in meters
    private int ANT1;
    private int ANT2;
    private int speed;
    private String vari;
    private Connection con;

    public DBS_Record(RFID_Ver_14_11 frame, double antennaDistance, int ANT1, int ANT2, Connection con, int speed, String vari) {
        this.frame = frame;
        this.antennaDistance = antennaDistance;
        this.ANT1 = ANT1;
        this.ANT2 = ANT2;
        this.con = con;
        this.speed = speed;
        this.vari = vari;
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        try {
            for (Tag tag : report.getTags()) {
                try {
                    String epc = tag.getEpc().toString();
                    int antennaPort = tag.getAntennaPortNumber();
                    long currentSeenTime = tag.getLastSeenTime().getLocalDateTime().getTime();
                    double rssi = tag.getPeakRssiInDbm();

                    // SQL dotaz pro získání odpovídající SPZ z databáze
                    String getSpzQuery = "SELECT SPZ FROM SPZ WHERE Tag_ID = ?";
                    PreparedStatement getSpzStmt = con.prepareStatement(getSpzQuery);
                    getSpzStmt.setString(1, epc);

                    ResultSet spzResultSet = getSpzStmt.executeQuery();
                    String spz = null;

                    if (spzResultSet.next()) {
                        spz = spzResultSet.getString("SPZ");
                    } else {
                        System.out.println("SPZ nebyla nalezena pro EPC: " + epc);
                        continue; // Pokud SPZ neexistuje, přeskočíme další zpracování
                    }

                    // Aktualizace statistik (už pracujeme se SPZ místo EPC)
                    tagSeenCountMap.put(spz, tagSeenCountMap.getOrDefault(spz, 0) + 1);
                    tagRssiSumMap.put(spz, tagRssiSumMap.getOrDefault(spz, 0.0) + rssi);

                    double avgRssi = tagRssiSumMap.get(spz) / tagSeenCountMap.get(spz);

                    // SQL dotaz pro kontrolu posledního záznamu
                    String selectQuery = "SELECT Seen, First_time, Last_time, Pass_Number, RSSI_AVG FROM rfid_reader WHERE SPZ = ? AND Antena = ? ORDER BY Last_time DESC LIMIT 1";
                    PreparedStatement selectStmt = con.prepareStatement(selectQuery);
                    selectStmt.setString(1, spz);
                    selectStmt.setInt(2, antennaPort);

                    ResultSet resultSet = selectStmt.executeQuery();

                    if (resultSet.next()) {
                        long lastSeenTime = resultSet.getLong("Last_time");
                        int seen = resultSet.getInt("Seen");
                        int passNumber = resultSet.getInt("Pass_Number");

                        if (currentSeenTime - lastSeenTime > 10_000) { // Rozdíl větší než 10 sekund
                            // Vložení nového záznamu s inkrementovaným Pass_Number
                            String insertQuery = "INSERT INTO rfid_reader (SPZ, Antena, Seen, First_time, Last_time, Pass_Number, RSSI_AVG, Expected_Speed, Variant) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                            insertStmt.setString(1, spz);
                            insertStmt.setInt(2, antennaPort);
                            insertStmt.setInt(3, 1); // První průchod v novém záznamu
                            insertStmt.setLong(4, currentSeenTime);
                            insertStmt.setLong(5, currentSeenTime);
                            insertStmt.setInt(6, passNumber + 1); // Zvýšení Pass_Number
                            insertStmt.setDouble(7, avgRssi); // Uložení průměrného RSSI
                            insertStmt.setInt(8, speed); // Expected_Speed
                            insertStmt.setString(9, vari); // Uložení hodnoty Variant
                            insertStmt.execute();
                        } else {
                            // Aktualizace existujícího záznamu
                            String updateQuery = "UPDATE rfid_reader SET Seen = Seen + 1, Last_time = ?, RSSI_AVG = ?, Variant = ? WHERE SPZ = ? AND Antena = ? AND First_time = ?";
                            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                            updateStmt.setLong(1, currentSeenTime);
                            updateStmt.setDouble(2, avgRssi); // Aktualizace průměrného RSSI
                            updateStmt.setString(3, vari); // Aktualizace hodnoty Variant
                            updateStmt.setString(4, spz);
                            updateStmt.setInt(5, antennaPort);
                            updateStmt.setLong(6, resultSet.getLong("First_time"));
                            updateStmt.execute();
                        }
                    } else {
                        // Vložení nového záznamu (první průchod)
                        String insertQuery = "INSERT INTO rfid_reader (SPZ, Antena, Seen, First_time, Last_time, Pass_Number, RSSI_AVG, Expected_Speed, Variant) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                        insertStmt.setString(1, spz);
                        insertStmt.setInt(2, antennaPort);
                        insertStmt.setInt(3, 1);
                        insertStmt.setLong(4, currentSeenTime);
                        insertStmt.setLong(5, currentSeenTime);
                        insertStmt.setInt(6, 1); // První průchod má Pass_Number = 1
                        insertStmt.setDouble(7, avgRssi); // Uložení průměrného RSSI
                        insertStmt.setInt(8, speed); // Expected_Speed
                        insertStmt.setString(9, vari); // Uložení hodnoty Variant
                        insertStmt.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
