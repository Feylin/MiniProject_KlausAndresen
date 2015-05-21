package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Country;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Administrator on 20-05-2015.
 */
public class CountryEditDialogController {
    @FXML private TextField txtName;
    @FXML private TextField txtAlpha2;
    @FXML private TextField txtAlpha3;
    @FXML private TextField txtCurrency;

    private Stage dialogStage;
    private Country country;
    private boolean okClicked = false;
    private boolean isInputValid = false;
    private Main mainApplication;
    private HashMap<String, String[]> countryMap;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApplication main stage.
     */
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
        this.countryMap = mainApplication.getCountryMap();
    }

    /**
     * Sets the country to be edited in the dialog
     * @param country the country object
     */
    public void setCountry(Country country) {
        this.country = country;

        txtName.setText(country.getName());
        txtAlpha2.setText(country.getAlpha2Code());
        txtAlpha3.setText(country.getAlpha3Code());
        txtCurrency.setText(country.getCurrency());
    }

    /**
     * Method to check if ok has been clicked
     * @return true if the user clicked OK, false otherwise
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void btnOkClicked() {
        if (mainApplication.getCountryData().stream().anyMatch(c -> c.getName().equals(txtName.getText().trim()))) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApplication.getPrimaryStage());
            alert.setTitle("Already exists");
            alert.setHeaderText("Country already exists.");
            alert.setContentText("Please enter a country that does not exist.");

            alert.showAndWait();
        } else if (isInputValid) {
            country.setName(txtName.getText().trim());
            country.setAlpha2Code(txtAlpha2.getText().trim());
            country.setAlpha3Code(txtAlpha3.getText().trim());
            country.setCurrency(txtCurrency.getText().trim());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel
     */
    @FXML
    private void btnCancelClicked() {
        dialogStage.close();
    }

    @FXML
    private void handleCountryNameInput(KeyEvent event) {
        String s = txtName.getText();

        try {
            if (event.getCode() == KeyCode.BACK_SPACE)
                s = s.substring(0, s.length() - 1);
            else s += event.getText();
            if (countryMap.keySet().contains(s)) {
                isInputValid = true;
                txtAlpha2.setText(countryMap.get(s)[0]); // Alpha2
                txtAlpha3.setText(countryMap.get(s)[1]); // Alpha3
                txtCurrency.setText(countryMap.get(s)[2]); // Currency
                removeRed(txtAlpha2);
                removeRed(txtAlpha3);
                removeRed(txtCurrency);
            }
            else {
                txtAlpha2.setText("");
                txtAlpha3.setText("");
                txtCurrency.setText("");
                setRed(txtAlpha2);
                setRed(txtAlpha3);
                setRed(txtCurrency);
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException ignored) {

        }
    }

    private void setRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();

        if(!styleClass.contains("tferror")) {
            styleClass.add("tferror");
        }
    }


    private void removeRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("tferror"));
    }
}
