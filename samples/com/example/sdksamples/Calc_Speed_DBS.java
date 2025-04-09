package com.example.sdksamples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

public class Calc_Speed_DBS {

    public static void main(String[] args) {
        // Připojení k databázi
        Connection con = CreateConnection.getConnection("root", "root", 3306, "rfid_vari");

        if (con == null) {
            System.out.println("Connection to database failed!");
            return;
        }
        System.out.println("Připojení k databázi bylo úspěšné.\n");

        try {
            // SQL dotaz pro výběr dat
            String query = "SELECT * FROM rfid_reader ORDER BY Antena ASC, Last_time ASC, SPZ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Vytvoření seznamu pro průjezdy
            ArrayList<CarPassage> passages = new ArrayList<>();
            HashSet<Double> processedTimes = new HashSet<>();

            while (rs.next()) {
                String spz = rs.getString("SPZ");
                int antenna = rs.getInt("Antena");
                double lastTime = rs.getLong("Last_time") / 1000.0; // Převod času na sekundy
                double expectedSpeed = rs.getDouble("Expected_Speed");
                String vari = rs.getString("Variant");

               

                // Přidání dat do seznamu průjezdů
                passages.add(new CarPassage(spz, antenna, lastTime, expectedSpeed, vari));
            }

            // Hledání a výpočet rychlosti mezi anténami 1 a 9
            for (int i = 0; i < passages.size(); i++) {
                CarPassage first = passages.get(i);

                // Hledáme průjezdy s anténou 1
                if (first.antenna == 1 && !processedTimes.contains(first.lastTime)) {
                    // Hledáme odpovídající záznam na anténě 9 s časovým rozdílem < 20 sekund
                    CarPassage second = null;
                    for (int j = i + 1; j < passages.size(); j++) {
                        CarPassage potentialSecond = passages.get(j);

                        // Podmínky pro odpovídající záznam na anténě 9
                        if (potentialSecond.antenna == 9 && first.spz.equals(potentialSecond.spz)) {
                            double timeDifference = potentialSecond.lastTime - first.lastTime; // Rozdíl času v sekundách
                            if (timeDifference > 0 && timeDifference <= 20) {
                                second = potentialSecond;
                                break;
                            }
                        }
                    }

                    // Pokud nenajdeme odpovídající záznam na anténě 9, přeskočíme tento průchod
                    if (second == null) {
                        System.out.println("SPZ: " + first.spz + " - Neexistuje odpovídající záznam na anténě 9 v časovém limitu 20 sekund. Přeskakuji.\n");
                        continue;
                    }

                    // Výpočet rychlosti mezi anténami
                    double timeDifference = second.lastTime - first.lastTime; // rozdíl času v sekundách
                    double distanceInMeters = 12.0; // Délka dráhy mezi anténami v metrech

                    if (timeDifference > 0) {
                        // Rychlost v m/s a km/h
                        double speedMs = distanceInMeters / timeDifference;
                        double speedKmH = speedMs * 3.6;

                        // Výstup výsledků
                        System.out.println("Variant: " + first.vari);
                        System.out.println("SPZ: " + first.spz);
                        System.out.println("Expected Speed: " + first.expectedSpeed + " km/h");
                        System.out.printf("Rozdíl času (s): %.3f\n", timeDifference);
                        System.out.printf("Rychlost (km/h): %.2f\n\n", speedKmH);
                    }

                    // Označíme časy jako zpracované
                    processedTimes.add(first.lastTime);
                    processedTimes.add(second.lastTime);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Třída pro reprezentaci průjezdů
class CarPassage {

    String spz;
    int antenna;
    double lastTime; // Čas v sekundách
    double expectedSpeed;
    String vari;

    public CarPassage(String spz, int antenna, double lastTime, double expectedSpeed, String vari) {
        this.spz = spz;
        this.antenna = antenna;
        this.lastTime = lastTime;
        this.expectedSpeed = expectedSpeed;
        this.vari = vari;
    }
}
