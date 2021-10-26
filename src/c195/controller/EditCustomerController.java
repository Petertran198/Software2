package c195.controller;

import c195.utilities.SwitchRoute;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
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

    }
}
