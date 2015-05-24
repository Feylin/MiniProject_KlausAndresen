package Runnables;

import Service.RmiServerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 23-05-2015.
 */
public class FetchCurrencyThread implements Callable<HashMap<String, Double>> {
    private List<String> currencies;
    private HashMap<String, Double> currencyAndValues = new HashMap<>();

    public FetchCurrencyThread(List<String> currencies) {
        this.currencies = currencies;
    }

    @Override
    public HashMap<String, Double> call() throws Exception {
        for (String currency : currencies)
            currencyAndValues.put(currency, RmiServerImpl.fetchSingleCurrency(currency));
        return currencyAndValues;
    }
}
