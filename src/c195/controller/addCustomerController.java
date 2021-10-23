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


    public void getCountryDataForComboBox(){
        Callback<ListView<Country>, ListCell<Country>> country_cell_factory = new Callback<>() {
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
        countryDropDown.setButtonCell(country_cell_factory.call(null));
        countryDropDown.setCellFactory(country_cell_factory);
        countryDropDown.setItems(customerDAO.getCountryList());
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
    }
}
