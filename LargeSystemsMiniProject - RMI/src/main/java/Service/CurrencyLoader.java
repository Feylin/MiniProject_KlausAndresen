package Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by prep on 24-02-2015.
 */
public enum CurrencyLoader {
    INSTANCE;

    private List<String> currencyList;

    CurrencyLoader() {
        Charset defaultCharset = Charset.defaultCharset();
        // Path to the file containing the different currencies
        URL url = getClass().getResource("/Currency");
        try {
            Path currencyPath = Paths.get(url.toURI());
            currencyList = Files.readAllLines(currencyPath, defaultCharset);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }
}
