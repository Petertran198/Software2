package c195.controller;

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    /** This method when click will switch to the customerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToCustomerInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/customerInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToAppointmentInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/appointmentInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /** This method when click will switch to the contactAppointmentReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToContactAppointmentReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/contactAppointmentReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the customerMonthAndTypeReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToCustomerMonthAndTypeReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/customerMonthAndTypeReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToLocationReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/locationReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
