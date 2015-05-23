package Controllers;

import Model.Country;
import Runnables.FetchCountryDataThread;
import Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    JSONCountryDescription descriptionApi = JSONCountryDescription.INSTANCE;
//    JSONRestCountries restCountriesApi = JSONRestCountries.INSTANCE;

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    private List<Country> getAllCountries() {
        return service.getAllCountries();
    }

    @RequestMapping(value = "/countries/{name}", method = RequestMethod.GET)
    public Country getCountry(@PathVariable("name") String name) {
        Country country = service.getCountry(name);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Country> result = executorService.submit(new FetchCountryDataThread(country));

        Country countryFromExecutor = null;
        try {
            countryFromExecutor = result.get();
        } catch (Exception e) {
            result.cancel(true);
        }
        executorService.shutdown();

        return countryFromExecutor.setDescription(descriptionApi.getDescription(country))
               .setImage("http://www.geonames.org/flags/x/" + country.getAlpha2Code().toLowerCase() + ".gif");
    }

    @RequestMapping(value = "deleteCountry/{name}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "name") String name) {
        service.deleteCountry(name);
    }

    @RequestMapping(value = "/updateCountry/country", method = RequestMethod.PUT)
    public @ResponseBody String updateCountry(@RequestBody Country country){
        service.saveCountry(country);
        return "ok";
    }

    @RequestMapping(value = "/addCountry/country", method = RequestMethod.POST)
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {

        service.saveCountry(country);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }
}
