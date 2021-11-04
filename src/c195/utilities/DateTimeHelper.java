package c195.utilities;

import c195.model.Appointment;
import javafx.collections.ObservableList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper {


    public static LocalDate convertUTCLocalDateTimeToLocalDate(LocalDateTime localDateTime){
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime,ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDateTime().toLocalDate();
        return  localDate;
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
            ZonedDateTime localDateTimeZone = ZonedDateTime.of(date,ZoneId.systemDefault());
            ZonedDateTime utcDateTimeZone =
                    localDateTimeZone.withZoneSameInstant(ZoneId.of("UTC"));
            //This is the converted utc timezone
            date = utcDateTimeZone.toLocalDateTime();
        }catch(Exception e){
            System.out.println("ConvertToUTC not working");
        }
        return date;
    }

    //This method convert the time that was saved in db in UTC to be converted to system default tineZone
    public static LocalDateTime convertUTCLocalDateTimeToSystemDefault(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime localZonedDateTime =  ZonedDateTime.of(localDateTime,zoneId);
        return localZonedDateTime.toLocalDateTime();
    }

    public static boolean isAMTime(LocalDateTime localDateTime){
        int hour = localDateTime.getHour();
        if(hour == 0){
            return true;
        }
         return hour < 12 ? true : false;
    }


    public static boolean isAppointmentOverlapping(Appointment currentAppointment,
                                                   ObservableList<Appointment> allAppointments){
        for(Appointment anotherAppointment : allAppointments){
            //If currentAppointment and anotherAppointments and not made by the
            // same user and customer
            LocalDateTime currentStart = currentAppointment.getStart();
            LocalDateTime currentEnd = currentAppointment.getEnd();
            LocalDateTime anotherAppointmentStart = anotherAppointment.getStart();
            LocalDateTime anotherAppointmentEnd = anotherAppointment.getEnd();
            if(anotherAppointment.getAppointment_ID() == currentAppointment.getAppointment_ID() ){

                if(currentStart.isAfter(anotherAppointmentStart) && currentEnd.isBefore(anotherAppointmentEnd)){
                    return true;
                }
                if(currentStart.isBefore(anotherAppointmentStart) && currentEnd.isAfter(anotherAppointmentStart)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAppointmentTimeWithinCompanysTime(LocalDateTime start, LocalDateTime end){

        ZoneId estTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime companyStartTime8AMESTConverted = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(8,00),estTimeZone);
        ZonedDateTime businessStartTimeConvertBackToSystemDefaultTime =
                companyStartTime8AMESTConverted.withZoneSameInstant(ZoneId.systemDefault());

        //22 hours is 10pm est, which is the company's closing hours
        ZonedDateTime companyEndTime10PMESTConverted = ZonedDateTime.of(end.toLocalDate(), LocalTime.of(22,00),estTimeZone);
        ZonedDateTime businessEndTimeConvertBackToSystemDefaultTime = companyEndTime10PMESTConverted.withZoneSameInstant(ZoneId.systemDefault());

        if(start.toLocalTime().isBefore(companyStartTime8AMESTConverted.toLocalTime())){
            return false;
        }

        if(start.toLocalTime().isAfter(companyEndTime10PMESTConverted.toLocalTime())){
            return false;
        }

        if(end.toLocalTime().isBefore(companyStartTime8AMESTConverted.toLocalTime())){
            return false;
        }

        if(end.toLocalTime().isAfter((companyEndTime10PMESTConverted.toLocalTime()))){
            return false;
        }
        return true;

    }
    public static int returnHourIn12HourFormat(LocalDateTime localDateTime){
        LocalDateTime convertedTimeToLocal = convertUTCLocalDateTimeToSystemDefault(localDateTime);
        int hour = convertedTimeToLocal.getHour();
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


}
