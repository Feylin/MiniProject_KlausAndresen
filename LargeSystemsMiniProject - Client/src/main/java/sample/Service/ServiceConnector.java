package sample.Service;

import sample.Model.Country;

import java.util.List;

/**
 * Created by Administrator on 20-05-2015.
 */
public interface ServiceConnector {
    List<Country> getAllCountries();

    Country getCountry(String name);

    void saveCountry(Country country);

    void deleteCountry(String name);

    Country updateCountry(String name);
}
