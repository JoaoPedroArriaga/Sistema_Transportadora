package com.mycompany.sistema_transportadora.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat DATE_TIME_FORMAT = 
        new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat DATE_FORMAT = 
        new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = 
        new SimpleDateFormat("HH:mm");

    public static String formatDateTime(Calendar calendar) {
        if (calendar == null) return "Não definido";
        return DATE_TIME_FORMAT.format(calendar.getTime());
    }

    public static String formatDate(Calendar calendar) {
        if (calendar == null) return "Não definido";
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static String formatTime(Calendar calendar) {
        if (calendar == null) return "Não definido";
        return TIME_FORMAT.format(calendar.getTime());
    }

    public static Calendar createCalendar(int dia, int mes, int ano, int hora, int minuto) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes - 1, dia, hora, minuto, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static boolean isDataFutura(Calendar data) {
        return data.after(Calendar.getInstance());
    }

    public static boolean isDataPassada(Calendar data) {
        return data.before(Calendar.getInstance());
    }

    public static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}