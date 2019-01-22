import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JsonDeserializer class for IndexLevels
 */
public class IndexLevelsDeserializer implements JsonDeserializer<IndexLevels> {
  @Override
  public IndexLevels deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    IndexLevels indexLevels = new IndexLevels();
    JsonObject jsonObject = json.getAsJsonObject();
    String indexLevel = jsonObject.get("stIndexLevel").getAsJsonObject().get("indexLevelName").getAsString();
    String timestampS = jsonObject.get("stSourceDataDate").getAsString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse(timestampS);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    Timestamp timestamp = new Timestamp(parsedDate.getTime());
    indexLevels.put(timestamp, indexLevel);
    return indexLevels;
  }
}
