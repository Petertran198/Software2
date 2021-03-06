package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.Appointment;
import c195.model.AppointmentTypeOrMonth;
import c195.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to handle  Appointments Report for Contacts
 */
public class ContactAppointmentReportController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //FXML variables for table view
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> idColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> contactNameColumn;
    @FXML private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML private TableColumn<Appointment, String> startColumn;
    @FXML private TableColumn<Appointment, String> endColumn;
    @FXML private ComboBox<Contact> contactCombo;


    AppointmentDaoInterface appointmentDao = new AppointmentDaoImplementation();
    ObservableList<Appointment> contactList = FXCollections.observableArrayList();


    /**
     * Select a contact from combo and query sql for that contact
     * @param event
     */
    public void selectContact(ActionEvent event){
       contactList =
                appointmentDao.getAllAppointmentsByContact(contactCombo.getValue().getContact_Name());
        appointmentTable.setItems(contactList);
    }


    /** This method when click will switch to the home.fxml
     * @param event any ActionEvent most likely click
     * */
    public void switchToHome(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(appointmentDao.getAllContacts());
        //Init the table columns
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "customer_ID"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "contact_name"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>(
                "title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>(
                "description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>(
                "start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>(
                "end"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>(
                "appointment_ID"));
    }
}