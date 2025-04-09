package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Speed_meter_1 implements TagReportListener {

    private Map<String, Double> startTimeMap = new ConcurrentHashMap<>();
    private Map<String, Boolean> isTimingMap = new ConcurrentHashMap<>();
    private Map<String, Double> lastSeenTime = new ConcurrentHashMap<>();
    private static final double thresholdTime = 1.0; // in seconds

    private A_RFID_SPEED frame;
    private double antennaDistance; // Distance between ANT1 and ANT2 in meters
    private int ANT1;
    private int ANT2;
    

    private ScheduledExecutorService scheduler;

    public Speed_meter_1(A_RFID_SPEED frame, double antennaDistance, int ANT1, int ANT2) {
        this.frame = frame;
        this.antennaDistance = antennaDistance; // Initialize with actual distance
        this.ANT1 = ANT1;
        this.ANT2 = ANT2;

        // Initialize the scheduler
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::checkForInactiveTags, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        for (Tag tag : report.getTags()) {
            String epc = tag.getEpc().toString();

            if (!startTimeMap.containsKey(epc)) {
                if (tag.getAntennaPortNumber() == ANT1) {
                    System.out.println("Tag EPC: " + epc);
                    startTimeMap.put(epc, System.currentTimeMillis() / 1000.0);
                    isTimingMap.put(epc, true);
                    System.out.println("First tag detected by antenna " + ANT1 + ". Start time: " + startTimeMap.get(epc));
                }
            } else {
                if (tag.getAntennaPortNumber() == ANT2 && isTimingMap.getOrDefault(epc, false)) {
                    double currentTime = System.currentTimeMillis() / 1000.0;
                    lastSeenTime.put(epc, currentTime);
                    System.out.println("Tag detected by antenna " + ANT2 + ". Last seen time updated.");
                }
            }
        }
    }

    private void checkForInactiveTags() {
        double currentTime = System.currentTimeMillis() / 1000.0;
        for (String epc : new HashSet<>(lastSeenTime.keySet())) {
            double lastTimeSeen = lastSeenTime.get(epc);
            if ((currentTime - lastTimeSeen) > thresholdTime && isTimingMap.getOrDefault(epc, false)) {
                isTimingMap.put(epc, false);

                double elapsedTime = lastTimeSeen - startTimeMap.get(epc);
                double speed = (antennaDistance / 1000.0) / (elapsedTime / 3600.0); // km/h

                System.out.println("Tag " + epc + " was not seen for " + thresholdTime + " seconds.");
                System.out.println("Last seen time: " + lastTimeSeen);
                System.out.println("Elapsed time: " + elapsedTime + " seconds");
                System.out.println("Speed calculation: " + speed + " km/h");
                System.out.println("Timer stopped for tag " + epc);

                // Update the GUI
                javax.swing.SwingUtilities.invokeLater(() -> frame.Speed.setText(String.format("%.2f km/h", speed)));

                // Clean up maps
                startTimeMap.remove(epc);
                lastSeenTime.remove(epc);
                isTimingMap.remove(epc);
            }
        }
    }

    // Don't forget to shut down the scheduler when done
    public void shutdown() {
        scheduler.shutdownNow();
    }
}
