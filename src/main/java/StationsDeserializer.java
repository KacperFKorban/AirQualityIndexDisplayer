import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * JsonDeserializer class for Stations
 */
public class StationsDeserializer implements JsonDeserializer<Stations> {
  @Override
  public Stations deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Stations stations = new Stations();
    JsonArray jsonArray = json.getAsJsonArray();
    for(int i = 0; i < jsonArray.size(); i++) {
      Station station = new Station();
      JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get("id");
      if (!jsonElement.isJsonNull()) {
        station.setId(jsonElement.getAsInt());
      }
      jsonElement = jsonObject.get("stationName");
      if (!jsonElement.isJsonNull()) {
        station.setName(jsonElement.getAsString());
      }
      stations.addStation(station);
    }
    return stations;
  }
}
