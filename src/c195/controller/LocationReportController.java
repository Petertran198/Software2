package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.Appointment;
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

public class LocationReportController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    //Appointment Table
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> startColumn;
    @FXML private TableColumn<Appointment, String> endColumn;
    @FXML private ComboBox<String> locationCombo;

    AppointmentDaoInterface appointmentDao = new AppointmentDaoImplementation();
    ObservableList<Appointment> appointmentsByLocation =
            FXCollections.observableArrayList();

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


    public void sortByLocation(ActionEvent event){
        String locationString = locationCombo.getValue();
        appointmentsByLocation =
                appointmentDao.getAppointmentsByLocation(locationString);
        appointmentTable.setItems(appointmentsByLocation);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationCombo.setItems(AddAppointmentController.appointmentLocationList);
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
        contactColumn.setCellValueFactory(new PropertyValueFactory<>(
                "contact_name"));
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>(
                "appointment_ID"));


    }
}
