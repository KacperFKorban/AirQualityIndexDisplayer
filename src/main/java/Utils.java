import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.api.scripting.URLReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Utils class
 */
public class Utils {

  private static String getSensorDataURL = "http://api.gios.gov.pl/pjp-api/rest/data/getData/";
  private static String getAllStationsURL = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";
  private static String getStationDataURL = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/";
  private static String getIndexURL = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/";

  private static String getAllStationsFile = "data/stations.json";
  private static String getSensorDataFile = "data/sensor";
  private static String getStationDataFile = "data/station";
  private static String getIndexFile = "data/index";

  /**
   * Prints a help message
   */
  public static void printHelp() {
    StringBuilder res = new StringBuilder();
    res.append("AirQualityIndexDisplayer\n");
    res.append("Usage:\n");
    res.append("\tDisplaying index for given station:\n");
    res.append("\t\t-1\n");
    res.append("\t\t[stationName]\n");
    res.append("\tDisplaying index value for given station, parameter and timestamp:\n");
    res.append("\t\t-2\n");
    res.append("\t\t[stationName] [timestamp] [parameterCode]\n");
    res.append("\tDisplaying average index value for given station, period and parameter code:\n");
    res.append("\t\t-3\n");
    res.append("\t\t[stationName] [startTime] [endTime] [parameterCode]\n");
    res.append("\tDisplaying parameter with biggest fluctuations from given timestamp:\n");
    res.append("\t\t-4\n");
    res.append("\t\t[stationName] [timestamp]\n");
    res.append("\tDisplaying parameter with lowest index value at given timestamp:\n");
    res.append("\t\t-5\n");
    res.append("\t\t[timestamp]\n");
    res.append("\tDisplaying parameters above limit for given station and timestamp:\n");
    res.append("\t\t-6\n");
    res.append("\t\t[stationName] [timestamp]\n");
    res.append("\tDisplaying information about smallest and biggest value for given parameter:\n");
    res.append("\t\t-7\n");
    res.append("\t\t[parameterCode]\n");
    res.append("\tDrawing a diagram of index values for given station, period and parameter code:\n");
    res.append("\t\t-8\n");
    res.append("\t\t[stationName] [startTime] [endTime] [parameterCode]\n");
    res.append("\tUpdating data:\n");
    res.append("\t\t-9\n");
    System.out.println(res.toString());
  }

