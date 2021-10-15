package c195.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // This will allow us to access a specific set of properties files
    // within c195/languages/Nat. The language code we want to get is
    // returned from Locale.getDefault();
    //In my case Locale.getDefault() will return 'en' language code
    ResourceBundle rb = ResourceBundle.getBundle("c195/languages/Nat",
            Locale.getDefault());
    //Labels for FXML view page
    @FXML private Label userIdLabel;
    @FXML private Label passwordLabel;
    @FXML private Label appointmentAppLabel;
    @FXML private Label loginLabel;
    @FXML private Label timeZoneTitleLabel;
    @FXML private Label timeZoneLabel;
    @FXML private Label errors;
    //Button FXML
    @FXML private Button loginButton;


    //Needed to append the error message if other error messages are needed
    StringBuilder errorMsgText = new StringBuilder();

    /**
     * Return ur local machine's time zone
     * @return String
     */
    private String getZoneID(){
        return ZoneId.systemDefault().getId();
    }

    /**
     * set the language to ur local machines language
     * if the default language isn't found just set it to english
     */
    private void setUserLanguageFromLocalMachine(){
        try {
            userIdLabel.setText(rb.getString("UserID"));
            passwordLabel.setText(rb.getString("Password"));
            appointmentAppLabel.setText(rb.getString("AppointmentApp"));
            timeZoneTitleLabel.setText(rb.getString("Location"));
            loginLabel.setText(rb.getString("Login"));
        }catch (Exception e){
            errorMsgText.append("Local Machine's language not supported. " +
                    "Default: English");
            errors.setText(errorMsgText.toString());

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneLabel.setText(" " + getZoneID());
        setUserLanguageFromLocalMachine();
    }
}
