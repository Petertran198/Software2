package c195.dao.interfaces;

import c195.model.Appointment;
import c195.model.Contact;
import c195.model.Customer;
import c195.model.User;
import javafx.collections.ObservableList;

public interface AppointmentDaoInterface {
    ObservableList<Appointment> getAllAppointment();
    void deleteAppointment(int id);
    ObservableList<Customer> getAllCustomers();
    ObservableList<Contact> getAllContacts();
    ObservableList<User> getAllUsers();
    public void saveAppointment(Appointment appointment);

}
