package it.unisalento.pps1920.carsharing.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static Date dateTimeFromString (String s){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try{
            d = sdf.parse(s);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return d;
    }

    public static String stringFromDate (Date d){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    public static Date convertToDateFromLocalDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static LocalDate convertToLocalDateFromDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date dateTimeFromStringSlashDots (String s){
        String pattern = "dd/MM/yy HH.mm.ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try{
            d = sdf.parse(s);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return d;
    }

    public static String onlyDateToString(Date date){
        DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        return df1.format(date);
    }

    public static String onlyDateAndTimeToString(Date date){
        DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ITALY);
        return df2.format(date);
    }

    public static Date modificaOrarioData(Date date, String ora, String minuto){
        return dateTimeFromStringSlashDots(onlyDateToString(date) + " " + ora + "." + minuto + ".00");
    }




    public static Date onlyDateFromDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(date));
        return dateWithoutTime;
    }

    /*public static Date setTimeToDate(String time, Date daModificare){
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ITALY);
        return daModificare;
    }*/

    /*public static String test123(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(date));
        return sdf.format(dateWithoutTime);
    }*/


    public static String fromRomeToLondon(String oraDaConvertire){
        /*ZoneId italia = ZoneId.of("Europe/Rome");
        String str = oraDaConvertire;
        System.out.println("str: " + str);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localtDateAndTime = LocalDateTime.parse(str, formatter);
        ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localtDateAndTime, italia );

        System.out.println("Current date and time in a particular timezone : " + dateAndTimeInSydney);

        ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneOffset.UTC);

        System.out.println("Current date and time in UTC : " + utcDate);*/
        System.out.println("da conv : " + oraDaConvertire);
        String dateformat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime ldt = LocalDateTime.parse(oraDaConvertire, DateTimeFormatter.ofPattern(dateformat));

        ZoneId romeZoneId = ZoneId.of("Europe/Rome");
        //System.out.println("TimeZone : " + romeZoneId);

        //LocalDateTime + ZoneId = ZonedDateTime
        ZonedDateTime romeZonedDateTime = ldt.atZone(romeZoneId);
        //System.out.println("Date (Rome) : " + romeZonedDateTime);

        ZoneId londonZoneId = ZoneId.of("Europe/London");
        //System.out.println("TimeZone : " + londonZoneId);

        ZonedDateTime londonDateTime = romeZonedDateTime.withZoneSameInstant(londonZoneId);
        //System.out.println("Date (London) : " + londonDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
        //System.out.println("\n---DateTimeFormatter---");
        //System.out.println("Date (Rome) : " + format.format(romeZonedDateTime));
        System.out.println("Date (London) : " + format.format(londonDateTime));
        return format.format(londonDateTime);
    }
    public static String fromLondonToRome(String oraDaConvertire){
        System.out.println("da conv : " + oraDaConvertire);
        String dateformat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime ldt = LocalDateTime.parse(oraDaConvertire, DateTimeFormatter.ofPattern(dateformat));

        ZoneId londonZoneId = ZoneId.of("Europe/London");
        //System.out.println("TimeZone : " + romeZoneId);

        //LocalDateTime + ZoneId = ZonedDateTime
        ZonedDateTime londonZonedDateTime = ldt.atZone(londonZoneId);
        //System.out.println("Date (Rome) : " + romeZonedDateTime);

        ZoneId romeZoneId = ZoneId.of("Europe/Rome");
        //System.out.println("TimeZone : " + londonZoneId);

        ZonedDateTime romeDateTime = londonZonedDateTime.withZoneSameInstant(romeZoneId);
        //System.out.println("Date (London) : " + londonDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
        //System.out.println("\n---DateTimeFormatter---");
        //System.out.println("Date (Rome) : " + format.format(romeZonedDateTime));
        System.out.println("Date (Rome) : " + format.format(romeDateTime));
        return format.format(romeDateTime);
    }



}
