package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.implementations.CustomerDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.dao.interfaces.CustomerDaoInterface;
import c195.model.Appointment;
import c195.model.Customer;
import c195.utilities.DateTimeHelper;
import c195.utilities.SwitchRoute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    //Radio Buttons for appointments order by weeks/month
    @FXML private RadioButton allRadioBtn;
    @FXML private RadioButton weeklyRadioBtn;
    @FXML private RadioButton monthlyRadioBtn;
    @FXML private ToggleGroup appointmentToogleGroup;
    @FXML private Button goUpBtn;
    @FXML private Button goDownBtn;
    //keep track of what month or week we want to see. Starting after
    // appointment start date
    private static int showWhatMonth = 0;
    private static int showWhatWeek = 0;


    //variables
    //Using the DAO pattern, customerDao will be used to query  the customer db
    CustomerDaoInterface customerDao = new CustomerDaoImplementation();
    AppointmentDaoInterface appointmentDao = new AppointmentDaoImplementation();

    //This variable is to select the customer we want to edit
    public  static Customer selectedCustomerToModify;
    //This variable is to select the appointment we want to edit
    public static Appointment selectedAppointmentToModify;

    //When the all appointments radio button is selected
    public void allRadioBtnMethod(ActionEvent event) throws Exception{
        //If shown allAppointments u disable goUp and downButton as it already shows
        // every appointment
        goUpBtn.setDisable(true);
        goDownBtn.setDisable(true);
        ObservableList<Appointment> appointments =
                appointmentDao.getAllAppointment();
        appointmentTable.setItems(appointments);
    }
    //When the weekly radio button is selected
    public void weeklyRadioBtnMethod(ActionEvent event) throws Exception{
        showWhatWeek = 0;
        goUpBtn.setDisable(false);
        goDownBtn.setDisable(false);
        ObservableList<Appointment> appointments =
                appointmentDao.getAppointmentsOrderByWeek(LoginController.user_id,
                        showWhatWeek);
        appointmentTable.setItems(appointments);
    }
    //When the monthly radio button is selected
    public void monthlyRadioBtnMethod(ActionEvent event) throws Exception{
        showWhatMonth = 0;
        goUpBtn.setDisable(false);
        goDownBtn.setDisable(false);
        ObservableList<Appointment> appointments =
                appointmentDao.getAppointmentsOrderByMonth(LoginController.user_id
                        ,showWhatMonth);
        appointmentTable.setItems(appointments);
    }

    //This method is to go up or down a week/month for appointments
    public void goDownBtnMethod(ActionEvent event) throws Exception{
        if(appointmentToogleGroup.getSelectedToggle() == weeklyRadioBtn){
            showWhatWeek = showWhatWeek -1;
            appointmentTable.setItems(appointmentDao.getAppointmentsOrderByWeek(LoginController.user_id,showWhatWeek));
        }

        if(appointmentToogleGroup.getSelectedToggle() == monthlyRadioBtn){
            showWhatMonth = showWhatMonth -1;
            appointmentTable.setItems(appointmentDao.getAppointmentsOrderByMonth(LoginController.user_id
                    ,showWhatMonth));

        }
    }

    //This method is to go up or down a week/month for appointments
    public void goUpBtnMethod(ActionEvent event) throws Exception{
        if(appointmentToogleGroup.getSelectedToggle() == weeklyRadioBtn){
            showWhatWeek = showWhatWeek +1;
            appointmentTable.setItems(appointmentDao.getAppointmentsOrderByWeek(LoginController.user_id,showWhatWeek));
        }

        if(appointmentToogleGroup.getSelectedToggle() == monthlyRadioBtn) {
            showWhatMonth = showWhatMonth + 1;
            appointmentTable.setItems(appointmentDao.getAppointmentsOrderByMonth(LoginController.user_id
                    ,showWhatMonth));
        }
    }
    public void modifyCustomer(ActionEvent event) throws Exception{
        String err = "";
        Customer selectedCustomer =
                customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            selectedCustomerToModify =
                    customerDao.findCustomer(selectedCustomer.getCustomer_ID());
            switchToEditCustomerInfoForm(event);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("No Selection Notice");
            alert.setContentText("Please select a customer to edit.");
            alert.showAndWait();
        }
    }

    public void modifyAppointment(ActionEvent event) throws Exception{
        String err = "";
        Appointment selectedAppointment =
                appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            selectedAppointmentToModify =
                    appointmentDao.findAppointment(selectedAppointment.getAppointment_ID());
            switchToEditAppointmentInfoForm(event);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("No Selection Notice");
            alert.setContentText("Please select a Appointment to update.");
            alert.showAndWait();
        }
    }
    public void deleteCustomer(){
        try{
            Customer selectedCustomer =
                    customerTable.getSelectionModel().getSelectedItem();
            int id = selectedCustomer.getCustomer_ID();
            String name = selectedCustomer.getCustomer_Name();
            int confirmDelete = JOptionPane.showConfirmDialog(null,"Are u sure u " +
                            "want" +
                            " to delete? \nCustomer: "+name+" with the id "+id +
                            "\nAll their " +
                            "APPOINTMENTS will also be deleted.\nPlease " +
                            "confirm that is what you want.",
                    "Delete " +
                            "Customer " +
                            "Notice",
                    JOptionPane.YES_NO_OPTION);
            if(confirmDelete == JOptionPane.YES_OPTION){
                customerDao.deleteCustomer(id);
                showCustomerTable();
                showAppointmentTable();
            }
        }catch(NullPointerException e){
            Alert noCustomerSelectedAlert = new Alert(Alert.AlertType.ERROR);
            noCustomerSelectedAlert.setContentText("Please select a " +
                    "customer to delete");
            noCustomerSelectedAlert.show();
        }

    }


    public void deleteAppointment(){
        try{
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            int id = selectedAppointment.getAppointment_ID();
            String title = selectedAppointment.getTitle();
            String type = selectedAppointment.getType();
            int confirmDelete = JOptionPane.showConfirmDialog(null,"Are u sure u " +
                            "want" +
                            " to delete? \nAppointment Title: "+title+" | Type: "+type,
                    "Delete " +
                            "Appointment " +
                            "Notice",
                    JOptionPane.YES_NO_OPTION);
            if(confirmDelete == JOptionPane.YES_OPTION){

                //Got to save these info because the appointment will already be deleted therefore
                //I can't called selectedAppointment.getAppointment_ID
                int appointmentID = selectedAppointment.getAppointment_ID();
                String appointmentType = selectedAppointment.getType();
                String appointmentTitle = selectedAppointment.getTitle();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Deleted Appointment");
                alert.setContentText("Appointment ID: "+appointmentID +"\nAppointment Title: "+appointmentTitle+"\nType: "+appointmentType+
                        "\nHas been deleted");
                appointmentDao.deleteAppointment(id);
                showAppointmentTable();
                alert.show();
            }
        }catch(NullPointerException e){
            Alert noAppointmentSelectedAlert = new Alert(Alert.AlertType.ERROR);
            noAppointmentSelectedAlert.setContentText("Please select an " +
                    "appointment to delete");
            noAppointmentSelectedAlert.show();
        }

    }


    //This method notifies if we have an appointment within 15 mins from us
    public void checkIfUserHasAppointmentsWithin15Mins(){
        if(LoginController.remaindAppointmentOnce == true){
            ObservableList<Appointment> usersAppointments =
                    appointmentDao.getUsersAppointments(LoginController.user_id);
            //Itterate though user appointments, convert to local time and check if
            // there is an appointment with 15 mins
            usersAppointments.forEach(appointment -> {
                LocalDateTime localConvertedStartTime =
                        DateTimeHelper.convertDBUTCTimeToSystemDefault(appointment.getStart());
                LocalDateTime localTimeRn =
                        LocalDateTime.now(ZoneId.systemDefault());
                LocalDateTime localDateTimeRNPlus15Mins =
                        LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

                if(localConvertedStartTime.isAfter(localTimeRn) && (localConvertedStartTime.isBefore(localDateTimeRNPlus15Mins))){
                    LoginController.remaindAppointmentOnce = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    String sentence = "You have an upcoming appointment.  " +
                            "\nAppointment" +
                            " ID: "+ appointment.getAppointment_ID() +"\nStarting at "+localConvertedStartTime.toLocalTime().format(formatter)+"\nLocal Time: " +localTimeRn.toLocalTime().format(formatter)
                            ;
                    alert.setContentText(sentence);
                    alert.show();
                }else {
                    LoginController.remaindAppointmentOnce = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    String sentence = "You have no upcoming appointments within 15 " +
                            "minutes";
                    alert.setContentText(sentence);
                    alert.show();
                }
            });
        }
    }
    /** This method when click will switch to the addCustomerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToAddCustomerInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToAddCustomerInfoForm(event);
    }
    /** This method when click will switch to the editCustomerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToEditCustomerInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToEditCustomerInfoForm(event);
    }

    /** This method when click will switch to the addAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToAddAppointmentInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToAddAppointmentInfoForm(event);
    }

    /** This method when click will switch to the editAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToEditAppointmentInfoForm(ActionEvent event) throws Exception {
        SwitchRoute.switchToEditAppointmentInfoForm(event);
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

    /** This method when click will switch to the addAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToLocationReport(ActionEvent event) throws Exception {
        SwitchRoute.switchToLocationReport(event);
    }

    /**
     * Init the customer columns and display customers
     */
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

    /**
     * Init the appointments columns and display appointments
     */
    public void showAppointmentTable(){
        goUpBtn.setDisable(true);
        goDownBtn.setDisable(true);
        ObservableList<Appointment> appointmentList;
        appointmentList = appointmentDao.getAllAppointment();
        appointmentTable.setItems(appointmentList);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "appointment_ID"));
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
                "customer_ID"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>(
                "start"));
        appointmentDueDateColumn.setCellValueFactory(new PropertyValueFactory<>(
                "end"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCustomerTable();
        showAppointmentTable();
        checkIfUserHasAppointmentsWithin15Mins();
    }
}