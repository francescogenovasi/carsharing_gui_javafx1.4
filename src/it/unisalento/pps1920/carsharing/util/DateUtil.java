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

    public static String fromRomeToLondon(String oraDaConvertire){
        String dateformat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime ldt = LocalDateTime.parse(oraDaConvertire, DateTimeFormatter.ofPattern(dateformat));
        ZoneId romeZoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime romeZonedDateTime = ldt.atZone(romeZoneId);
        ZoneId londonZoneId = ZoneId.of("Europe/London");
        ZonedDateTime londonDateTime = romeZonedDateTime.withZoneSameInstant(londonZoneId);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
        return format.format(londonDateTime);
    }
    public static String fromLondonToRome(String oraDaConvertire){
        String dateformat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime ldt = LocalDateTime.parse(oraDaConvertire, DateTimeFormatter.ofPattern(dateformat));
        ZoneId londonZoneId = ZoneId.of("Europe/London");
        ZonedDateTime londonZonedDateTime = ldt.atZone(londonZoneId);
        ZoneId romeZoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime romeDateTime = londonZonedDateTime.withZoneSameInstant(romeZoneId);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
        return format.format(romeDateTime);
    }



}
