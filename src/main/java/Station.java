import com.google.gson.JsonDeserializer;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

/**
 * Class representing a station
 */
public class Station {
  private int id;
  private String name;
  private Sensors sensors;
  private IndexLevels indexLevels;

  public Station() {
    sensors = new Sensors();
    indexLevels = new IndexLevels();
  }

  /**
   * Returns name of the station
   *
   * @return name of the station
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name of the station
   *
   * @param name new name of the station
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns id of the station
   *
   * @return id of the station
   */
  public int getId() {
    return id;
  }

  /**
   * Sets id of the station
   *
   * @param id new id of the station
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns sensors of the station
   *
   * @return sensors of the station
   */
  public Sensors getSensors() {
    return sensors;
  }

  /**
   * Sets sensors of the station
   *
   * @param sensors new sensors of the station
   */
  public void setSensors(Sensors sensors) {
    this.sensors = sensors;
  }

  /**
   * Returns index levels of the station
   *
   * @return index levels of the station
   */
  public IndexLevels getIndexLevels() {
    return indexLevels;
  }

  /**
   * Sets index levels of the station
   *
   * @param indexLevels new index levels of the station
   */
  public void setIndexLevels(IndexLevels indexLevels) {
    this.indexLevels = indexLevels;
  }
}
