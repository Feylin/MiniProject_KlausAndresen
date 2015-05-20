package Controllers;

import Model.Country;
import Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 17-05-2015.
 */
@RestController
public class CountryController {
    @Autowired
    CountryService service;

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    private List<Country> getAllCountries()
    {
        return service.getAllCountries();
    }

    @RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
    public Country getCountry(@PathVariable("id") int id) {
        return service.getCountry(id);
    }

    @RequestMapping(value = "/deleteCountry/{id}", method = RequestMethod.DELETE)
    public void deleteCountry(@PathVariable("id") int id) {
        getAllCountries().remove(id);
    }

//    @RequestMapping(value = "/addCountry/{name}/{currency}/{iso2code}", method = RequestMethod.PUT)
//    public void putCountry(@PathVariable("name") String name,
//                           @PathVariable("currency") String currency,
//                           @PathVariable("iso2code") String iso2code) {
//        service.saveCountry(new Country().setName(name).setCurrency(currency).setAlpha2Code(iso2code));
//    }

//    @RequestMapping(value = "/addCountry/{country}", method = RequestMethod.POST)
//    public void putCountry(@PathVariable("country") Country country) {
//        service.saveCountry(country);
//    }

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
