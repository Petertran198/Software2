package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Appointment;
import c195.model.Customer;
import c195.utilities.SwitchRoute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    //Customer Table
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIDColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerStateColumn;
    @FXML private TableColumn<Customer, String> customerCountryColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;


    //Appointment Table
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, String> appointmentStartColumn;
    @FXML private TableColumn<Appointment, String> appointmentDueDateColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIDColumn;

    //variables
    //Using the DAO pattern, customerDao will be used to query  the customer db
    CustomerDaoInterface customerDao = new CustomerDaoImplementation();
    AppointmentDaoInterface appointmentDao = new AppointmentDaoImplementation();




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

    public void showCustomerTable(){
        ObservableList<Customer> customerList;
        customerList = customerDao.getAllCustomer();
        //Populate each columns with the data that belongs to that specific
        // property. Example Customer got a customer_ID property.
        //The PropertyValueFactory method requires u to use java default
        // getter and setter methods for this to work.
        //The reason customer_ID works is because we used Java default
        // getCustomer_ID method
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "customer_ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "customer_Name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>(
                "address"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>(
                "phone"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>(
                "country_name"));
        customerStateColumn.setCellValueFactory(new PropertyValueFactory<>(
                "division"));
        customerTable.setItems(customerList);
    }

    public void showAppointmentTable(){
        ObservableList<Appointment> appointmentList;
        appointmentList = appointmentDao.getAllAppointment();
        appointmentTable.setItems(appointmentList);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "customer_ID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>(
                "title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>(
                "description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>(
                "location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "type"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>(
                "contact_name"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "user_ID"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "contact_ID"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>(
                "start"));
        appointmentDueDateColumn.setCellValueFactory(new PropertyValueFactory<>(
                "end"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCustomerTable();
        showAppointmentTable();
    }
}
