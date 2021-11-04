package c195.model;

import org.w3c.dom.Text;

import java.time.LocalDateTime;

public class User {
    private int user_ID;
    private String user_Name;
    private Text password;
    private LocalDateTime create_Date;
    private String created_Date;
    private LocalDateTime last_Update;
    private String last_Updated_By;

    public User(int user_ID, String user_Name){
        this.user_ID = user_ID;
        this.user_Name=user_Name;
    }

    public User() {

    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    @Override
    public String toString() {
        return this.getUser_Name();
    }
}
