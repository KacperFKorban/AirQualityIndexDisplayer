import java.util.LinkedHashMap;

/**
 * Class containing sensors
 */
public class Sensors {
  private LinkedHashMap<Param, Sensor> sensors;

  public Sensors() {
    this.sensors = new LinkedHashMap<>();
  }

  /**
   * Returns map of sensors
   *
   * @return map of sensors
   */
  public LinkedHashMap<Param, Sensor> getSensors() {
    return sensors;
  }

  /**
   * Adds a sensor to the map
   *
   * @param param air pollution parameter
   * @param sensor sensor data
   */
  public void put(Param param, Sensor sensor) {
    sensors.put(param, sensor);
  }
}
