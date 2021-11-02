package c195.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper {

    //Useful for datepicker as it only accept localdate obj
    public static LocalDate convertLocalDateTimeToLocalDate(LocalDateTime localDateTime){
        LocalDate localDate = localDateTime.toLocalDate();
        return  localDate;
    }

    public static boolean isAMTime(LocalDateTime localDateTime){
        int hour = localDateTime.getHour();
        if(hour == 0){
            return true;
        }
         return hour < 12 ? true : false;
    }


    public static int returnHourIn12HourFormat(LocalDateTime localDateTime){
        int hour = localDateTime.getHour();
        if(hour == 0){
             hour = 12;
        }else if (hour >0 && hour > 12){
                switch (hour){
                    case 13:
                        hour = 1;
                        break;
                    case 14:
                        hour = 2;
                        break;
                    case 15:
                        hour = 3;
                        break;
                    case 16:
                        hour = 4;
                        break;
                    case 17:
                        hour = 5;
                        break;
                    case 18:
                        hour = 6;
                        break;
                    case 19:
                        hour = 7;
                        break;
                    case 20:
                        hour = 8;
                        break;
                    case 21:
                        hour = 9;
                        break;
                    case 22:
                        hour = 10;
                        break;
                    case 23:
                        hour = 11;
                        break;
                    case 24:
                        hour = 12;
                        break;
                }
        }
        return hour;
    }

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
