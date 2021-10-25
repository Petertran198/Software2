package c195.model;

import java.time.LocalDateTime;

public class Customer {
    private int customer_ID;
    private int division_ID;
    private String customer_Name;
    private String address;
    private String postal_code;
    private String phone;
    private LocalDateTime created_Date;
    private String created_By;
    private LocalDateTime last_Update;
    private String last_Updated_By;
    //Made up for the Customer table view to be populated
    private String country_name;
    private  String division;


    /**
     * Used to add a customer for appointment form
     * @param customer_ID
     * @param customer_Name
     */
    public Customer(int customer_ID, String customer_Name){
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
    }
    /**
     * This constructor is for the table view therefore it only has certain
     * parms. It is not meant to be saved in database that will be dont in
     * overloaded verizon of this constructor
     * @param customer_ID
     * @param customer_Name
     * @param address
     * @param division
     * @param country_name
     * @param phone
     */
    public Customer(int customer_ID, String customer_Name,
                    String address, String division, String country_name,
                    String phone
                    ){

        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.division = division;
        this.phone = phone;
        this.country_name = country_name;
    }

    /**
     * This constructor is for customer add field
     * @param name
     * @param address
     * @param postal_code
     * @param phone
     * @param country_name
     * @param divisionId
     */
    public Customer(String name, String address,
                    String postal_code, String phone,
                    String country_name,  int divisionId) {

        this.customer_Name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.country_name = country_name;
        this.division_ID = divisionId;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getDivision_ID() {
        return division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(LocalDateTime created_Date) {
        this.created_Date = created_Date;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public LocalDateTime getLast_Update() {
        return last_Update;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        this.last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }




}
