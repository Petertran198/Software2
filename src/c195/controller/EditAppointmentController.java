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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller to handle Editing Appointments
 */
public class EditAppointmentController implements Initializable {
    //ComboBox variables
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML private  ComboBox<User> userCombo;
    @FXML private  ComboBox<Contact> contactCombo;
    //Appointment's Type & location
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> locationCombo;

    //Textfields
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    //Errors
    @FXML private Label errors;
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


    /**
     * Callback to returns the customer Name for the combobox text instead of the
     * Customer object because "Peter" is easier than "Model.298.Peter"
     */
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

    /**
     * Callback to returns the contact name for the combobox text instead of the
     * contact object because "Peter" is easier than "Model.298.Peter"
     */
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

    /**
     * Callback to returns the User Name for the combobox text instead of the
     * User object because "Peter" is easier than "Model.298.Peter"
     */
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

    /**
     * Populate the ComboBox with the correct customer, user, and contact
     */
    public  void getSelectedComboBoxData(){
        ObservableList<Customer> customerList = customerDao.getAllCustomer();
        //I used Lambda as it is more readable then using a for loop
        //The lambda iterate through each customer and then check for the correct
        // customer and then select that customer for combo box.
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

    /**
     * Init all time spinners with correct values
     */
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

    /**
     * Populate the datePickers with the correct local dates
     */
    public void initDates(){
        startDatePicker.setValue(DateTimeHelper.convertUTCLocalDateTimeToLocalDate(appointment.getStart()));
        endDatePicker.setValue(DateTimeHelper.convertUTCLocalDateTimeToLocalDate(appointment.getEnd()));

    }

    /**
     * Populate all the appointment text fields with the info of that specific
     * appointments
     */
    public void initAppointmentFields(){
        titleField.setText(appointment.getTitle());
        typeCombo.setItems(AddAppointmentController.appointmentTypesList);
        typeCombo.getSelectionModel().select(appointment.getType());
        locationCombo.setItems(AddAppointmentController.appointmentLocationList);
        locationCombo.getSelectionModel().select(appointment.getLocation());
        descriptionField.setText(appointment.getDescription());
    }


    /**
     * Set the correct radio button if appointment is AM or PM
     */
    public void setTimeSpinners(){
        if(DateTimeHelper.isAMTime(appointment.getStart())){
            startTimeAMRadioButton.setSelected(true);
        }else {
            startTimePMRadioButton.setSelected(true);
        }
        if(DateTimeHelper.isAMTime(appointment.getEnd())){
            endTimeAMRadioButton.setSelected(true);
        }else{
            endTimePMRadioButton.setSelected(true);
        }
    }

    /**
     * Update the appointment if it passes all test and validations.
     * @param event
     */
    public void saveAppointment(ActionEvent event) {
        //Convert 0 -> 00, 1 -> 01
        String startHourString;
        String endHourString;
        //Convert 0 -> 00, 1 -> 01
        String startMinutesString;
        String endMinutesString;
        // Combined string for Date,hours & mins of am/pm start and end date
        String combinedStartTime;
        String combinedEndTime;
        LocalDateTime startLocalDateTime;
        LocalDateTime endLocalDateTime;
        try{
            String title = titleField.getText();
            String type = typeCombo.getValue();
            String location = locationCombo.getValue();
            String description = descriptionField.getText();
            int startMinutes = startMinutesSpinner.getValue();
            int endMinutes = endMinutesSpinner.getValue();
            //12AM is 00H  while 12PM is 12H AND 1PM is 13H
            int startHour = startHourSpinner.getValue();
            int endHour = endHourSpinner.getValue();


            if(startTimeAMRadioButton.isSelected() && startHour == 12){
                startHour = 0;
            }
            //If Start PM Radio Button is selected and the time isnt 12pm because 12pm is just 12H not 24H
            //The rest of the PM's u add 12 too
            if(startTimePMRadioButton.isSelected() && startHour != 12){
                startHour = startHourSpinner.getValue() + 12;
            }

            if(endTimeAMRadioButton.isSelected() && endHour == 12){
                endHour = 0;
            }
            //If End PM Radio Button is selected and the time isnt 12pm because 12pm is just 12H not 24H
            if(endTimePMRadioButton.isSelected() && endHour != 12){
                endHour = endHourSpinner.getValue() + 12;
            }
            //Date Picker --------------
            String startDatePickerString =
                    startDatePicker.getValue().toString();
            String endDatePickerString = endDatePicker.getValue().toString();
            //Time ----------
            startHourString = (startHour < 10 ?
                    "0"+startHour :
                    String.valueOf(startHour));
            endHourString = (endHour < 10 ? "0"+endHour :
                    String.valueOf(endHour));
            //Move double two decimal place so it can get converted to minutes
            startMinutesString = (startMinutes < 10 ?
                    "0"+startMinutes :
                    String.valueOf(startMinutes));
            endMinutesString = (endMinutes < 10 ?
                    "0"+endMinutes :
                    String.valueOf(endMinutes));
            //Format Ex 2021-09-29 1:20  NOTE 24hour format
            combinedStartTime =
                    startDatePickerString+" "+startHourString+":"+startMinutesString;
            combinedEndTime =
                    endDatePickerString+" "+endHourString+":" + endMinutesString;
            //Convert start/end datetime string to utc time to be saved to the data base
            startLocalDateTime = DateTimeHelper.convertToUTC(combinedStartTime);
            endLocalDateTime = DateTimeHelper.convertToUTC(combinedEndTime);

            //Type & location combo box must be filled
            if(type.isBlank() || location.isBlank()){
                throw new NullPointerException();
            }
            Appointment a = new Appointment(title,description,
                    location,contactCombo.getValue().getContact_Name(),type,
                    customerCombo.getValue().getCustomer_ID(),
                    userCombo.getValue().getUser_ID(),
                    contactCombo.getValue().getContact_ID(),
                    startLocalDateTime,endLocalDateTime);
            a.setAppointment_ID(appointment.getAppointment_ID());

            //Appointments validation check ---------------------------------
            //Check if appointment is made within comapny's hour 8am to 10pm
            if(!DateTimeHelper.isAppointmentTimeWithinCompanysTime(startLocalDateTime,endLocalDateTime)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Conflicting Appointment Time");
                alert.setContentText("Appointments must be made within business hours. \n 8AM - 10PM EST Mon-Sun");
                alert.show();
                return;
            }
            //Check if appointment overlap
            if(DateTimeHelper.isAppointmentOverlapping(appointment, appointmentDAO.getAllAppointment())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Conflicting Appointment Time");
                alert.setContentText("This appointment overlap with another client.");
                alert.show();
                return;
            }

            appointmentDAO.updateAppointment(a);

            SwitchRoute.switchToHome(event);
        }catch(NullPointerException e){
            errors.setText("\nPlease fill out all fields \n-Date " +
                    "Pickers \n-Text Fields \n-Combo Boxes");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error From UpdateAppointment can't SwitchRoute");
        }
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
        setTimeSpinners();


    }
}
