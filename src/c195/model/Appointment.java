package c195.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime create_Date;
    private String created_By;
    private LocalDateTime last_Update;
    private String last_Updated_By;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;

    public Appointment(){

    }
}
