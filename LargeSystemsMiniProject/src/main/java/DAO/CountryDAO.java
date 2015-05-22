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

    void delete(String name);

    Country get(int id);

    Country get(String name);

    List<Country> listAllCountries();
}
