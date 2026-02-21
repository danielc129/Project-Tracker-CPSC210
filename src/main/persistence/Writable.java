package persistence;

import org.json.JSONObject;

// ATTRIBUTION: Based on JsonSerializationDemo 
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
