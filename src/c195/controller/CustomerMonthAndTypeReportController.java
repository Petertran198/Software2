package c195.controller;

import c195.dao.implementations.AppointmentDaoImplementation;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.AppointmentTypeOrMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerMonthAndTypeReportController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TableView<AppointmentTypeOrMonth> appointmentTypeOrMonthTable;
    @FXML private TableColumn<AppointmentTypeOrMonth, Integer> appointmentCountTableColumn;
    @FXML private TableColumn<AppointmentTypeOrMonth, String> appointmentTypeOrMonthTableColumn;
    @FXML private RadioButton typeRadioBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML private ToggleGroup radioToggleGroup;

    AppointmentDaoInterface appointmentDAO = new AppointmentDaoImplementation();
    //Holds the data of what appointments to show by type or months
    ObservableList<AppointmentTypeOrMonth> showAppointmentsList =
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

    public void getAppointmentsByType(javafx.scene.input.MouseEvent mouseEvent) {
        appointmentTypeOrMonthTableColumn.setText("Type");
        showAppointmentsList =
                appointmentDAO.getAppointmentsOrderByType();
        appointmentTypeOrMonthTable.setItems(showAppointmentsList);

    }

    public void getAppointmentsByMonth(javafx.scene.input.MouseEvent mouseEvent) {
        appointmentTypeOrMonthTableColumn.setText("Month");
        showAppointmentsList =
                appointmentDAO.getAppointmentsByMonth();
        appointmentTypeOrMonthTable.setItems(showAppointmentsList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentCountTableColumn.setCellValueFactory(new PropertyValueFactory<>(
                "quantity"));
        appointmentTypeOrMonthTableColumn.setCellValueFactory(new PropertyValueFactory<>(
                "name"));

        //Populate the table first time with appointments group by types
        appointmentTypeOrMonthTableColumn.setText("Type");
        showAppointmentsList =
                appointmentDAO.getAppointmentsOrderByType();
        appointmentTypeOrMonthTable.setItems(showAppointmentsList);
    }

}
