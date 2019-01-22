import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * JsonDeserializer class for copying entire JSONs
 */
public class StringDeserializer implements JsonDeserializer<String> {
  @Override
  public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return json.toString();
  }
}
