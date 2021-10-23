package c195.controller;

import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Country;
import c195.model.FLDivision;
import c195.utilities.SwitchRoute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;


import java.net.URL;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    //FXML fields
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalTextField;
    @FXML private TextField phoneTextField;

    //FXML ComboBoxes
    @FXML private ComboBox<Country> countryDropDown;
    @FXML private ComboBox<FLDivision> FLDDropDown;

    // labels
    @FXML private Label errors;
    //Buttons
    @FXML private Button saveButton;

    //customerDao is used for accessing and saving customer data.
    CustomerDaoInterface customerDAO = new CustomerDaoImplementation();

    /**
     * This method gets the country's data and populate it into the
     * comboBox. The country.getCountryList will return an observable list
     * filled with COUNTY objects. Therefore if we set the comboBox to just
     * that it will display objects instead of the actual country's name
     * We used a callback to change the display of the comboBox from just
     * objects(ugly) to their actual name. We used a callback instead of making
     * an array of the country's name and display it because we actually need
     * to get the country OBJECT later to save the country_ID
     */
    public void getCountryDataForComboBox(){
        Callback<ListView<Country>, ListCell<Country>> cellFactory =
                new Callback<>() {
            @Override
            public ListCell<Country> call(ListView<Country> l) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Country country, boolean empty) {
                        super.updateItem(country, empty);
                        if (country == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(country.getCountryName());
                        }
                    }
                };
            }
        };
        countryDropDown.setButtonCell(cellFactory.call(null));
        countryDropDown.setCellFactory(cellFactory);
        countryDropDown.setItems(customerDAO.getCountryList());
    }

    /**
     * Same implementation and reasoning as getCountryDataForComboBox()
     * The method will display the Division aka (state) but hold the FLDDivision
     * object
     */
    public void getDivisionDataForComboBox(){
        Callback<ListView<FLDivision>, ListCell<FLDivision>> cellFactory =
                new Callback<>() {
                    @Override
                    public ListCell<FLDivision> call(ListView<FLDivision> l) {
                        return new ListCell<>() {
                            @Override
                            protected void updateItem(FLDivision division,
                                                      boolean empty) {
                                super.updateItem(division, empty);
                                if (division == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(division.getDivision_Name());
                                }
                            }
                        };
                    }
                };
        FLDDropDown.setButtonCell(cellFactory.call(null));
        FLDDropDown.setCellFactory(cellFactory);
        FLDDropDown.setItems(customerDAO.getFLDDivisionList());
    }
    /** This method when click will switch to the home.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToHome(ActionEvent event) throws Exception {
        SwitchRoute.switchToHome(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCountryDataForComboBox();
        getDivisionDataForComboBox();
    }
}
