package c195.model;

import java.time.LocalDateTime;

public class FLDivision {
    private int division_ID;
    private String division_Name;
    private LocalDateTime create_Date;
    private String created_By;
    private String last_Updated_By;
    private LocalDateTime last_Update;
    private int country_ID;
    public FLDivision(){

    }

    public FLDivision(int division_ID, String division_Name, int country_ID){
        this.division_Name = division_Name;
        this.country_ID = country_ID;
        this.division_ID = division_ID;
    }
    public int getDivision_ID() {
        return division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    public String getDivision_Name() {
        return division_Name;
    }

    public void setDivision_Name(String division_Name) {
        this.division_Name = division_Name;
    }

    public LocalDateTime getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        this.create_Date = create_Date;
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

    public int getCountry_ID() {
        return country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    @Override
    public String toString() {
        return this.getDivision_Name();
    }
}
