import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Class representing Stations
 */
public class Stations {
  private LinkedHashMap<String, Station> stations;

  public Stations() {
    stations = new LinkedHashMap<>();
  }

  /**
   * Adds a station to the map
   *
   * @param station new station
   */
  public void addStation(Station station) {
    stations.put(station.getName(), station);
  }

  /**
   * Returns stations
   *
   * @return stations
   */
  public LinkedHashMap<String, Station> getStations() {
    return stations;
  }

  /**
   * Returns index level of given station
   *
   * @param name name of the station
   * @return index level of given station
   */
  public String getIndexOfStation(String name) {
    return stations
            .get(name)
            .getIndexLevels()
            .getIndexLevels()
            .entrySet()
            .parallelStream()
            .max(Comparator.
                    comparing(Map.Entry::getKey))
            .get()
            .getValue();
  }

  /**
   * Returns index value for given station, parameter and time
   *
   * @param time queried time
   * @param name queried station name
   * @param param queried parameter
   * @return index value for given station, parameter and time
   */
  public Double getParamValueFor(Timestamp time, String name, Param param) {
    Double res = null;
    try {
      res = stations
              .get(name)
              .getSensors()
              .getSensors()
              .get(param)
              .getReadings()
              .getReadings()
              .get(time);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Returns average index value for given station, period and parameter
   *
   * @param startTime beginning of the queried period
   * @param endTime end of the queried period
   * @param param queried parameter
   * @param stationName queried station name
   * @return average index value for given station, period and parameter
   */
  public double getAverageForParam(Timestamp startTime, Timestamp endTime, Param param, String stationName) {
    Double res = null;
    try {
      res = stations
              .get(stationName)
              .getSensors()
              .getSensors()
              .get(param)
              .getReadings()
              .getReadings()
              .entrySet()
              .stream()
              .filter(e -> (e.getKey().after(startTime) && e.getKey().before(endTime)) || e.getKey().equals(startTime) || e.getKey().equals(endTime))
              .mapToDouble(a -> a.getValue())
              .sum() / stations
              .get(stationName)
              .getSensors()
              .getSensors()
              .get(param)
              .getReadings()
              .getReadings()
              .entrySet()
              .stream()
              .filter(e -> (e.getKey().after(startTime) && e.getKey().before(endTime)) || e.getKey().equals(startTime) || e.getKey().equals(endTime))
              .count();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Returns parameter with biggest fluctuations from given timestamp
   *
   * @param name queried station name
   * @param fromTime beginning of queried period
   * @return parameter with biggest fluctuations from given timestamp
   */
  public Param getBiggestFluctuationsFor(String name, Timestamp fromTime) {
    return stations
            .get(name)
            .getSensors()
            .getSensors()
            .values()
            .stream()
            .max((x, y) -> x.fluctuation(fromTime).compareTo(y.fluctuation(fromTime)))
            .get()
            .getParam();
  }

  /**
   * Returns parameter with lowest index value at given timestamp
   *
   * @param timestamp queried time
   * @return parameter with lowest index value at given timestamp
   */
  public Param getSmallest(Timestamp timestamp) {
    Param minParam = null;
    Double minVal = 1e18;
    for (Param param : Param.values()) {
      Double minValParam = 1e18;
      for (Station station : stations.values()) {
        Double tmp;
        try {
          tmp = station
                  .getSensors()
                  .getSensors()
                  .get(param)
                  .getReadings()
                  .getReadings()
                  .get(timestamp);
        }
        catch(Exception e) {
          tmp = 1e20;
        }
        if (tmp != null && tmp < minValParam) {
          minValParam = tmp;
        }
      }
      if (minValParam < minVal) {
        minVal = minValParam;
        minParam = param;
      }
    }
    return minParam;
  }

  /**
   * Returns parameters above limit for given station and timestamp
   *
   * @param timestamp queried time
   * @param stationName queried station name
   * @return parameters above limit for given station and timestamp
   */
  public List<Param> getBiggestOverLimitParams(Timestamp timestamp, String stationName) {
    TreeMap<Param, Double> map = new TreeMap<>();
    for (Param param : Param.values()) {
      if(stations.get(stationName).getSensors().getSensors().get(param) != null) {
        Double val = stations
                .get(stationName)
                .getSensors()
                .getSensors()
                .get(param)
                .getReadings()
                .getReadings()
                .get(timestamp) - param.limit();
        map.put(param, val);
      }
    }
    return new ArrayList<>(map.keySet());
  }

  /**
   * Returns information about smallest and biggest value for given parameter
   *
   * @param param queried parameter
   * @return information about smallest and biggest value for given parameter
   */
  public String getBiggestAndSmallest(Param param) {
    String smallestName = null;
    Timestamp smallestTimestamp = null;
    Double smallest = 1e18;
    for(Station station : stations.values()) {
      if(station.getSensors().getSensors().get(param) != null) {
        for (Map.Entry entry : station.getSensors().getSensors().get(param).getReadings().getReadings().entrySet()) {
          Timestamp time = (Timestamp) entry.getKey();
          Double val = (Double) entry.getValue();
          if (val < smallest) {
            smallest = val;
            smallestName = station.getName();
            smallestTimestamp = time;
          }
        }
      }
    }
    String biggestName = null;
    Timestamp biggestTimestamp = null;
    Double biggest = 0.0;
    for(Station station : stations.values()) {
      if(station.getSensors().getSensors().get(param) != null) {
        for (Map.Entry entry : station.getSensors().getSensors().get(param).getReadings().getReadings().entrySet()) {
          Timestamp time = (Timestamp) entry.getKey();
          Double val = (Double) entry.getValue();
          if (val > biggest) {
            biggest = val;
            biggestName = station.getName();
            biggestTimestamp = time;
          }
        }
      }
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Smallest " + param + " value for " + smallestName + " in " + smallestTimestamp + "\n");
    stringBuilder.append("Biggest " + param + " value for " + biggestName + " in " + biggestTimestamp + "\n");
    return stringBuilder.toString();
  }

  /**
   * Returns a diagram of index values for given station, period and parameter code
   *
   * @param stationName queried station name
   * @param param queried parameter
   * @param startTime beginning of the queried period
   * @param endTime end of the queried period
   * @param dmv Diagram maker visitor
   * @return a diagram of index values for given station, period and parameter code
   */
  public String accept(String stationName, Param param, Timestamp startTime, Timestamp endTime, DiagramMakerVisitor dmv) {
    return dmv.visit(this, stationName, startTime, endTime, param);
  }



}
