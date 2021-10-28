package c195.controller;

import c195.model.Contact;
import c195.model.Customer;
import c195.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    @FXML private TextArea descriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
