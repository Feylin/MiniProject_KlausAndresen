package sample.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.Model.Country;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 20-05-2015.
 */
public enum ServiceConnectorImpl implements ServiceConnector {
    INSTANCE;

    private static final String REST_SERVICE_URL = "http://localhost:8182/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Country> getAllCountries() {
        Country[] countries = restTemplate.getForObject(REST_SERVICE_URL + "countries", Country[].class);
        return Arrays.asList(countries);
    }

    public Country getCountry(String name) {
        return restTemplate.getForObject(REST_SERVICE_URL + "countries/{name}", Country.class, name);
    }

    public void saveCountry(Country country) {
        restTemplate.postForObject(REST_SERVICE_URL + "addCountry/country", country, Country.class);
    }

    public void deleteCountry(String name) {
        restTemplate.delete(REST_SERVICE_URL + "deleteCountry/{name}", name);
    }

    public Country updateCountry(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HashMap<String, Double>> entity = new HttpEntity<>(headers);
        ResponseEntity response = restTemplate.exchange(REST_SERVICE_URL + "updateCountry/{name}", HttpMethod.PUT, entity, Country.class, name);

        response.getHeaders().getLocation();
        response.getStatusCode();
        return (Country) response.getBody();
    }
}
