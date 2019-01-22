import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * JsonDeserializer class for Sensors
 */
public class SensorsDeserializer implements JsonDeserializer<Sensors> {
  @Override
  public Sensors deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Sensors sensors = new Sensors();
    JsonArray jsonArray = json.getAsJsonArray();
    for(int i = 0; i < jsonArray.size(); i++) {
      Sensor sensor = new Sensor();
      JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get("id");
      if(!jsonElement.isJsonNull()) {
        sensor.setSensorId(jsonElement.getAsInt());
      }
      jsonElement = jsonObject.get("param").getAsJsonObject().get("paramCode");
      if(!jsonElement.isJsonNull()) {
        sensor.setParam(Param.fromString(jsonElement.getAsString()));
      }
      sensors.put(sensor.getParam(), sensor);
    }
    return sensors;
  }
}
