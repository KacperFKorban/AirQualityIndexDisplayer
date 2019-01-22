import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.api.scripting.URLReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Integration tests class
 */
public class IntegrationTests {

  private static String getSensorDataURL = "http://api.gios.gov.pl/pjp-api/rest/data/getData/";
  private static String getAllStationsURL = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";
  private static String getStationDataURL = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/";
  private static String getIndexURL = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/";

  private static Gson gson;

  @BeforeClass
  public static void setup() {
    gson = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).create();
  }

  @Test
  public void getsStationsTest() throws MalformedURLException {
    JsonReader jsonReader = new JsonReader(new URLReader(new URL(getAllStationsURL)));
    String s = gson.fromJson(jsonReader, String.class);
    assertNotNull(s);
  }

  @Test
  public void getsStationDataTest() throws MalformedURLException {
    JsonReader jsonReader = new JsonReader(new URLReader(new URL(getStationDataURL + 400)));
    String s = gson.fromJson(jsonReader, String.class);
    assertNotNull(s);
  }

  @Test
  public void getsSensorDataTest() throws MalformedURLException {
    JsonReader jsonReader = new JsonReader(new URLReader(new URL(getSensorDataURL + 2750)));
    String s = gson.fromJson(jsonReader, String.class);
    assertNotNull(s);
  }

  @Test
  public void getsIndexTest() throws MalformedURLException {
    JsonReader jsonReader = new JsonReader(new URLReader(new URL(getIndexURL + 400)));
    String s = gson.fromJson(jsonReader, String.class);
    assertNotNull(s);
  }
}
