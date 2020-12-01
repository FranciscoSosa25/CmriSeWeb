package com.cmrise.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Project RadixCloud
 * Created by ernesto on 8/12/16.
 */
public class DateUtil {

    public static LocalDate convert(String date, String pattern) {
        DateTimeFormatter dateTimeFormatter;
        LocalDate result;
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            result = LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            result = null;
        }
        return result;
    }

    public static LocalDateTime convertWithTime(String date, String pattern) {

        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    //TODO: check that
    public static java.sql.Date asSqlDate(LocalDate date) {

        return (null == date) ? null : java.sql.Date.valueOf(date);
    }


    public static java.sql.Date asSqlDate(java.util.Date date) {
        java.sql.Date sqlDate = null;
        if (date != null) {
            sqlDate = new java.sql.Date(date.getTime());
        }
        return sqlDate;
    }

    public static java.util.Date asUtilDate(java.sql.Date sqlDate) {

        if (null != sqlDate) {

            return new java.util.Date(sqlDate.getTime());
        }

        return null;
    }


    /**
     * Obtiene la fecha actual en el formato especificado
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    public static java.sql.Date getNow() {
        return new java.sql.Date(System.currentTimeMillis());
    }
}