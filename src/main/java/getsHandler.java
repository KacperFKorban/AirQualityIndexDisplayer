import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.api.scripting.URLReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Singleton class for reading data From File/URL
 */
public class getsHandler {
  /**
   * Returns parsed Stations
   *
   * @param fileName name of the file to parse
   * @return parsed stations
   * @throws FileNotFoundException if such a file doesn't exist
   */
  public static Stations getStationsFile(String fileName) throws FileNotFoundException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Stations.class, new StationsDeserializer()).create();
    JsonReader reader = new JsonReader(new FileReader(fileName));
    Stations data = gson.fromJson(reader, Stations.class);
    return data;
  }

  /**
   * Returns parsed Stations
   *
   * @param url address of url to parse
   * @return parsed stations
   * @throws MalformedURLException if given a malformed url
   */
  public static Stations getStationsURL(String url) throws MalformedURLException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Stations.class, new StationsDeserializer()).create();
    JsonReader reader = new JsonReader(new URLReader(new URL(url)));
    Stations data = gson.fromJson(reader, Stations.class);
    return data;
  }

  /**
   * Returns parsed Sensors
   *
   * @param fileName name of the file to parse
   * @return parsed sensors
   * @throws FileNotFoundException if such a file doesn't exist
   */
  public static Sensors getSensorsFile(String fileName) throws FileNotFoundException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Sensors.class, new SensorsDeserializer()).create();
    JsonReader reader = new JsonReader(new FileReader(fileName));
    Sensors data = gson.fromJson(reader, Sensors.class);
    return data;
  }

  /**
   * Returns parsed Sensors
   *
   * @param url address of url to parse
   * @return parsed sensors
   * @throws MalformedURLException if given a malformed url
   */
  public static Sensors getSensorsURL(String url) throws MalformedURLException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Sensors.class, new SensorsDeserializer()).create();
    JsonReader reader = new JsonReader(new URLReader(new URL(url)));
    Sensors data = gson.fromJson(reader, Sensors.class);
    return data;
  }

  /**
   * Returns parsed Readings
   *
   * @param fileName name of the file to parse
   * @return parsed readings
   * @throws FileNotFoundException if such a file doesn't exist
   */
  public static Readings getReadingsFile(String fileName) throws FileNotFoundException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Readings.class, new ReadingsDeserializer()).create();
    JsonReader reader = new JsonReader(new FileReader(fileName));
    Readings data = gson.fromJson(reader, Readings.class);
    return data;
  }

  /**
   * Returns parsed Readings
   *
   * @param url address of url to parse
   * @return parsed readings
   * @throws MalformedURLException if given a malformed url
   */
  public static Readings getReadingsURL(String url) throws MalformedURLException {
    Gson gson = new GsonBuilder().registerTypeAdapter(Readings.class, new ReadingsDeserializer()).create();
    JsonReader reader = new JsonReader(new URLReader(new URL(url)));
    Readings data = gson.fromJson(reader, Readings.class);
    return data;
  }

  /**
   * Returns parsed IndexLevels
   *
   * @param fileName name of the file to parse
   * @return parsed index levels
   * @throws FileNotFoundException if such a file doesn't exist
   */
  public static IndexLevels getIndexFile(String fileName) throws FileNotFoundException {
    Gson gson = new GsonBuilder().registerTypeAdapter(IndexLevels.class, new IndexLevelsDeserializer()).create();
    JsonReader reader = new JsonReader(new FileReader(fileName));
    IndexLevels data = gson.fromJson(reader, IndexLevels.class);
    return data;
  }

  /**
   * Returns parsed IndexLevels
   *
   * @param url address of url to parse
   * @return parsed index levels
   * @throws MalformedURLException if given a malformed url
   */
  public static IndexLevels getIndexURL(String url) throws MalformedURLException {
    Gson gson = new GsonBuilder().registerTypeAdapter(IndexLevels.class, new IndexLevelsDeserializer()).create();
    JsonReader reader = new JsonReader(new URLReader(new URL(url)));
    IndexLevels data = gson.fromJson(reader, IndexLevels.class);
    return data;
  }
}
