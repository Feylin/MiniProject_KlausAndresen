package sample.Controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import sample.Main;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 24-05-2015.
 */
public class MapDialogController implements MapComponentInitializedListener {
    private Main mainApplication;
    private Stage dialogStage;
    private GoogleMapView mapView;
    private String countryLocation;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        //Create the JavaFX component and set this as a listener so we know when
        //the map has been initialized, at which point we can then begin manipulating it.
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApplication main stage.
     */
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCountry(String countryLocation) {
        this.countryLocation = countryLocation;
    }

    @Override
    public void mapInitialized() {
        List<String> locationList = Arrays.asList(countryLocation.replace("[", "").replace("]", "").split(","));
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(Double.valueOf(locationList.get(0)), Double.valueOf(locationList.get(1))))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(7);

        GoogleMap map = mapView.createMap(mapOptions);

        //Add a marker to the map
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position( new LatLong(Double.valueOf(locationList.get(0)), Double.valueOf(locationList.get(1))) )
                .visible(Boolean.TRUE)
                .title("My Marker");

        Marker marker = new Marker( markerOptions );

        map.addMarker(marker);
    }

    public Parent getMapView() {
        return mapView;
    }
}
