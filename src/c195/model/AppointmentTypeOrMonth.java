package c195.model;

/**
 * This class is used to hold the aggregated data when querying the Appointment Table
 * Contains info about appointments as a whole AKA(GROUP BY CLAUSE)
 */
public class AppointmentTypeOrMonth {
    private String name;
    private int quantity;
    private  String month;
    public AppointmentTypeOrMonth(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
