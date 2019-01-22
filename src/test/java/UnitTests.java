import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests class
 */
public class UnitTests {

  private static final double EPSILON = 1e-15;

  private static Stations stations;

  @BeforeClass
  public static void setUp() {
    stations = new Stations();
    Station station = new Station();
    station.setName("A");
    station.setId(1);
    IndexLevels indexLevels = new IndexLevels();
    indexLevels.put(Timestamp.valueOf("2019-01-21 08:00:00"), "Dobry");
    station.setIndexLevels(indexLevels);
    Sensors sensors = new Sensors();
    Sensor sensor = new Sensor();
    sensor.setSensorId(11);
    Readings readings = new Readings();
    readings.put(Timestamp.valueOf("2019-01-21 08:00:00"), 1600.0);
    readings.put(Timestamp.valueOf("2019-01-21 07:00:00"), 1500.0);
    readings.put(Timestamp.valueOf("2019-01-21 06:00:00"), 1700.0);
    readings.put(Timestamp.valueOf("2019-01-21 05:00:00"), 1800.0);
    readings.put(Timestamp.valueOf("2019-01-21 04:00:00"), 1400.0);
    readings.put(Timestamp.valueOf("2019-01-21 03:00:00"), 1600.0);
    sensor.setReadings(readings);
    sensor.setParam(Param.CO);
    sensors.put(Param.CO, sensor);
    Sensor sensor2 = new Sensor();
    sensor2.setSensorId(13);
    Readings readings2 = new Readings();
    readings2.put(Timestamp.valueOf("2019-01-21 08:00:00"), 160.0);
    readings2.put(Timestamp.valueOf("2019-01-21 07:00:00"), 150.0);
    readings2.put(Timestamp.valueOf("2019-01-21 06:00:00"), 170.0);
    readings2.put(Timestamp.valueOf("2019-01-21 05:00:00"), 180.0);
    readings2.put(Timestamp.valueOf("2019-01-21 04:00:00"), 140.0);
    readings2.put(Timestamp.valueOf("2019-01-21 03:00:00"), 160.0);
    sensor2.setReadings(readings2);
    sensor2.setParam(Param.PM10);
    sensors.put(Param.PM10, sensor2);
    station.setSensors(sensors);
    stations.addStation(station);
    Station station1 = new Station();
    station1.setName("B");
    station1.setId(2);
    IndexLevels indexLevels1 = new IndexLevels();
    indexLevels1.put(Timestamp.valueOf("2019-01-21 08:00:00"), "Zły");
    station1.setIndexLevels(indexLevels1);
    Sensors sensors1 = new Sensors();
    Sensor sensor1 = new Sensor();
    sensor1.setSensorId(12);
    Readings readings1 = new Readings();
    readings1.put(Timestamp.valueOf("2019-01-21 08:00:00"), 1800.0);
    readings1.put(Timestamp.valueOf("2019-01-21 07:00:00"), 1800.0);
    readings1.put(Timestamp.valueOf("2019-01-21 06:00:00"), 1900.0);
    readings1.put(Timestamp.valueOf("2019-01-21 05:00:00"), 2000.0);
    readings1.put(Timestamp.valueOf("2019-01-21 04:00:00"), 1700.0);
    readings1.put(Timestamp.valueOf("2019-01-21 03:00:00"), 1800.0);
    sensor1.setReadings(readings1);
    sensor1.setParam(Param.CO);
    sensors1.put(Param.CO, sensor1);
    station1.setSensors(sensors1);
    stations.addStation(station1);
  }

  @Test
  public void getIndexOfStationTestA() {
    assertEquals(stations.getIndexOfStation("A"), "Dobry");
  }

  @Test
  public void getIndexOfStationTestB() {
    assertEquals(stations.getIndexOfStation("B"), "Zły");
  }

  @Test
  public void getParamValueForTestACO() {
    assertEquals(stations.getParamValueFor(Timestamp.valueOf("2019-01-21 08:00:00"), "A", Param.CO), 1600.0, EPSILON);
  }

  @Test
  public void getParamValueForTestAPM10() {
    assertEquals(stations.getParamValueFor(Timestamp.valueOf("2019-01-21 06:00:00"), "A", Param.PM10), 170.0, EPSILON);
  }

  @Test
  public void getParamValueForTestBCO() {
    assertEquals(stations.getParamValueFor(Timestamp.valueOf("2019-01-21 07:00:00"), "B", Param.CO), 1800.0, EPSILON);
  }

  @Test
  public void getAverageForParamTestACO() {
    assertEquals(stations.getAverageForParam(Timestamp.valueOf("2019-01-21 03:00:00"), Timestamp.valueOf("2019-01-21 08:00:00")
      , Param.CO, "A"), 1600.0, EPSILON);
  }

  @Test
  public void getAverageForParamTestAPM10() {
    assertEquals(stations.getAverageForParam(Timestamp.valueOf("2019-01-21 03:00:00"), Timestamp.valueOf("2019-01-21 07:00:00")
            , Param.PM10, "A"), 160.0, EPSILON);
  }

  @Test
  public void getAverageForParamTestBCO() {
    assertEquals(stations.getAverageForParam(Timestamp.valueOf("2019-01-21 04:00:00"), Timestamp.valueOf("2019-01-21 08:00:00")
            , Param.CO, "B"), 1840.0, EPSILON);
  }

  @Test
  public void getBiggestFluctuationsForTestA() {
    assertEquals(stations.getBiggestFluctuationsFor("A", Timestamp.valueOf("2019-01-21 05:00:00")), Param.CO);
  }

  @Test
  public void getBiggestFluctuationsForTestB() {
    assertEquals(stations.getBiggestFluctuationsFor("B", Timestamp.valueOf("2019-01-21 05:00:00")), Param.CO);
  }

  @Test
  public void getSmallestTest() {
    assertEquals(stations.getSmallest(Timestamp.valueOf("2019-01-21 07:00:00")), Param.PM10);
  }

  @Test
  public void getBiggestOverLimitParamsTestA() {
    List<Param> l = new ArrayList<>();
    l.add(Param.CO);
    l.add(Param.PM10);
    assertEquals(stations.getBiggestOverLimitParams(Timestamp.valueOf("2019-01-21 07:00:00"), "A"), l);
  }

  @Test
  public void getBiggestOverLimitParamsTestB() {
    List<Param> l = new ArrayList<>();
    l.add(Param.CO);
    assertEquals(stations.getBiggestOverLimitParams(Timestamp.valueOf("2019-01-21 07:00:00"), "B"), l);
  }

  @Test
  public void getBiggestAndSmallestTest() {
    assertEquals(stations.getBiggestAndSmallest(Param.CO), "Smallest CO value for A in 2019-01-21 04:00:00.0\n" +
            "Biggest CO value for B in 2019-01-21 05:00:00.0\n");
  }

  @Test
  public void getDiagramTest() {
    DiagramMakerVisitor dmv = new DiagramMakerVisitor('\1', 20);
    assertEquals(stations.accept("A", Param.CO, Timestamp.valueOf("2019-01-21 04:00:00"), Timestamp.valueOf("2019-01-21 08:00:00"), dmv),
            "2019-01-21 04:00:00.0 \1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1 1400.0\n" +
                    "2019-01-21 05:00:00.0 \1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1 1800.0\n" +
                    "2019-01-21 06:00:00.0 \1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1 1700.0\n" +
                    "2019-01-21 07:00:00.0 \1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1 1500.0\n" +
                    "2019-01-21 08:00:00.0 \1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1\1 1600.0\n");
  }
}
