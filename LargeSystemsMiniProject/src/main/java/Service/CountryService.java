package Service;

import Model.Country;

import java.util.List;

/**
 * Created by Administrator on 17-05-2015.
 */
public interface CountryService {
    void deleteCountry(String name);

    Country getCountry(String name);

    List<Country> getAllCountries();

    void saveCountry(Country country);
}
