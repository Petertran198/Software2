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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to handle AddCustomer
 */
public class AddCustomerController implements Initializable {

    //FXML fields
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalTextField;
    @FXML private TextField phoneTextField;

    //FXML ComboBoxes
    @FXML private ComboBox<Country> countryDropDown;
    @FXML private ComboBox<FLDivision> FLDDropDown;

    // errors
    @FXML private Label errors;
    String errorListString = "";
    private  ObservableList<FLDivision> allFlds;

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
     *Use LAMBDA to filters for FLD(aka region)  after selecting a country from
     * combo box
     */
    public void selectCountryToFilterFLD(ActionEvent event) throws Exception{
        //If a country is selected from dropdown
        if(countryDropDown.getSelectionModel().getSelectedItem() != null){
            int country_id =
                    countryDropDown.getSelectionModel().getSelectedItem().getCountry_ID();
            //Instead of hitting the database and querying for all FLD's
            // WHERE country_id = "insert-country-id" I used a lambda to filter
            // the flds after I get the country via selection of combo box
            // This makes the code cleaner and more readable
            ObservableList<FLDivision> selectedCountryFLDList =
                    allFlds.filtered(fld -> fld.getCountry_ID() == country_id);
            FLDDropDown.setItems(selectedCountryFLDList);
        }
    }


    /**
     * Check if addCustomer form had any empty fields that is not suppose to be empty
     * @param name
     * @param address
     * @param phone
     * @param postal
     * @return returns the error message if there is a field that is empty that
     * isn't suppose to be
     */
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


    /**
     * Save Customer
     * @param event
     * @throws Exception
     */
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCountryDataForComboBox();
        //Get all fld's then filtered later using lambda expression
        allFlds = customerDAO.getFLDDivisionList();

    }
}
