package Model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 17-05-2015.
 */
@Entity
@Table(name = "Countries")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;
    private String currency;
    private String alpha2Code;
    private String alpha3Code;
    @Transient
    private String description;
    @Transient
    private String region;
    @Transient
    private String capital;
    @Transient
    private String timezone;
    @Transient
    private String population;
    @Transient
    private String image;
    @Transient
    private HashMap<String, Double> currencies;
    @Transient
    private String latlng;

    public Country() {

    }

    public Country(String name, String currency, String alpha2Code) {
        this.name = name;
        this.currency = currency;
        this.alpha2Code = alpha2Code;
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

    public String getRegion() {
        return region;
    }

    public Country setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getCapital() {
        return capital;
    }

    public Country setCapital(String capital) {
        this.capital = capital;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Country setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getPopulation() {
        return population;
    }

    public Country setPopulation(String population) {
        this.population = population;
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

    public String getLatlng() {
        return latlng;
    }

    public Country setLatlng(String latlng) {
        this.latlng = latlng;
        return this;
    }
}
