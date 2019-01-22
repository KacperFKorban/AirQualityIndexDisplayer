import java.sql.Timestamp;
import java.util.LinkedHashMap;

/**
 * Class containing index levels
 */
public class IndexLevels {
  private LinkedHashMap<Timestamp, String> indexLevels;

  public IndexLevels() {
    indexLevels = new LinkedHashMap<>();
  }

  /**
   * Adds a new index measurement
   *
   * @param timestamp time of the measurement
   * @param string index level
   */
  public void put(Timestamp timestamp, String string) {
    indexLevels.put(timestamp, string);
  }

  /**
   * Raturns a map of index levels
   *
   * @return map of index levels
   */
  public LinkedHashMap<Timestamp, String> getIndexLevels() {
    return indexLevels;
  }

}
