package sample.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 19-05-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Comparable<Country>, Serializable {
    private int id;
    private String name;
    private String currency;
    private String alpha2Code;
    private String alpha3Code;
    private String description;
    private String capital;
    private String region;
    private String population;
    private String timezone;
    private String image;
    private HashMap<String, Double> currencies;

    public Country() {

    }

    public Country(String name, String currency, String alpha2Code, String alpha3Code) {
        this.name = name;
        this.currency = currency;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Country setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public Country setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
        return this;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public Country setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Country setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCapital() {
        return capital;
    }

    public Country setCapital(String capital) {
        this.capital = capital;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Country setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getPopulation() {
        return population;
    }

    public Country setPopulation(String population) {
        this.population = population;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Country setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Country setImage(String image) {
        this.image = image;
        return this;
    }

    public HashMap<String, Double> getCurrencies() {
        return currencies;
    }

    public Country setCurrencies(HashMap<String, Double> currencies) {
        this.currencies = currencies;
        return this;
    }

    @Override
    public int compareTo(Country country) {
        String sourceName = name;
        String targetName = country.name;

        return sourceName.compareToIgnoreCase(targetName);
    }
}
