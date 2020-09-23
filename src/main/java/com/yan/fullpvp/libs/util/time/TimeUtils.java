package com.yan.fullpvp.libs.util.time;

public class TimeUtils {

    public static String timeLeft(long time) {
        time = time /1000;
        final StringBuilder sb = new StringBuilder();

        long years;
        long months;
        long weeks;
        long days;
        long hours;
        long minutes;
        long seconds;

        long aux = time;

        if (time >= 29030400) {
            years = time / 29030400;
            aux -= years * 29030400;
            sb.append((years == 1) ? (years + " ano ")
                    : (years + " anos "));
        }

        if (aux >= 2419200) {
            months = aux / 2419200;
            aux -= months * 2419200;
            sb.append((months == 1) ? (months + " mês ")
                    : (months + " meses "));
        }

        if (aux >= 604800) {
            weeks = aux / 604800;
            aux -= weeks * 604800;
            sb.append((weeks == 1) ? (weeks + " semana ")
                    : (weeks + " semanas "));
        }

        if (aux >= 86400) {
            days = aux / 86400;
            aux -= days * 86400;
            sb.append((days == 1) ? (days + " dia ")
                    : (days + " dias "));
        }

        if (aux >= 3600) {
            hours = aux / 3600;
            aux -= hours * 3600;
            sb.append((hours == 1) ? (hours + " hora ")
                    : (hours + " horas "));
        }
        if (aux >= 60) {
            minutes = aux / 60;
            aux -= minutes * 60;
            sb.append((minutes == 1) ? (minutes + " minuto ")
                    : (minutes + " minutos "));
        }
        if (aux > 0) {
            seconds = aux;
            sb.append((seconds == 1) ? (seconds + " segundo")
                    : (seconds + " segundos"));
        }
        return sb.substring(0, sb.toString().length());
    }

}