package sample.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

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

    @Override
    public int compareTo(Country country) {
        String sourceName = name;
        String targetName = country.name;

        return sourceName.compareToIgnoreCase(targetName);
    }
}
