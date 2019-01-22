import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

/**
 * Visitor class to create diagram
 */
public class DiagramMakerVisitor {
  private char character;
  private int pipesLimit;

  public DiagramMakerVisitor(char character, int pipesLimit) {
    this.character = character;
    this.pipesLimit = pipesLimit;
  }

  /**
   * Returns a diagram of index values for given station, period and parameter code
   *
   * @param stations class containing stations
   * @param stationName queried station name
   * @param param queried parameter
   * @param startTime beginning of the queried period
   * @param endTime end of the queried period
   * @return a diagram of index values for given station, period and parameter code
   */
  public String visit(Stations stations, String stationName, Timestamp startTime, Timestamp endTime, Param param) {
    TreeMap<Timestamp, String> tm = new TreeMap<>();
    Double max = stations.getStations().get(stationName).getSensors().getSensors().get(param).max(startTime, endTime);
    Double valuePerPipe = max / pipesLimit;
    for(Map.Entry entry : stations.getStations().get(stationName).getSensors().getSensors().get(param).getReadings().getReadings().entrySet()) {
      Timestamp timestamp = (Timestamp) entry.getKey();
      Double value = (Double) entry.getValue();
      if((timestamp.after(startTime) && timestamp.before(endTime)) || timestamp.equals(startTime) || timestamp.equals(endTime)) {
        long pipes = Math.round(value / valuePerPipe);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pipes; i++) {
          stringBuilder.append(character);
        }
        tm.put(timestamp, stringBuilder.toString() + " " + value);
      }
    }
    StringBuilder stringBuilder = new StringBuilder();
    for(Map.Entry e : tm.entrySet()) {
      stringBuilder.append(e.getKey() + " " + e.getValue() + "\n");
    }
    return stringBuilder.toString();
  }
}
