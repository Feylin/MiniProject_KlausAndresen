package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prep on 20-02-2015.
 */
public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {
    private static CurrencyLoader currencyCache = CurrencyLoader.INSTANCE;
    private static HashMap<String, Double> currencyExchange = new HashMap<>();

    public RmiServerImpl() throws RemoteException {
        super(0);
    }

    protected static void fillCurrencyCache() {
        String splitChar = "\\$";
        int arrayIndex = 0;
        List<String> currencies = currencyCache.getCurrencyList();

        for (String sourceCurrency : currencies) {
            for (String targetCurrency : currencies) {
//                String splitSourceCurr = sourceCurrency.split(splitChar)[arrayIndex].trim();
//                String splitTargetCurr = targetCurrency.split(splitChar)[arrayIndex].trim();
                String appendedCurrency = sourceCurrency + targetCurrency;

                Double value = fetchSingleCurrency(appendedCurrency);
                currencyExchange.put(appendedCurrency, value);
            }
        }
    }

    private static double fetchSingleCurrency(String currency) {
        String req = "http://download.finance.yahoo.com/d/quotes.csv?s=" +currency.toUpperCase() +"=X&f=l1";
        String exchangeValue = "";

        try {
            URL url = new URL(req);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            exchangeValue = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Double.valueOf(exchangeValue);
    }

    @Override
    public double exchangeRate(String sourceCurrency, String targetCurrency, Double amount) {
        String appendedCurrency = sourceCurrency + targetCurrency;

        if (currencyExchange.containsKey(appendedCurrency))
            return currencyExchange.get(appendedCurrency) * amount;
        else {
            double value = fetchSingleCurrency(appendedCurrency);
            currencyExchange.put(appendedCurrency, value);
            return value * amount;
        }
    }

    @Override
    public double exchangeRate(String sourceCurrency, String targetCurrency) {
        return exchangeRate(sourceCurrency, targetCurrency, 1d);
    }
}