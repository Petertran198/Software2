package c195.dao.interfaces;

import c195.model.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface AppointmentDaoInterface {
    ObservableList<Appointment> getAllAppointment();
    void deleteAppointment(int id);
    ObservableList<Customer> getAllCustomers();
    ObservableList<Contact> getAllContacts();
    ObservableList<User> getAllUsers();
    public void saveAppointment(Appointment appointment);
    public Appointment findAppointment(int id);
    public void updateAppointment(Appointment appointment);
    public ObservableList<Appointment> getUsersAppointments(int id);
    public ObservableList<Appointment> getAppointmentsOrderByWeek(int user_id,
                                                                  int numberOfWeekOffsetFromStartAppointments) throws SQLException;
    public ObservableList<Appointment> getAppointmentsOrderByMonth(int user_id,
                                                                  int numberOfMonthOffsetFromStartAppointments) throws SQLException;
    public ObservableList<AppointmentTypeOrMonth> getAppointmentsOrderByType();
    public ObservableList<AppointmentTypeOrMonth> getAppointmentsByMonth();
}
