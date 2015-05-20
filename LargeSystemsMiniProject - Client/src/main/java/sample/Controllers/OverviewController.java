package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import sample.Main;
import sample.Model.Country;
import sample.Service.ServiceConnector;
import sample.Service.ServiceConnectorImpl;

public class OverviewController {
    @FXML private TextArea txtDescription;
    @FXML private TableView<Country> tblCountries;
    @FXML private TableColumn<Country, String> tblNameColumn;
    @FXML private TableColumn<Country, String> tblCurrencyColumn;
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
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void btnDeleteCountry() {
        int tableIndex = tblCountries.getSelectionModel().getSelectedIndex();
        int selectedCountryIndex = tblCountries.getSelectionModel().getSelectedItem().getId();
        if (selectedCountryIndex >= 0) {
            service.deleteCountry(selectedCountryIndex);
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
