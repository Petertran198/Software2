package c195.controller;

import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Country;
import c195.model.Customer;
import c195.model.FLDivision;
import c195.utilities.SwitchRoute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
    //FXML fields
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalTextField;
    @FXML private TextField phoneTextField;
    @FXML private  TextField customerIDField;
    //FXML ComboBoxes
    @FXML private ComboBox<Country> countryDropDown;
    @FXML private ComboBox<FLDivision> FLDDropDown;

    //Variable for Customer Selected from Home Controller
    private Customer customer;
    //Variable for the country_ID selected to filter FLDcombo box
    private int country_id;

    private ObservableList<Country> countriesList =
            FXCollections.observableArrayList();
    private ObservableList<FLDivision> FLDList =
            FXCollections.observableArrayList();

    // errors
    @FXML private Label errors;
    String errorListString = "";

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
     * object.
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
        FLDDropDown.setItems(customerDAO.getFLDDivisionList().filtered(fld -> fld.getCountry_ID() == country_id));
    }
    public void selectCountryToFilterFLD(ActionEvent event) throws Exception{
        //If a country is selected from dropdown
        if(countryDropDown.getSelectionModel().getSelectedItem() != null){
            int country_id =
                    countryDropDown.getSelectionModel().getSelectedItem().getCountry_ID();
            FLDDropDown.getSelectionModel().clearSelection();
// Clear value of ComboBox because clearSelection() does not do it
            FLDDropDown.setValue(null);

            //Instead of hitting the database and querying for all FLD's
            // WHERE country_id = "insert-country-id" I used a lambda to filter
            // the flds after I get the country via selection of combo box
            ObservableList<FLDivision> selectedCountryFLDList =
                    FLDList.filtered(fld -> fld.getCountry_ID() == country_id);
            FLDDropDown.setItems(selectedCountryFLDList);
        }
    }

    public  String handleFormEmptyField(String name, String address,
                                        String phone, String postal
    ) {
        String errorMsg= "";
        if(name.equals("")){
            errorMsg = errorMsg + "-Name field can't be empty";
        }
        if(address.equals("")){
            errorMsg = errorMsg + "\n-Address field can't be empty";
        }
        if(phone.equals("")){
            errorMsg = errorMsg + "\n-Phone field can't be empty";
        }
        if(postal.equals("")){
            errorMsg = errorMsg + "\n-Postal field can't be empty";
        }

        return errorMsg;
    }

    public void saveCustomer(ActionEvent event) throws Exception{
        String name = "";
        String address = "";
        String phone = "";
        String postal = "";
        try{
            name = nameTextField.getText();
            address = addressTextField.getText();
            phone = phoneTextField.getText();
            postal = postalTextField.getText();
            FLDivision div =
                    FLDDropDown.getSelectionModel().getSelectedItem();
            int divID = div.getDivision_ID();
            Customer customer = new Customer(name, address, postal,phone,
                    divID);
            customerDAO.addCustomer(customer);
            SwitchRoute.switchToHome(event);
        }catch(NullPointerException e ){
            errorListString = handleFormEmptyField(name,address,phone,postal);
            errorListString = errorListString + "\n-Please make sure all " +
                    "combo " +
                    "boxes are filled";
            errors.setText(errorListString);
        }
    }
    /** This method when click will switch to the home.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToHome(ActionEvent event) throws Exception {
        SwitchRoute.switchToHome(event);
    }



    public void populateForm(){
        nameTextField.setText(customer.getCustomer_Name());
        addressTextField.setText(customer.getAddress());
        phoneTextField.setText(customer.getPhone());
        postalTextField.setText(customer.getPostal_code());
        customerIDField.setText(Integer.toString(customer.getCustomer_ID()));
        countriesList = customerDAO.getCountryList();

        for(Country country: countriesList){
            if(country.getCountryName().equals(customer.getCountry_name())){
                countryDropDown.getSelectionModel().select(country);
                country_id = country.getCountry_ID();
            }
        }

        FLDList =
                customerDAO.getFLDDivisionList();
        for(FLDivision fld: FLDList){
            if(fld.getDivision_Name().equals(customer.getDivision())){
                FLDDropDown.getSelectionModel().select(fld);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = HomeController.selectedCustomerToModify;
        populateForm();
        getCountryDataForComboBox();
        getDivisionDataForComboBox();

    }
}
