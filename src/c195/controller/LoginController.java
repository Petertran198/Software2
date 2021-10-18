package c195.controller;

import c195.utilities.LoginAccount;
import c195.utilities.SwitchRoute;
import c195.utilities.TextLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    //TextFields FXML
    @FXML private TextField userIDTextField;
    @FXML private TextField passwordTextField;
    //Button FXML
    @FXML private Button loginButton;

    //variables to switch scenes.
    private Stage stage;
    private Scene scene;
    private Parent root;


    //Needed to append the error message if other error messages are needed
    private String errorMsg = "";

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
            errorMsg += "\n -Local Machine's language not supported. " +
                    "Default: English";
            errors.setText(errorMsg);
        }
    }

    /**This method make sure that the form does not have any empty fields everything must be populated
     * <br/> <strong>RUNTIME ERROR:</strong> Unable to properly display the error because instead or errors += errors I did
     * errors = errors. I fixed this by instead of rewriting the error I added on to it by using '+='
     * @param name name text field input string
     * @param inventory inventory text field input string
     * @param cost cost text field input string
     * @param max  max text field input string
     * @param min  min text field input string
     * @param inHouseFieldOrOutsourcedField MachineId/Company's name text field input string
     * @return Returns correct error message if any field is blank
     */
    public  String handleFormErrorsEmptyField(String username,
                                                    String password) {
        String errors= "";

        if (username.equals("")) {
            errors +=
                     "\n- "+ rb.getString("Username")+" "+  rb.getString("not") +" "+ rb.getString("found");
        }
        if (password.equals("")) {
            errors += "\n- "+ rb.getString("Password")+" "+  rb.getString("not") +" "+ rb.getString("found");
        }

        return errors;
    }

    /**
     * Get the userId and password
     * Then check if there is an account with those info
     * If there is then log in if not give error message
     */
    @FXML
    private void loginToAccount(ActionEvent event)  {
        TextLog log = new TextLog();
        try{
                if(LoginAccount.logIntoAccount(userIDTextField.getText(),
                        passwordTextField.getText())){
                    log.logInfo(true);
                    SwitchRoute.switchToHome(event);
                }else {
                    errorMsg += "\n -"+
                            rb.getString("Account") + " " + rb.getString("not")+" "+ rb.getString("found");
                    errorMsg += handleFormErrorsEmptyField(userIDTextField.getText(), passwordTextField.getText());
                    errors.setText(errorMsg);
                    errorMsg = "";
                    log.logInfo(false);
                }
            }catch(Exception e){
                    System.out.println(e.getMessage());
            }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneLabel.setText(" " + getZoneID());
        setUserLanguageFromLocalMachine();
    }
}
