package Runnables;

import Controllers.JSONRestCountries;
import Model.Country;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 22-05-2015.
 */
public class FetchCountryDataThread implements Callable<Country> {
    Country country;

    public FetchCountryDataThread(Country country) {
        this.country = country;
    }

    @Override
    public Country call() throws Exception {
        HashMap<String, String> attributes = JSONRestCountries.INSTANCE.getCountryAttributes(country);

        return country.setCapital(attributes.get("capital"))
                .setPopulation(attributes.get("population"))
                .setRegion(attributes.get("region"))
                .setTimezone(attributes.get("timezone"));
    }
}