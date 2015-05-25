package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.web.client.ResourceAccessException;
import sample.Controllers.CountryEditDialogController;
import sample.Controllers.MapDialogController;
import sample.Controllers.OverviewController;
import sample.Model.Country;
import sample.Service.ServiceConnector;
import sample.Service.ServiceConnectorImpl;

import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class Main extends Application {

    private ServiceConnector service = ServiceConnectorImpl.INSTANCE;
    private Stage primaryStage;
    private HashMap<String, String[]> countryMap = new HashMap<>();

    /**
     * The data as an observable list of countries.
     */
    private ObservableList<Country> countryData = FXCollections.observableArrayList();

    public Main() {
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {
            Locale obj = new Locale.Builder().setLanguage("en").setRegion(countryCode).build();
            String countryName = obj.getDisplayCountry(Locale.ENGLISH);
            String countryAlpha2 = obj.getCountry();
            String countryAlpha3 = obj.getISO3Country();
            Currency countryCurrency = Currency.getInstance(obj);

            countryMap.put(countryName, new String[]{ countryAlpha2, countryAlpha3, String.valueOf(countryCurrency)});
        }
        countryMap.remove("Antarctica"); // no currency and or rest data
        try {
            countryData.addAll(service.getAllCountries());
        } catch (ResourceAccessException ignored) {}
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Large Systems Client");

        showMainStage();
    }

    private void showMainStage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LargeSystemsOverview.fxml"));
            AnchorPane overview = loader.load();

            Scene scene = new Scene(overview);
            primaryStage.setScene(scene);

            OverviewController controller = loader.getController();
            controller.setMainApplication(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMapDialog(String countryLocation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MapDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Map");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            MapDialogController controller = loader.getController();
            dialogStage.setScene(new Scene(controller.getMapView()));
            controller.setMainApplication(this);
            controller.setDialogStage(dialogStage);
            controller.setCountry(countryLocation);

            // Show the dialog and wait until the user closes it.
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified country.
     * If the user clicks OK, the changes are saved into the provided champion object and true is returned.
     * @param country the champion object to be edited.
     * @return true is the user clicked OK, false otherwise.
     */
    public boolean showCountryEditDialog(Country country) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CountryEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Country");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the country into the controller
            CountryEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCountry(country);
            controller.setMainApplication(this);

            // Show the dialog and wait until the user closes it.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap<String, String[]> getCountryMap() {
        return countryMap;
    }

    public ObservableList<Country> getCountryData() {
        FXCollections.sort(countryData);
        return countryData;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
