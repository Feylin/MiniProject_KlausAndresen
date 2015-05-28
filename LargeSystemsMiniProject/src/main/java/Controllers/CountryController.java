package Controllers;

import Model.Country;
import Runnables.FetchCountryDataThread;
import Service.CountryService;
import Service.RmiConnector;
import Service.RmiServer;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 17-05-2015.
 */
@RestController
public class CountryController {
    @Autowired
    CountryService service;
    private JSONCountryDescription descriptionApi = JSONCountryDescription.INSTANCE;
    private RmiConnector rmiConnector = RmiConnector.INSTANCE;
    private HashMap<String, HashMap<String, Double>> countryAndCurrencies = new HashMap<>();
//    private JSONRestCountries restCountriesApi = JSONRestCountries.INSTANCE;

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    private List<Country> getAllCountries() {
        return service.getAllCountries();
    }

    @RequestMapping(value = "/countries/{name}", method = RequestMethod.GET)
    public Country getCountry(@PathVariable("name") String name) throws RemoteException {
        Country country = service.getCountry(name);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Country> result = executorService.submit(new FetchCountryDataThread(country));

        Country countryFromExecutor = null;
        try {
            countryFromExecutor = result.get();
        } catch (Exception e) {
            result.cancel(true);
        }
        executorService.shutdown();

        if (countryAndCurrencies.containsKey(country.getName()))
            country.setCurrencies(countryAndCurrencies.get(country.getName()));
        else if (rmiConnector.connectToRmi()) {
            if (!countryAndCurrencies.containsKey(country.getName())) {
                HashMap<String, Double> currencies = new HashMap<>();
                for (int i = 0; i < 3; i++) {
                    Pair<String, Double> currencyPair = rmiConnector.getRmiServer().exchangeRate(country.getCurrency());
                    currencies.put(currencyPair.getKey(), currencyPair.getValue());
                }
                countryAndCurrencies.put(country.getName(), currencies);
                country.setCurrencies(countryAndCurrencies.get(country.getName()));
            }
        }

        return countryFromExecutor.setDescription(descriptionApi.getDescription(country))
               .setImage("http://www.geonames.org/flags/x/" + country.getAlpha2Code().toLowerCase() + ".gif");
    }

    @RequestMapping(value = "deleteCountry/{name}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "name") String name) {
        service.deleteCountry(name);
    }

    @RequestMapping(value = "/updateCountry/{name}", method = RequestMethod.PUT)
    public Country updateCountry(@PathVariable("name") String name) throws RemoteException {
        Country country = service.getCountry(name);

        if (rmiConnector.connectToRmi()) {
            if (!countryAndCurrencies.containsKey(country.getName())) {
                HashMap<String, Double> currencies = new HashMap<>();
                for (int i = 0; i < 3; i++) {
                    Pair<String, Double> currencyPair = rmiConnector.getRmiServer().exchangeRate(country.getCurrency());
                    currencies.put(currencyPair.getKey(), currencyPair.getValue());
                }
                countryAndCurrencies.put(country.getName(), currencies);
                country.setCurrencies(countryAndCurrencies.get(country.getName()));
            } else {
                HashMap<String, Double> currenciesForCountry = countryAndCurrencies.get(name);

                for (String currency : currenciesForCountry.keySet()) {
                    double exchangeRate = rmiConnector.getRmiServer().exchangeRate(country.getCurrency(), currency);
                    currenciesForCountry.put(currency, exchangeRate);
                }
                countryAndCurrencies.put(country.getName(), currenciesForCountry);

                country.setCurrencies(currenciesForCountry);
            }
        }

        return country;
    }

    @RequestMapping(value = "/addCountry/country", method = RequestMethod.POST)
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {

        service.saveCountry(country);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }
}
