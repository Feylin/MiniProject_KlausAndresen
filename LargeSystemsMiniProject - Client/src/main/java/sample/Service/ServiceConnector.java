package sample.Service;

import sample.Model.Country;

import java.util.List;

/**
 * Created by Administrator on 20-05-2015.
 */
public interface ServiceConnector {
    List<Country> getAllCountries();

    Country getCountry(int id);

    void saveCountry(Country country);

    void deleteCountry(int id);

    void updateCountry(Country country);
}
