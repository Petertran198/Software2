package c195.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchRoute {
    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    /** This method when click will switch to the customerInfoForm.fxml
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

    /** This method when click will switch to the customerInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToCustomerInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/customerInfoForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToAppointmentInfoForm(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/appointmentInfoForm.fxml"));
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
        stage.show();
    }

    /** This method when click will switch to the customerMonthAndTypeReport.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToCustomerMonthAndTypeReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/customerMonthAndTypeReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This method when click will switch to the appointmentInfoForm.fxml
     * @param event any ActionEvent most likely click
     * */
    public static void switchToLocationReport(ActionEvent event) throws Exception {
        root = FXMLLoader.load(SwitchRoute.class.getResource("../view/locationReport.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
