package Controllers;

import Model.Country;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 21-05-2015.
 */
public enum JSONRestCountries {
    INSTANCE;

    private static final String REST_COUNTRIES_URL = "http://restcountries.eu/rest/v1/name/";

    public HashMap<String, String> getCountryAttributes(Country country) {
        HashMap<String, String> attributes = new HashMap<>();

        try {
            URL url = URLEncoder.getEncodedURLString(REST_COUNTRIES_URL + country.getName() + "?fullText=true");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root;
            try (InputStreamReader streamReader = new InputStreamReader((InputStream) request.getContent(), StandardCharsets.UTF_8)) {
                root = jp.parse(streamReader);
            }
            JsonArray rootObj = root.getAsJsonArray();

            int firstObject = 0;

            attributes.put("population", rootObj.get(firstObject).getAsJsonObject().get("population").getAsString());
            attributes.put("capital", rootObj.get(firstObject).getAsJsonObject().get("capital").getAsString());
            attributes.put("region", rootObj.get(firstObject).getAsJsonObject().get("region").getAsString());
            attributes.put("latlng", rootObj.get(firstObject).getAsJsonObject().get("latlng").getAsJsonArray().toString());
            JsonArray timezones = rootObj.get(firstObject).getAsJsonObject().get("timezones").getAsJsonArray();
            attributes.put("timezone", timezones.get(timezones.size() - 1).getAsString());

            return attributes;
        } catch (IOException | URISyntaxException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return attributes;
    }
}
