package Server;

import Shared.RegistryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

//        SpringApplication.run(Application.class, args);

        try {
            // fire to localhost port 1099
            Registry myRegistry = LocateRegistry.getRegistry(RegistryConfig.REGISTRY_PORT);

            // search for myMessage service
            RmiServer impl = (RmiServer) myRegistry.lookup(RegistryConfig.INSTANCE_NAME);

            // call server's method
            System.out.println(impl.exchangeRate("EUR", "USD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
