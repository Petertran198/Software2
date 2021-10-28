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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    //ComboBox variables
    @FXML private ComboBox<Customer> customerCombo;
    @FXML private  ComboBox<User> userCombo;
    @FXML private  ComboBox<Contact> contactCombo;
    //Time components
    @FXML private DatePicker startDatePicker;
    @FXML private  DatePicker endDatePicker;
    @FXML private  Spinner<Integer> startHourSpinner;
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

    //Errors
    @FXML private Label errors;
    String errorListString = "";

    //Textfields
    @FXML private TextField titleField;
    @FXML private TextField typeField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;


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



    public void saveAppointment(ActionEvent event) {
        int startHour;
        int startMinutes;
        int endHour;
        int endMinutes;
        //Convert 0 -> 00, 1 -> 01
        String startMinutesString;
        String endMinutesString;
        // Combined string for Date,hours & mins of am/pm start and end date
        String combinedStartTime;
        String combinedEndTime;
        try{
            String title = titleField.getText();
            String type = typeField.getText();
            String location = locationField.getText();
            String description = descriptionField.getText();
            startMinutes = startMinutesSpinner.getValue();
            endMinutes = endMinutesSpinner.getValue();
            //Move double two decimal place so it can get converted to minutes
            startMinutesString = (startMinutes < 10 ?
                    "0"+startMinutes :
                    String.valueOf(startMinutes));
            endMinutesString = (endMinutes < 10 ?
                    "0"+endMinutes :
                    String.valueOf(endMinutes));

            //If Start Am Radio Button is selected
            if(startTimeAMRadioButton.isSelected()){
                startHour = startHourSpinner.getValue();
            }else{
                startHour = startHourSpinner.getValue() + 12;
            }
            //If End Am Radio Button is selected
            if(endTimeAMRadioButton.isSelected()){
                endHour = endHourSpinner.getValue();
            }else{
                endHour = endHourSpinner.getValue() + 12;
            }
            String startDatePickerString =
                    startDatePicker.getValue().toString();
            String endDatePickerString = endDatePicker.getValue().toString();
            //Format Ex 2021-09-29 1:20  NOTE 24hour format
            combinedStartTime =
                    startDatePickerString+" "+startHour+":"+startMinutesString;
            combinedEndTime = endDatePickerString+" "+endHour+
                    ":"+endMinutesString;


        }catch(NullPointerException e){
            errors.setText("\nPlease fill out all fields \n-Date " +
                    "Pickers \n-Text Fields");
        }


    }

    public void initTimeSpinnersWithValues(){
        SpinnerValueFactory<Integer> valueFactoryHoursStart =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12);
        startHourSpinner.setValueFactory(valueFactoryHoursStart);

        SpinnerValueFactory<Integer> valueFactoryHoursEnd =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12);
        endHourSpinner.setValueFactory(valueFactoryHoursEnd);

        SpinnerValueFactory<Integer> valueFactoryMinsStart =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60);
        startMinutesSpinner.setValueFactory(valueFactoryMinsStart);


        SpinnerValueFactory<Integer> valueFactoryMinsEnd =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60);
        endMinutesSpinner.setValueFactory(valueFactoryMinsEnd);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerDataForComboBox();
        getContactDataForComboBox();
        getUserDataForComboBox();
        initTimeSpinnersWithValues();

    }
}