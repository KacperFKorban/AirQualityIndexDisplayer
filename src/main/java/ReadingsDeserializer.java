import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JsonDeserializer class for Readings
 */
public class ReadingsDeserializer implements JsonDeserializer<Readings> {
  @Override
  public Readings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Readings readings = new Readings();
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray values = jsonObject.get("values").getAsJsonArray();
    for(int i = 0; i < values.size(); i++) {
      JsonObject reading = values.get(i).getAsJsonObject();
      String timestampS = reading.get("date").getAsString();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      Date parsedDate = null;
      try {
        parsedDate = dateFormat.parse(timestampS);
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      Timestamp timestamp = new Timestamp(parsedDate.getTime());
      Double value = null;
      if(!reading.get("value").isJsonNull())
        value = reading.get("value").getAsDouble();
      if(value != null)
        readings.put(timestamp, value);
    }
    return readings;
  }
}