  /**
   * Handles program arguments and input
   *
   * @param args program arguments
   * @throws MalformedURLException if given a malformed url
   * @throws FileNotFoundException if given a non existing file
   */
  public static void handleInput(String[] args) throws MalformedURLException, FileNotFoundException {
    if(args.length == 0) {
      printHelp();
    }
    else {
      Stations stations = null;
      Scanner scanner = new Scanner(System.in);
      String stationName;
      String paramCode;
      Param param;
      String timestampS;
      Timestamp timestamp;
      Timestamp startTime;
      Timestamp endTime;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      Date parsedDate = null;

      if(args[0].equals("-9")) {
        Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).create();
        JsonReader jsonReader = new JsonReader(new URLReader(new URL(getAllStationsURL)));
        String s = gson.fromJson(jsonReader, String.class);
        PrintWriter out = new PrintWriter(getAllStationsFile);
        out.println(s);
        out.flush();

        stations = getsHandler.getStationsFile(getAllStationsFile);

        int n = stations.getStations().size();
        int i = 0;
        for(Station station : stations.getStations().values()) {
          i++;
          System.out.println("Station " + i + " from " + n);
          jsonReader = new JsonReader(new URLReader(new URL(getStationDataURL + station.getId())));
          s = gson.fromJson(jsonReader, String.class);
          out = new PrintWriter(getStationDataFile + station.getId() + ".json");
          out.println(s);
          out.flush();
          station.setSensors(getsHandler.getSensorsFile(getStationDataFile + station.getId() + ".json"));
        }


        i = 0;
        for(Station station : stations.getStations().values()) {
          i++;
          System.out.println("Station " + i + " from " + n);
          for(Sensor sensor : station.getSensors().getSensors().values()) {
            jsonReader = new JsonReader(new URLReader(new URL(getSensorDataURL + sensor.getSensorId())));
            s = gson.fromJson(jsonReader, String.class);
            out = new PrintWriter(getSensorDataFile + sensor.getSensorId() + ".json");
            out.println(s);
            out.flush();
          }
        }

        i = 0;
        for(Station station : stations.getStations().values()) {
          i++;
          System.out.println("Station " + i + " from " + n);
          jsonReader = new JsonReader(new URLReader(new URL(getIndexURL + station.getId())));
          s = gson.fromJson(jsonReader, String.class);
          out = new PrintWriter(getIndexFile + station.getId() + ".json");
          out.println(s);
          out.flush();
        }
      }
      else {
        stations = getsHandler.getStationsFile(getAllStationsFile);
        for(Station station : stations.getStations().values()) {
          station.setSensors(getsHandler.getSensorsFile(getStationDataFile + station.getId() + ".json"));
          station.setIndexLevels(getsHandler.getIndexFile(getIndexFile + station.getId() + ".json"));
        }
        for(Station station : stations.getStations().values()) {
          for(Sensor sensor : station.getSensors().getSensors().values()) {
            sensor.setReadings(getsHandler.getReadingsFile(getSensorDataFile + sensor.getSensorId() + ".json"));
          }
        }
      }

      switch(args[0]) {
        case "-1":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.println(stations.getIndexOfStation(stationName));
          break;
        case "-2":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.print("timestamp: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          timestamp = new Timestamp(parsedDate.getTime());
          System.out.print("paramCode: ");
          paramCode = scanner.nextLine();
          param = Param.fromString(paramCode);
          System.out.println(stations.getParamValueFor(timestamp, stationName, param));
          break;
        case "-3":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.print("startTime: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          startTime = new Timestamp(parsedDate.getTime());
          System.out.print("endTime: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          endTime = new Timestamp(parsedDate.getTime());
          System.out.print("paramCode: ");
          paramCode = scanner.nextLine();
          param = Param.fromString(paramCode);
          System.out.println(stations.getAverageForParam(startTime, endTime, param, stationName));
          break;
        case "-4":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.print("timestamp: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          timestamp = new Timestamp(parsedDate.getTime());
          System.out.println(stations.getBiggestFluctuationsFor(stationName, timestamp));
          break;
        case "-5":
          System.out.print("timestamp: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          timestamp = new Timestamp(parsedDate.getTime());
          System.out.println(stations.getSmallest(timestamp));
          break;
        case "-6":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.print("timestamp: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          timestamp = new Timestamp(parsedDate.getTime());
          stations.getBiggestOverLimitParams(timestamp, stationName).forEach(System.out::println);
          break;
        case "-7":
          System.out.print("paramCode: ");
          paramCode = scanner.nextLine();
          param = Param.fromString(paramCode);
          System.out.println(stations.getBiggestAndSmallest(param));
          break;
        case "-8":
          System.out.print("stationName: ");
          stationName = scanner.nextLine();
          System.out.print("startTime: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          startTime = new Timestamp(parsedDate.getTime());
          System.out.print("endTime: ");
          timestampS = scanner.nextLine();
          try {
            parsedDate = dateFormat.parse(timestampS);
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          endTime = new Timestamp(parsedDate.getTime());
          System.out.print("paramCode: ");
          paramCode = scanner.nextLine();
          param = Param.fromString(paramCode);
          DiagramMakerVisitor dmv = new DiagramMakerVisitor('\1', 20);
          System.out.println(stations.accept(stationName, param, startTime, endTime, dmv));
          break;
        default:
          printHelp();
      }
    }
  }
}
