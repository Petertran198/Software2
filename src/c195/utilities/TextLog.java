package c195.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TextLog {

    public  void logInfo(boolean logInSuccessful){
        //Gets the login time.
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        //Formating pattern for date/time
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mma z");
        String timeString = formatter.format(zonedDateTime);
        if(logInSuccessful == false ){
            timeString += " -Unsuccessful login";
        }else{
            timeString += " -Successful login";
        }
        try(FileWriter fw = new FileWriter("login_activity.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fw)){
            bufferedWriter.write(timeString);
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage()+ " | Happened TextLog Class");
        }
    }
}
