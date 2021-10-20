package c195.dao.interfaces;

import c195.model.Appointment;
import javafx.collections.ObservableList;

public interface AppointmentDaoInterface {
    ObservableList<Appointment> getAllAppointment();
}
