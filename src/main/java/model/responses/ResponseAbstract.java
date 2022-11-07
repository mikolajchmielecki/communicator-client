package model.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResponseAbstract {

    private final JsonElement root;

    public ResponseAbstract(String response) {
        this.root = new JsonParser().parse(response);
    }

    public int getStatus() {
        return root.getAsJsonObject().get("status").getAsInt();
    }

    public String getResponse() {
        return root.getAsJsonObject().get("response").getAsString();
    }

    protected JsonObject getBody() {
        return root.getAsJsonObject().get("body").getAsJsonObject();
    }

    @Override
    public String toString() {
        return getResponse() + "\n";
    }
}
