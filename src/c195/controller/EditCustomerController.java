package c195.controller;

import c195.model.Customer;
import c195.utilities.SwitchRoute;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
    //FXML fields
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalTextField;
    @FXML private TextField phoneTextField;
    //Variable for Customer Selected from Home Controller
    private Customer customer;

    public void saveCustomer(){

    }

    /** This method when click will switch to the home.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToHome(ActionEvent event) throws Exception {
        SwitchRoute.switchToHome(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = HomeController.selectedCustomerToModify;
        nameTextField.setText(customer.getCustomer_Name());
        addressTextField.setText(customer.getAddress());
        phoneTextField.setText(customer.getPhone());
        postalTextField.setText(customer.getPostal_code());
    }
}
