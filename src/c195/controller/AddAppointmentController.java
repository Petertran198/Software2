package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.*;
import c195.utilities.DateTimeHelper;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Controller to handle AddingAppointments
 */
public class AddAppointmentController implements Initializable {

    //ComboBox variables
    @FXML private ComboBox<Customer> customerCombo;
    @FXML private  ComboBox<User> userCombo;
    @FXML private  ComboBox<Contact> contactCombo;
    //Appointment's Type & location
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> locationCombo;

    //Time components
    @FXML private DatePicker startDatePicker;
    @FXML private  DatePicker endDatePicker;
    @FXML private  Spinner<Integer> startHourSpinner;
    @FXML private  Spinner<Integer> startMinutesSpinner;
    @FXML private  Spinner<Integer> endHourSpinner;
    @FXML private  Spinner<Integer> endMinutesSpinner;

    //Time components Toggle Radio Button
    @FXML RadioButton startTimeAMRadioButton;
    @FXML RadioButton startTimePMRadioButton;
    @FXML RadioButton endTimeAMRadioButton;
    @FXML RadioButton endTimePMRadioButton;

    //Errors
    @FXML private Label errors;


    //Textfields
    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField descriptionField;


    //appointmentDao is used for accessing and saving appointments data.
    AppointmentDaoInterface appointmentDAO = new AppointmentDaoImplementation();

    //appointment type list to display the appointments types avaliable
    public static ObservableList<String> appointmentTypesList =
            FXCollections.observableArrayList("Team Meeting", "Business",
                    "Consultant");
    //appointment location list to display the appointments location available
    public static ObservableList<String> appointmentLocationList =
            FXCollections.observableArrayList("White Plains", "Newmarket", "London");

    /**
     * Populate typeCombo box with appointment types
     */
    public  void getAppointmentTypeDataForComboBox(){
        typeCombo.setItems(appointmentTypesList);
    }
    /**
     * Populate locationCombo box with appointment locations
     */
    public  void getAppointmentLocationDataForComboBox(){
        locationCombo.setItems(appointmentLocationList);
    }
    /**
     * Populate customerCombo box with customers
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


    /**
     * Save the appointment
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

            //Converted start/end datetime string to utc time to be saved to the db aka START/END
            startLocalDateTime = DateTimeHelper.convertToUTC(combinedStartTime);
            endLocalDateTime = DateTimeHelper.convertToUTC(combinedEndTime);
            //Type & location combo box must be filled
            if(type.isBlank() || location.isBlank()){
                throw new NullPointerException();
            }
            Appointment appointment = new Appointment(title,description,
                    location,contactCombo.getValue().getContact_Name(),type,
                    customerCombo.getValue().getCustomer_ID(),
                    userCombo.getValue().getUser_ID(),
                    contactCombo.getValue().getContact_ID(),
                    startLocalDateTime,endLocalDateTime);

            //Appointments validation check -------------------------------
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
            appointmentDAO.saveAppointment(appointment);
            SwitchRoute.switchToHome(event);
        }catch(NullPointerException e){
            errors.setText("\nPlease fill out all fields \n-Date " +
                    "Pickers \n-Text Fields \n-Combo Boxes");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error From AddAppointment can't SwitchRoute");
        }


    }

    /**
     * Init all  Time Spinners with correct Value
     */
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
        getAppointmentTypeDataForComboBox();
        getAppointmentLocationDataForComboBox();
    }
}