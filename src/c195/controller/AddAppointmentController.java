package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Contact;
import c195.model.Country;
import c195.model.Customer;
import c195.model.User;
import c195.utilities.SwitchRoute;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    //ComboBox variables
    @FXML private ComboBox<Customer> customerCombo;
    @FXML private  ComboBox<User> userCombo;
    @FXML private  ComboBox<Contact> contactCombo;



    //appointmentDao is used for accessing and saving appointments data.
    AppointmentDaoInterface appointmentDAO = new AppointmentDaoImplementation();

    public void getCustomerDataForComboBox(){
        Callback<ListView<Customer>, ListCell<Customer>> cellFactory =
                new Callback<>() {
                    @Override
                    public ListCell<Customer> call(ListView<Customer> l) {
                        return new ListCell<Customer>() {
                            @Override
                            protected void updateItem(Customer customer,
                                                      boolean empty) {
                                super.updateItem(customer, empty);
                                if (customer == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(customer.getCustomer_Name());
                                }
                            }
                        };
                    }
                };
        customerCombo.setButtonCell(cellFactory.call(null));
        customerCombo.setCellFactory(cellFactory);
        customerCombo.setItems(appointmentDAO.getAllCustomers());
    }

    public void getContactDataForComboBox(){
        Callback<ListView<Contact>, ListCell<Contact>> cellFactory =
                new Callback<>() {
                    @Override
                    public ListCell<Contact> call(ListView<Contact> l) {
                        return new ListCell<Contact>() {
                            @Override
                            protected void updateItem(Contact contact,
                                                      boolean empty) {
                                super.updateItem(contact, empty);
                                if (contact == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(contact.getContact_Name());
                                }
                            }
                        };
                    }
                };
        contactCombo.setButtonCell(cellFactory.call(null));
        contactCombo.setCellFactory(cellFactory);
        contactCombo.setItems(appointmentDAO.getAllContacts());
    }

    public void getUserDataForComboBox(){
        Callback<ListView<User>, ListCell<User>> cellFactory =
                new Callback<>() {
                    @Override
                    public ListCell<User> call(ListView<User> l) {
                        return new ListCell<User>() {
                            @Override
                            protected void updateItem(User user,
                                                      boolean empty) {
                                super.updateItem(user, empty);
                                if (user == null || empty) {
                                    setGraphic(null);
                                } else {
                                    setText(user.getUser_Name());
                                }
                            }
                        };
                    }
                };
        userCombo.setButtonCell(cellFactory.call(null));
        userCombo.setCellFactory(cellFactory);
        userCombo.setItems(appointmentDAO.getAllUsers());
    }

    /** This method when click will switch to the home.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToHome(ActionEvent event) throws Exception {
        SwitchRoute.switchToHome(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerDataForComboBox();
        getContactDataForComboBox();
        getUserDataForComboBox();
    }
}