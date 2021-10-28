package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.*;
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
                        "a.Contact_ID, "+
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
                int contactID = resultSet.getInt("Contact_ID");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end =
                        resultSet.getTimestamp("End").toLocalDateTime();

                Appointment appointment = new Appointment(appointmentID,title
                        ,description, location, contact_name,type,customerID,
                        userID,contactID,start,end );
                appointments.add(appointment);
            }
            statement.close();
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage() + "------");
        }

        return appointments;
    }

    @Override
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        String sql = "Select Customer_ID, Customer_Name FROM Customers";
        ObservableList<Customer> customers =
                FXCollections.observableArrayList();
        try{
            Statement statement =
                    JDBC.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name")
                );
                customers.add(customer);
            }
        }catch(SQLException e){
            System.out.println("Error in getAllCustomer method " + e.getMessage());
        }
        return customers;
    }

    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM Contacts";
        try{
            Statement statement =
                    JDBC.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Contact contact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                contacts.add(contact);
            }
        }catch(SQLException e){
            System.out.println("Error in getAllContact method " + e.getMessage());
        }
        return contacts;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        String sql = "Select * FROM Users";
        ObservableList<User> users = FXCollections.observableArrayList();
        try{
            Statement statement =
                    JDBC.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name")
                );
                users.add(user);
            }
        }catch(SQLException e){
            System.out.println("Error in getAllCustomer method " + e.getMessage());
        }
        return users;
    }

}
