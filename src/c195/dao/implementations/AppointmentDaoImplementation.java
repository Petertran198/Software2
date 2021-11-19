package c195.dao.implementations;

import c195.dao.JDBC;
import c195.dao.interfaces.AppointmentDaoInterface;
import c195.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Contains DB methods for handling Appointments
 */
public class AppointmentDaoImplementation implements AppointmentDaoInterface {

    /**
     *
     * @return Observablelist of all Appointments
     */
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
                LocalDateTime start = resultSet.getObject("Start",
                        LocalDateTime.class);
                LocalDateTime end =
                        resultSet.getObject("End",LocalDateTime.class);
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

    /**
     * Takes in an appointmentID and delete that appointment
     * @param id appointmentID
     */
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

    /**
     * Get a list of all Customers
     * @return ObservableList of Customers
     */
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

    /**
     * Get a list of all Contacts
     * @return ObservableList of Contacts
     */
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

    /**
     * Get a list of all Users
     * @return ObservableList of Users
     */
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

    /**
     * Save appointment to db
     * @param appointment takes in an appointment instance to save to db
     */
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

    /**
     * Find the specific appointment specified
     * @param id Takes in appointment id
     * @return the correct appointment specified
     */
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

    /**
     * Update a specific  appointment
     * @param appointment Takes in an appointment that u want to update its data
     */
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

    /**
     * Takes in a user_id and return a list of all appointments made by that user
     * @param id
     * @return observableList of all appointments made by that specific user
     */
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

    /**
     *
     * @param user_id
     * @param numberOfWeekOffsetFromStartAppointments
     * @return  an  observableList of appointments by Weeks
     * orders by weeks
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentsOrderByWeek(int user_id, int numberOfWeekOffsetFromStartAppointments) throws SQLException {
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
                "ON a.Contact_ID = c.Contact_ID " +
                "WHERE a.User_ID=? AND WEEK(a.Start) = WEEK(now(),0) + "+ numberOfWeekOffsetFromStartAppointments;
        try(PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql);){
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        }catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Problem in getAppointmentsOrderByWeek "+ e.getMessage());
            alert.show();
        }
        return appointments;
    }

    /**
     * @param user_id
     * @param numberOfMonthOffsetFromStartAppointments
     * @return ObserableList of appointments by months
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentsOrderByMonth(int user_id, int numberOfMonthOffsetFromStartAppointments) throws SQLException {
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
                "ON a.Contact_ID = c.Contact_ID " +
                "WHERE a.User_ID=? AND MONTH(a.Start) = MONTH(now()) + "+ numberOfMonthOffsetFromStartAppointments;
        try(PreparedStatement preparedStatement =
                    JDBC.getConnection().prepareStatement(sql);){
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        }catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Problem in getAppointmentsOrderByMonth "+ e.getMessage());
            alert.show();
        }
        return appointments;
    }

    /**
     * Get the appointments with a specific location
     * @param loc
     * @return an observableList of appointments by location
     */
    @Override
    public ObservableList<Appointment> getAppointmentsByLocation(String loc) {
        String sql = "SELECT " +
                " a.Appointment_ID, "+
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
                "ON a.Contact_ID = c.Contact_ID" +
                " WHERE a.Location = '"+ loc +"'";
        ObservableList<Appointment> appointments =
                FXCollections.observableArrayList();
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                Appointment appointment = new Appointment();
                appointment.setAppointment_ID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setContact_name(resultSet.getString("Contact_Name"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setDescription(resultSet.getString("description"));
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                appointment.setStart(start);
                LocalDateTime end =
                        resultSet.getTimestamp("End").toLocalDateTime();
                appointment.setEnd(end);
                appointments.add(appointment);
            }
        }catch (SQLException e){
            System.out.println("Errors in getAppointmentByLocation "+ e.getMessage());
        }
        return appointments;
    }

    /**
     * Get all the appointments of a specific contact
     * @return an observableList of appointments by a specific contact
     */
    @Override
    public ObservableList<Appointment> getAllAppointmentsByContact(String contactName) {
        String sql = "SELECT " +
                        "c.Contact_Name, a.Appointment_ID, a.Title, a.Type, " +
                "a.Description, " +
                        "a.Start, a.End, a.Customer_ID "+
                    "FROM appointments a INNER JOIN contacts c " +
                        "ON c.Contact_ID = a.Contact_ID "+
                    "WHERE Contact_Name = '"+contactName+"' " +
                    "ORDER BY a.Start";
        ObservableList<Appointment> appointments =
                FXCollections.observableArrayList();
        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                Appointment appointment = new Appointment();
               appointment.setAppointment_ID(resultSet.getInt("Appointment_ID"));
               appointment.setContact_name(resultSet.getString("Contact_Name"));
               appointment.setType(resultSet.getString("Type"));
               appointment.setTitle(resultSet.getString("Title"));
               appointment.setDescription(resultSet.getString("Description"));
               appointment.setCustomer_ID(resultSet.getInt("Customer_ID"));
               appointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
               appointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
               appointments.add(appointment);
               System.out.println(appointment.getCustomer_ID() );
            }
        }catch (SQLException e){
            System.out.println("Error in getAllAppointmentsByContact "+ e.getMessage());
        }

        return appointments;
    }


    @Override
    public ObservableList<AppointmentTypeOrMonth> getAppointmentsOrderByMonthAndType() {
        String sql = "SELECT Type, Count(*) as Quantity, date_format(Start, " +
                "'%Y-%m-01') " +
                "AS Month FROM appointments GROUP BY Month, Type";
        ObservableList<AppointmentTypeOrMonth> appointmentTypes =
                FXCollections.observableArrayList();

        try(
                Statement statement = JDBC.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                AppointmentTypeOrMonth appointmentType =
                        new AppointmentTypeOrMonth();
                appointmentType.setName(resultSet.getString("Type"));
                appointmentType.setQuantity(resultSet.getInt("Quantity"));
                appointmentType.setMonth(resultSet.getString("Month"));
                System.out.println(appointmentType.getMonth());
                appointmentTypes.add(appointmentType);
            }
        }catch (SQLException e){
            System.out.println("Error in getAppointmentsOrderByMonths "+ e.getMessage());
        }

        return appointmentTypes;
    }

}
