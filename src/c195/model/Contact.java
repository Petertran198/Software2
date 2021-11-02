package c195.model;

public class Contact {
    private  int contact_ID;
    private  String contact_Name;
    private String email;

    public Contact(int contact_ID, String contact_Name, String email){
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getContact_Name() {
        return contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    public int getContact_ID() {
        return contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }


    //This method will instead of populating the combo box with just
    // the object in string form (Non legible) will instead return the contact's name
    @Override
    public String toString() {
        return this.getContact_Name();
    }
}
