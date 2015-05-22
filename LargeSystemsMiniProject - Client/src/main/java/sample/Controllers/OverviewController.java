package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;
import sample.Model.Country;
import sample.Service.ServiceConnector;
import sample.Service.ServiceConnectorImpl;

public class OverviewController {
    @FXML private TextArea txtDescription;
    @FXML private TableView<Country> tblCountries;
    @FXML private TableColumn<Country, String> tblNameColumn;
    @FXML private TableColumn<Country, String> tblCurrencyColumn;
    @FXML private Label lblName;
    @FXML private Label lblCapital;
    @FXML private Label lblRegion;
    @FXML private Label lblPopulation;
    @FXML private Label lblCurrency;
    @FXML private Label lblAlpha2;
    @FXML private Label lblAlpha3;
    @FXML private Label lblTimezone;
    @FXML private ImageView countryImg;
    private ServiceConnector service = ServiceConnectorImpl.INSTANCE;
    private StringProperty nameProperty = new SimpleStringProperty();
    private StringProperty currencyProperty = new SimpleStringProperty();

    private Main mainApplication;

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApplication main stage.
     */
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;

        // Add observable list data to the table
        tblCountries.setItems(mainApplication.getCountryData());
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public OverviewController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the champion table with the two columns.
        tblNameColumn.setCellValueFactory(cellData -> {
            nameProperty.setValue(cellData.getValue().getName());
            return nameProperty;
        });
        tblCurrencyColumn.setCellValueFactory(cellData -> {
            currencyProperty.setValue(cellData.getValue().getCurrency());
            return currencyProperty;
        });

        // Listen for selection changes and show the country details when changed.
        tblCountries.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        showCountryDetails(service.getCountry(newValue.getName()));
                    } catch (NullPointerException ignored) {
                        showCountryDetails(null);
                    }
                });
    }

    private void showCountryDetails(Country country) {
        if (country != null) {
            // Fill the labels with info from the champion object.
            lblName.setText(country.getName());
            lblAlpha2.setText(country.getAlpha2Code());
            lblAlpha3.setText(country.getAlpha3Code());
            lblCapital.setText(country.getCapital());
            lblCurrency.setText(country.getCurrency());
            lblPopulation.setText(country.getPopulation());
            lblRegion.setText(country.getRegion());
            lblTimezone.setText(country.getTimezone());
            txtDescription.setText(country.getDescription());
            countryImg.setImage(new Image(country.getImage()));
        } else {
            // Country is null, remove all the text.
            lblName.setText("");
            lblAlpha2.setText("");
            lblAlpha3.setText("");
            lblCapital.setText("");
            lblCurrency.setText("");
            lblPopulation.setText("");
            lblRegion.setText("");
            lblTimezone.setText("");
            txtDescription.setText("");
            countryImg.setImage(null);
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void btnDeleteCountry() {
        int tableIndex = tblCountries.getSelectionModel().getSelectedIndex();
        Country selectedCountry = tblCountries.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            service.deleteCountry(selectedCountry.getName());
            tblCountries.getItems().remove(tableIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApplication.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Country Selected");
            alert.setContentText("Please select a Country in the table.");

            alert.showAndWait();
        }
        tblCountries.requestFocus();
        tblCountries.getSelectionModel().select(0);
        tblCountries.getFocusModel().focus(0);
    }

    /**
     * Called when the user clicks the new button.
     * Opens a dialog to edit details for a new country.
     */
    @FXML
    private void btnNewClicked() {
        Country tempCountry = new Country();
        boolean okClicked = mainApplication.showChampionEditDialog(tempCountry);
        if (okClicked) {
            service.saveCountry(tempCountry);
            mainApplication.getCountryData().add(tempCountry);
            FXCollections.sort(mainApplication.getCountryData());
        }
    }

    /**
     * Called when the user clicks the edit button.
     * Opens a dialog to edit details for the selected country.
     */
    @FXML
    private void btnEditClicked() {

    }
}
