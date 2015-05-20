package Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.util.Locale;

/**
 * Created by Administrator on 17-05-2015.
 */
@ComponentScan({"Controllers", "Service", "DAO"})
//@SpringBootApplication
@ImportResource("classpath:spring/applicationContext.xml")
//@Configuration
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
//        String[] locales = Locale.getISOCountries();
//
//        for (String countryCode : locales) {
//
//            Locale obj = new Locale("", countryCode);
//
//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry());
//
//        }
//
//        System.out.println(locales.length);

        SpringApplication.run(Application.class, args);
    }
}
