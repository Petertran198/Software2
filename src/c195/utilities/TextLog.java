package c195.utilities;

import c195.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to keep track of logins
 */
public class TextLog {

    /**
     * Keep track of login activities in text field
     */
    public  void logInfo(boolean logInSuccessful, User user){
        //Gets the login time.
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        //Formating pattern for date/time
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mma z");
        String timeString = formatter.format(zonedDateTime);
        if(logInSuccessful == false ){
            timeString += " -Unsuccessful login | Invalid Username entered:"+ user.getUser_Name();
        }else{
            timeString += "-Successful login by " + user.getUser_Name();
        }
        try(FileWriter fw = new FileWriter("login_activity.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fw)){
            bufferedWriter.write(timeString);
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage()+ " | Happened in TextLog Class");
        }
    }
}
