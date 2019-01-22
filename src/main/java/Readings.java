import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

/**
 * Class containing sensor readings
 */
public class Readings {
  private LinkedHashMap<Timestamp, Double> readings;

  public Readings() {
    readings = new LinkedHashMap<>();
  }

  /**
   * Adds a new sensor reading
   *
   * @param timestamp time of the measurement
   * @param value value of the measurement
   */
  public void put(Timestamp timestamp, Double value) {
    readings.put(timestamp, value);
  }

  /**
   * Returns map of readings
   *
   * @return map of readings
   */
  public LinkedHashMap<Timestamp, Double> getReadings() {
    return readings;
  }

}
