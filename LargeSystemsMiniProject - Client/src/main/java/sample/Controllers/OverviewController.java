package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Model.Country;
import sample.Service.ServiceConnector;
import sample.Service.ServiceConnectorImpl;

import java.text.DecimalFormat;
import java.util.Map;

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
    @FXML private BarChart<String, Double> chartCurrencies;
    @FXML private Hyperlink lnkLocation;
    @FXML private AnchorPane rootPane;
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
        // Initialize the country table with the two columns.
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

    private void populateChart(Country country) {
        chartCurrencies.getData().clear();

        if (country.getCurrencies() != null) {
            chartCurrencies.getXAxis().setLabel("Currencies");

            chartCurrencies.getYAxis().setLabel("X per 1 " + country.getCurrency());

            XYChart.Series series = new XYChart.Series();

            for (Map.Entry<String, Double> entry : country.getCurrencies().entrySet())
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));

            chartCurrencies.getData().addAll(series);

            final Label lblResult = new Label();
            rootPane.getChildren().add(lblResult);

            for (XYChart.Series<String, Double> doubleSeries: chartCurrencies.getData()){
                for (XYChart.Data<String, Double> item: doubleSeries.getData()){
                    item.getNode().setOnMousePressed((MouseEvent event) -> {
                        lblResult.setTranslateX(event.getSceneX());
                        lblResult.setTranslateY(event.getSceneY());
                        lblResult.setText(String.format("%s - %s", item.getXValue(), item.getYValue()));
                    });
                }
            }
        }
    }

    private void showCountryDetails(Country country) {
        if (country != null) {
            // Fill the labels with info from the country object.
            lblName.setText(country.getName());
            lblAlpha2.setText(country.getAlpha2Code());
            lblAlpha3.setText(country.getAlpha3Code());
            lblCapital.setText(country.getCapital());
            lblCurrency.setText(country.getCurrency());
            lblPopulation.setText(thousandSeparator(country.getPopulation()));
            lblRegion.setText(country.getRegion());
            lblTimezone.setText(country.getTimezone());
            txtDescription.setText(country.getDescription());
            countryImg.setImage(new Image(country.getImage()));
            populateChart(country);
            lnkLocation.setText(country.getLatlng().replace("[", "").replace("]", ""));
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
            chartCurrencies.getData().clear();
            lnkLocation.setText("");
        }
    }

    private String thousandSeparator(String source) {
        int value = Integer.valueOf(source);
        DecimalFormat myFormatter = new DecimalFormat("#,###");
        return myFormatter.format(value);
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
            showNoSelectionError();
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
        boolean okClicked = mainApplication.showCountryEditDialog(tempCountry);
        if (okClicked) {
            service.saveCountry(tempCountry);
            mainApplication.getCountryData().add(tempCountry);
            FXCollections.sort(mainApplication.getCountryData());
        }
    }

    /**
     * Called when the user clicks the update button.
     * Opens a dialog to edit details for the selected country.
     */
    @FXML
    private void btnUpdateClicked() {
        Country selectedCountry = tblCountries.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            chartCurrencies.getData().clear();
            selectedCountry.setCurrencies(service.updateCountry(selectedCountry.getName()).getCurrencies());
            populateChart(selectedCountry);
        } else {
            showNoSelectionError();
        }
    }

    @FXML
    private void lnkCountryLocationClickec() {
        Country selectedCountry = tblCountries.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            mainApplication.showMapDialog(lnkLocation.getText());
        } else {
            showNoSelectionError();
        }
    }

    private void showNoSelectionError() {
        // Nothing selected.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApplication.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Country Selected");
        alert.setContentText("Please select a Country in the table.");

        alert.showAndWait();
    }
}
