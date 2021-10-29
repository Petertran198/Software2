package c195.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper {

    public static LocalDateTime convertToUTC(String dateString){
        LocalDateTime date = null;
        try{
                //Formatter pattern
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd kk:mm");
                //convert our string date to localdatetime obj using the
            // pattern specified above
                date = LocalDateTime.parse(dateString,formatter);
                //System default time zone
                ZonedDateTime localDateTimeZone =
                    date.atZone(ZoneId.systemDefault());
                ZonedDateTime utcDateTimeZone =
                    localDateTimeZone.withZoneSameInstant(ZoneId.of("UTC"));
                //This is the converted utc timezone
                date = utcDateTimeZone.toLocalDateTime();
        }catch(Exception e){
                System.out.println("ConvertToUTC not working");
        }
        return date;
    }

}
