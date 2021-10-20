package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDaoImplementation implements AppointmentDaoInterface {

    @Override
    public ObservableList<Appointment> getAllAppointment() {
        ObservableList<Appointment> appointments =
                FXCollections.observableArrayList();
        String sql = "SELECT " +
                        "a.Appointment_ID, " +
                        "a.Title, " +
                        "a.Description, "+
                        "a.Location, " +
                        "c.Contact_Name, "+
                        "a.Type, "+
                        "a.Start, "+
                        "a.End, " +
                        "a.Customer_ID, " +
                        "a.User_ID " +
                    "FROM appointments a " +
                    "INNER JOIN contacts c " +
                        "ON a.Contact_ID = c.Contact_ID;";
        try{
            Connection con = JDBC.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact_name = resultSet.getString("Contact_Name");
                String type = resultSet.getString("Type");
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end =
                        resultSet.getTimestamp("End").toLocalDateTime();

                Appointment appointment = new Appointment(appointmentID,title
                        ,description, location, contact_name,type,customerID,
                        userID,start,end );
                appointments.add(appointment);
            }
            statement.close();
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage() + "------");
        }

        return appointments;
    }
}
