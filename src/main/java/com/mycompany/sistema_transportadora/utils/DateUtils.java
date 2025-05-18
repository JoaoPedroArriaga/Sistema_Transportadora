package com.mycompany.sistema_transportadora.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    // Métodos otimizados para uso nas entidades
    public static String formatDateTime(Calendar calendar) {
        return calendar == null ? "Não definido" : DATE_TIME_FORMAT.format(calendar.getTime());
    }

    public static String formatDate(Calendar calendar) {
        return calendar == null ? "Não definido" : DATE_FORMAT.format(calendar.getTime());
    }

    public static String formatTime(Calendar calendar) {
        return calendar == null ? "Não definido" : TIME_FORMAT.format(calendar.getTime());
    }

    public static Calendar createCalendar(int dia, int mes, int ano, int hora, int minuto) {
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia, hora, minuto, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static boolean isDataFutura(Calendar data) {
        return data != null && data.after(Calendar.getInstance());
    }

    public static boolean isDataPassada(Calendar data) {
        return data != null && data.before(Calendar.getInstance());
    }

    public static String formatDuration(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}