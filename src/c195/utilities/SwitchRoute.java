package c195.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class to handle routing
 */
public class SwitchRoute {
    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    /** This method when click will switch to the addCustomerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToHome(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/home" +
                ".fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the addCustomerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToAddCustomerInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/addCustomerInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /** This method when click will switch to the editCustomerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToEditCustomerInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view" +
                "/editCustomerInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the addAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToAddAppointmentInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/addAppointmentInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /** This method when click will switch to the editAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToEditAppointmentInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view" +
                "/editAppointmentInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /** This method when click will switch to the contactAppointmentReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToContactAppointmentReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/contactAppointmentReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /** This method when click will switch to the customerMonthAndTypeReport.fxml
     * @param event any ActionEvent most likely click
     * @throws Exception
     * */
    public static void switchToCustomerMonthAndTypeReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/customerMonthAndTypeReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the addAppointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * @throws Exception if FXML not found
     * */
    public static void switchToLocationReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/locationReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
