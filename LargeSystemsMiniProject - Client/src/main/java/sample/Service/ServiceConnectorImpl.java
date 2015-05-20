package sample.Service;

import org.springframework.web.client.RestTemplate;
import sample.Model.Country;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 20-05-2015.
 */
public enum ServiceConnectorImpl implements ServiceConnector {
    INSTANCE;

    private static final String REST_SERVICE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Country> getAllCountries() {
        Country[] countries = restTemplate.getForObject(REST_SERVICE_URL + "countries", Country[].class);
        return Arrays.asList(countries);
    }

    public void saveCountry(Country country) {
        restTemplate.postForObject(REST_SERVICE_URL + "addCountry/country", country, Country.class);
    }

    public void deleteCountry(Country country) {
        restTemplate.delete(REST_SERVICE_URL + "deleteCountry/{country}", country);
    }

    public void deleteCountry(int id) {
        restTemplate.delete(REST_SERVICE_URL + "deleteCountry/{country}", id);
    }
}
