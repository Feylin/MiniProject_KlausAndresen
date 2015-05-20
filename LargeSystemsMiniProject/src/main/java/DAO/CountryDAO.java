package DAO;

import Model.Country;

import java.util.List;

/**
 * Created by Administrator on 17-05-2015.
 */
public interface CountryDAO {

    void save(Country country);

    void delete(Country country);

    void delete(int id);

    Country get(int id);

    List<Country> listAllCountries();
}
