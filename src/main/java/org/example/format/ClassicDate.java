package org.example.format;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClassicDate implements DateFormatter{



    private ClassicDate() {
    }


    private final static String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";

    public static LocalDate formatDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.minusDays(1);
    }
}
