package Controllers;

import Model.Country;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 21-05-2015.
 */
public enum JSONCountryDescription {
    INSTANCE;

    private static final String GOOGLE_API_URL = "https://www.googleapis.com/freebase/v1/text/en/";

    public String getDescription(Country country) {
        try {
            URL url = new URL(GOOGLE_API_URL + country.getName().toLowerCase().replace(" ", "_"));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root;
            try (InputStreamReader streamReader = new InputStreamReader((InputStream) request.getContent(), StandardCharsets.UTF_8)) {
                root = jp.parse(streamReader);
            }
            JsonObject rootObj = root.getAsJsonObject();
            return rootObj.get("result").getAsString();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return "";
    }
}
