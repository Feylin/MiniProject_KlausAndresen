package Service;

import DAO.CountryDAO;
import Model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 17-05-2015.
 */
@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDAO countryDAO;

    @Transactional(readOnly = false)
    public void deleteCountry(Country country) {
        countryDAO.delete(country);
    }

    @Transactional(readOnly = false)
    public void deleteCountry(int id) {
        countryDAO.delete(id);
    }

    @Transactional(readOnly = false)
    public void deleteCountry(String name) {
        countryDAO.delete(name);
    }

    @Transactional(readOnly = true)
    public Country getCountry(int id) {
        return countryDAO.get(id);
    }

    @Transactional(readOnly = true)
    public Country getCountry(String name) {
        return countryDAO.get(name);
    }

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryDAO.listAllCountries();
    }

    @Transactional(readOnly = false)
    public void saveCountry(Country country) {
        countryDAO.save(country);
    }
}
