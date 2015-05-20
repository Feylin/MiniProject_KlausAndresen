package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.web.client.RestTemplate;
import sample.Main;
import sample.Model.Country;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class OverviewController {
    @FXML private TextField txtName;
    @FXML private Label lblError;
    @FXML private Label lblAlpha2;
    @FXML private Label lblAlpha3;
    @FXML private Label lblCurrency;
    private HashMap<String, String[]> countryMap = new HashMap<>();

    private Main mainApplication;

    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public OverviewController() {
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("en").setRegion(countryCode).build();
            String countryName = obj.getDisplayCountry(Locale.ENGLISH);
            String countryAlpha2 = obj.getCountry();
            String countryAlpha3 = obj.getISO3Country();
            Currency countryCurrency = Currency.getInstance(obj);

            countryMap.put(countryName, new String[]{countryAlpha2, countryAlpha3, String.valueOf(countryCurrency)});
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // <<<-----TEST---->>>
        RestTemplate restTemplate = new RestTemplate();
//        Country country = restTemplate.getForObject("http://localhost:8080/country/1", Country.class);

        Country country = new Country().setName("ds").setAlpha2Code("ds").setCurrency("DS");

        restTemplate.postForObject("http://localhost:8080/addCountry/country", country, Country.class);

//        lblAlpha2.setText("Hentet fra min rest server " +country.getName());
    }

    public void handleInput(KeyEvent event) {
        String s = txtName.getText().trim();

        try {
            if (event.getCode() == KeyCode.BACK_SPACE)
                s = s.substring(0, s.length() - 1);
            else s += event.getText();
            if (countryMap.keySet().contains(s)) {
                lblError.setText("Valid");
                lblAlpha2.setText(countryMap.get(s)[0]); // Alpha2
                lblAlpha3.setText(countryMap.get(s)[1]); // Alpha3
                lblCurrency.setText(countryMap.get(s)[2]); // Currency
            }
            else {
                lblError.setText("Invalid");
                lblAlpha2.setText("");
                lblAlpha3.setText("");
                lblCurrency.setText("");
            }
        } catch (StringIndexOutOfBoundsException ignored) {

        }
    }
}
