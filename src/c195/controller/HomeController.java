package c195.controller;

import c195.utilities.SwitchRoute;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    /** This method when click will switch to the customerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToCustomerInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToCustomerInfoForm(event);
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToAppointmentInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToAppointmentInfoForm(event);
    }


    /** This method when click will switch to the contactAppointmentReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToContactAppointmentReport(ActionEvent event) throws Exception {
        SwitchRoute.switchToContactAppointmentReport(event);
    }

    /** This method when click will switch to the customerMonthAndTypeReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToCustomerMonthAndTypeReport(ActionEvent event) throws Exception {
        SwitchRoute.switchToCustomerMonthAndTypeReport(event);
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToLocationReport(ActionEvent event) throws Exception {
        SwitchRoute.switchToLocationReport(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
