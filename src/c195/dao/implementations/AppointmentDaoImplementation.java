package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.*;
import c195.utilities.DateTimeHelper;
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
                        ,description,location,contact_name,type,customerID,
                        userID,contactID,start,end);

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

    @Override
    public void saveAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments(Title,Description,Location," +
                "Type,Customer_ID,User_ID, Contact_ID, Start, End) VALUES(?," +
                "?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3,appointment.getLocation());
            preparedStatement.setString(4,appointment.getType());
            preparedStatement.setInt(5,appointment.getCustomer_ID());
            preparedStatement.setInt(6,appointment.getUser_ID());
            preparedStatement.setInt(7,appointment.getContact_ID());
            preparedStatement.setObject(8, appointment.getStart());
            preparedStatement.setObject(9, appointment.getEnd());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in addAppointment "+ e.getMessage());
        }
    }

    @Override
    public Appointment findAppointment(int id) {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location," +
                            "a.Type,a.Start, a.end, user.User_ID, contact.Contact_ID," +
                            " Customer.Customer_ID\n" +
                        "FROM appointments a\n" +
                    "INNER JOIN users user\n" +
                        "ON a.User_ID = user.User_ID\n" +
                    "INNER JOIN contacts contact\n" +
                        "ON a.Contact_ID = contact.Contact_ID\n" +
                    "INNER JOIN customers customer \n" +
                        "ON a.Customer_ID = customer.Customer_ID \n" +
                    "WHERE a.Appointment_ID =" + id +" LIMIT 1";
        Appointment appointment = new Appointment();
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                int appointment_id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start",LocalDateTime.class );
                LocalDateTime end = resultSet.getObject("End",LocalDateTime.class );
                int customer_id = resultSet.getInt("Customer_ID");
                int user_id = resultSet.getInt("User_ID");
                int contact_id = resultSet.getInt("Contact_ID");
                appointment.setAppointment_ID(appointment_id);
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                appointment.setCustomer_ID(customer_id);
                appointment.setUser_ID(user_id);
                appointment.setContact_ID(contact_id);
                appointment.setStart(start);
                appointment.setEnd(end);
                return appointment;
            }
            return appointment;
        }catch (SQLException e){
            System.out.println("Error in findAppointment "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        try{
            String sql = "Update appointments" +
                    " SET Title=?, Description=?, Location=?, Type=?," +
                    " Start=?, End=?, Customer_ID=?, User_ID =?, Contact_ID =? " +
                    "WHERE Appointment_ID=?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,appointment.getTitle());
            preparedStatement.setString(2,appointment.getDescription());
            preparedStatement.setString(3,appointment.getLocation());
            preparedStatement.setString(4,appointment.getType());
            preparedStatement.setObject(5, appointment.getStart());
            preparedStatement.setObject(6,appointment.getEnd());
            preparedStatement.setInt(7, appointment.getCustomer_ID());
            preparedStatement.setInt(8, appointment.getUser_ID());
            preparedStatement.setInt(9, appointment.getContact_ID());
            preparedStatement.setInt(10, appointment.getAppointment_ID());
            preparedStatement.executeUpdate();
        }catch(SQLException e ){
            System.out.println("Error in updateAppointment "+ e.getMessage());
        }
    }

    @Override
    public ObservableList<Appointment> getUsersAppointments(int id) {
        String sql = "Select * FROM Appointments WHERE User_ID =" + id;
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try{
            Statement statement =
                    JDBC.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Appointment appointment = new Appointment();
                appointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appointment.setAppointment_ID(resultSet.getInt("Appointment_ID"));
                appointments.add(appointment);
            }
        }catch(SQLException e){
            System.out.println("Error in getUsersAppointments method " + e.getMessage());
        }
        return appointments;
    }

}
