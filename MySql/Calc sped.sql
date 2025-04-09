
WITH first_ant AS (
    SELECT 
        SPZ, 
        Antena AS first_antenna, 
        Last_time / 1000.0 AS first_time, 
        Expected_Speed, 
        Variant
    FROM rfid_reader
    WHERE Antena = 1
), 
second_ant AS (
    SELECT 
        SPZ, 
        Antena AS second_antenna, 
        Last_time / 1000.0 AS second_time
    FROM rfid_reader
    WHERE Antena = 9
)
SELECT 
    f.Variant,
    f.SPZ,
    f.Expected_Speed AS Expected_Speed_kmh,
    (s.second_time - f.first_time) AS time_difference_seconds,
    (12.0 / (s.second_time - f.first_time)) * 3.6 AS calculated_speed_kmh
FROM first_ant f
JOIN second_ant s 
    ON f.SPZ = s.SPZ 
    AND (s.second_time - f.first_time) > 0
    AND (s.second_time - f.first_time) <= 20
ORDER BY f.first_time;

