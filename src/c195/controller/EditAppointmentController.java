package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Appointment;
import c195.model.Contact;
import c195.model.Customer;
import c195.model.User;
import c195.utilities.DateTimeHelper;
import c195.utilities.SwitchRoute;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAppointmentController implements Initializable {
    //ComboBox variables
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML private  ComboBox<User> userCombo;
    @FXML private  ComboBox<Contact> contactCombo;
    //Textfields
    @FXML private TextField titleField;
    @FXML private TextField typeField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;
    //Time components
    @FXML private DatePicker startDatePicker;
    @FXML private  DatePicker endDatePicker;
    @FXML private Spinner<Integer> startHourSpinner;
    @FXML private  Spinner<Integer> startMinutesSpinner;
    @FXML private  Spinner<Integer> endHourSpinner;
    @FXML private  Spinner<Integer> endMinutesSpinner;
    //Time components Toggle Group
    @FXML private ToggleGroup startTimeGroup;
    @FXML private ToggleGroup endTimeGroup;
    //Time components Toggle Radio Button
    @FXML RadioButton startTimeAMRadioButton;
    @FXML RadioButton startTimePMRadioButton;
    @FXML RadioButton endTimeAMRadioButton;
    @FXML RadioButton endTimePMRadioButton;
    //This is needed to populate appointment data to update
    private static Appointment appointment;
    //appointmentDao is used for accessing and saving appointments data.
    AppointmentDaoInterface appointmentDAO = new AppointmentDaoImplementation();
    //CustomerDAO is used for accessing customer data.
    CustomerDaoInterface customerDao = new CustomerDaoImplementation();

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

    public  void getSelectedComboBoxData(){
        ObservableList<Customer> customerList = customerDao.getAllCustomer();
        customerList.forEach(customer -> {
            if(customer.getCustomer_ID() == appointment.getCustomer_ID()){
                customerCombo.getSelectionModel().select(customer);
                return;
            }
        });
        ObservableList<Contact> contactList = appointmentDAO.getAllContacts();
        contactList.forEach(contact -> {
            if(contact.getContact_ID() == appointment.getContact_ID()){
                contactCombo.getSelectionModel().select(contact);
                return;
            }
        });

        ObservableList<User> userList = appointmentDAO.getAllUsers();
        userList.forEach(user -> {
            if(user.getUser_ID() == appointment.getUser_ID()){
                userCombo.getSelectionModel().select(user);
                return;
            }
        });
    }

    public void initTimeSpinnersWithValues(){
        SpinnerValueFactory<Integer> valueFactoryHoursStart =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12);
        startHourSpinner.setValueFactory(valueFactoryHoursStart);
        valueFactoryHoursStart.setValue(DateTimeHelper.returnHourIn12HourFormat(appointment.getStart()));

        SpinnerValueFactory<Integer> valueFactoryHoursEnd =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12);
        endHourSpinner.setValueFactory(valueFactoryHoursEnd);
        valueFactoryHoursEnd.setValue(DateTimeHelper.returnHourIn12HourFormat(appointment.getEnd()));

        SpinnerValueFactory<Integer> valueFactoryMinsStart =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60);
        startMinutesSpinner.setValueFactory(valueFactoryMinsStart);
        valueFactoryMinsStart.setValue(appointment.getStart().getMinute());

        SpinnerValueFactory<Integer> valueFactoryMinsEnd =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60);
        endMinutesSpinner.setValueFactory(valueFactoryMinsEnd);
        valueFactoryMinsEnd.setValue(appointment.getEnd().getMinute());
    }

    public void initDates(){
        startDatePicker.setValue(DateTimeHelper.convertLocalDateTimeToLocalDate(appointment.getStart()));
        endDatePicker.setValue(DateTimeHelper.convertLocalDateTimeToLocalDate(appointment.getEnd()));

    }

    public void initAppointmentFields(){
        titleField.setText(appointment.getTitle());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());
        descriptionField.setText(appointment.getDescription());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointment = HomeController.selectedAppointmentToModify;
        getCustomerDataForComboBox();
        getContactDataForComboBox();
        getUserDataForComboBox();
        getSelectedComboBoxData();
        initAppointmentFields();
        initTimeSpinnersWithValues();
        initDates();

        if(DateTimeHelper.isAMTime(appointment.getStart())){
            startTimeAMRadioButton.setSelected(true);
        }else {
            startTimePMRadioButton.setSelected(true);
        }

        System.out.println(appointment.getEnd()+ "***************| " + DateTimeHelper.isAMTime(appointment.getEnd()));
        if(DateTimeHelper.isAMTime(appointment.getEnd())){
            endTimeAMRadioButton.setSelected(true);
        }else{
            endTimePMRadioButton.setSelected(true);
        }



    }
}
