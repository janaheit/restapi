package be.abis.exercise.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static LocalDate parseDate(String dateString){
        return LocalDate.parse(dateString, formatter);
    }

    public static String writeDate(LocalDate localDate){
        return formatter.format(localDate);
    }
}
