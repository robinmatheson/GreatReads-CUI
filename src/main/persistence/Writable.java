package persistence;

import org.json.JSONObject;

// source: JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object

    JSONObject toJson();
}
