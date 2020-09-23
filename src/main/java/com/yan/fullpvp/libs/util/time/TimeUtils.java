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
            sb.append((years == 1) ? ("§7" +years + " §cano§r ")
                    : ("§7" +years + " §canos§r "));
        }

        if (aux >= 2419200) {
            months = aux / 2419200;
            aux -= months * 2419200;
            sb.append((months == 1) ? ("§7" +months + " §cmês§r ")
                    : ("§7" +months + " §cmeses§r "));
        }

        if (aux >= 604800) {
            weeks = aux / 604800;
            aux -= weeks * 604800;
            sb.append((weeks == 1) ? ("§7" +weeks + " §csemana§r ")
                    : ("§7" +weeks + " §csemanas§r "));
        }

        if (aux >= 86400) {
            days = aux / 86400;
            aux -= days * 86400;
            sb.append((days == 1) ? ("§7" +days + " §cD§r ")
                    : ("§7" +days + " §cD§r "));
        }

        if (aux >= 3600) {
            hours = aux / 3600;
            aux -= hours * 3600;
            sb.append((hours == 1) ? ("§7" +hours + " §cH§r ")
                    : ("§7" +hours + " §cH§r "));
        }
        if (aux >= 60) {
            minutes = aux / 60;
            aux -= minutes * 60;
            sb.append((minutes == 1) ? ("§7" +minutes + " §cM§r ")
                    : ("§7" +minutes + " §cM§r "));
        }
        if (aux > 0) {
            seconds = aux;
            sb.append((seconds == 1) ? ("§7" +seconds + " §cS§r")
                    : ("§7" +seconds + " §cS§r"));
        }
        return sb.substring(0, sb.toString().length());
    }

}