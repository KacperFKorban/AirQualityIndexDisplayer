import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class representing a sensor
 */
public class Sensor {
  private int sensorId;
  private Param param;
  private Readings readings;

  public Sensor() {
    readings = new Readings();
  }

  /**
   * Returns sensor id
   *
   * @return sensor id
   */
  public int getSensorId() {
    return sensorId;
  }

  /**
   * Sets sensor id
   *
   * @param sensorId new sensor id
   */
  public void setSensorId(int sensorId) {
    this.sensorId = sensorId;
  }

  /**
   * Returns air pollution parameter
   *
   * @return air pollution parameter
   */
  public Param getParam() {
    return param;
  }

  /**
   * Sets air pollution parameter
   *
   * @param param new air pollution parameter
   */
  public void setParam(Param param) {
    this.param = param;
  }

  /**
   * Returns sensor readings
   *
   * @return sensor readings
   */
  public Readings getReadings() {
    return readings;
  }

  /**
   * Sets sensor readings
   *
   * @param readings new sensor readings
   */
  public void setReadings(Readings readings) {
    this.readings = readings;
  }

  /**
   * Returns biggest between inside a given period
   *
   * @param startTime start of the period
   * @param endTime end of the period
   * @return biggest reading between a given period
   */
  public Double max(Timestamp startTime, Timestamp endTime) {
    return this
            .getReadings()
            .getReadings()
            .entrySet()
            .stream()
            .filter(e -> e.getKey().after(startTime) && e.getKey().before(endTime))
            .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .get().getValue();
  }

  /**
   * Returns smallest between inside a given period
   *
   * @param startTime start of the period
   * @param endTime end of the period
   * @return smallest reading between a given period
   */
  public Double min(Timestamp startTime, Timestamp endTime) {
    return this
            .getReadings()
            .getReadings()
            .entrySet()
            .stream()
            .filter(e -> e.getKey().after(startTime) && e.getKey().before(endTime))
            .min((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .get().getValue();
  }

  /**
   * Returns biggest fluctuation from a given timestamp
   *
   * @param fromTime start of the checked period
   * @return biggest fluctuation from a given timestamp
   */
  public Double fluctuation(Timestamp fromTime) {
    double max = this
            .getReadings()
            .getReadings()
            .entrySet()
            .stream()
            .filter(e -> e.getKey().after(fromTime) || e.getKey().equals(fromTime))
            .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .get().getValue();
    double min = this
            .getReadings()
            .getReadings()
            .entrySet()
            .stream()
            .filter(e -> e.getKey().after(fromTime) || e.getKey().equals(fromTime))
            .min((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .get().getValue();
    return max - min;
  }
}
